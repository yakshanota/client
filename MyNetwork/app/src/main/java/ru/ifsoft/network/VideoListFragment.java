package ru.ifsoft.network;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ru.ifsoft.network.adapter.ArtistListAdapter;
import ru.ifsoft.network.adapter.VideoListAdapter;
import ru.ifsoft.network.app.App;
import ru.ifsoft.network.constants.Constants;
import ru.ifsoft.network.model.Artist;
import ru.ifsoft.network.model.MediaItem;
import ru.ifsoft.network.model.NewVideo;
import ru.ifsoft.network.model.Video;
import ru.ifsoft.network.util.CustomRequestNew;
import ru.ifsoft.network.view.LineItemDecoration;

public class VideoListFragment extends Fragment implements Constants, SwipeRefreshLayout.OnRefreshListener {

    private static final String STATE_LIST = "State Adapter Data";

    Menu MainMenu;

    private RecyclerView mRecyclerView;
    private NestedScrollView mNestedView;

    private TextView mMessage;
    private ImageView mSplash;

    private SwipeRefreshLayout mItemsContainer;

    private ArrayList<NewVideo> itemsList;
    private VideoListAdapter itemsAdapter;

    private int itemId = 0;
    private int arrayLength = 0;
    private Boolean loadingMore = false;
    private Boolean viewMore = false;
    private Boolean restore = false;

    public VideoListFragment() {
        // Required empty public constructor
    }

    public VideoListFragment newInstance(Boolean pager) {

        VideoListFragment myFragment = new VideoListFragment();

        Bundle args = new Bundle();
        args.putBoolean("pager", pager);
        myFragment.setArguments(args);

        return myFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_video, container, false);

        if (savedInstanceState != null) {

            itemsList = savedInstanceState.getParcelableArrayList(STATE_LIST);
            itemsAdapter = new VideoListAdapter(getActivity(), itemsList);

            restore = savedInstanceState.getBoolean("restore");
            itemId = savedInstanceState.getInt("itemId");

        } else {

            itemsList = new ArrayList<NewVideo>();
            itemsAdapter = new VideoListAdapter(getActivity(), itemsList);

            restore = false;
            itemId = 0;
        }


        mItemsContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.container_items);
        mItemsContainer.setOnRefreshListener(this);

        mMessage = (TextView) rootView.findViewById(R.id.message);
        mSplash = (ImageView) rootView.findViewById(R.id.splash);

        //

        mNestedView = (NestedScrollView) rootView.findViewById(R.id.nested_view);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayout.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(itemsAdapter);

        itemsAdapter.setOnItemClickListener((view, item, position) -> {
            if (item.getVideoUrl().length() != 0) {
                watchYoutubeVideo(item.getVideoUrl());

            }
        });

        mRecyclerView.setNestedScrollingEnabled(false);


        mNestedView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY < oldScrollY) { // up


                }

                if (scrollY > oldScrollY) { // down


                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {

                    if (!loadingMore && (viewMore) && !(mItemsContainer.isRefreshing())) {

                        mItemsContainer.setRefreshing(true);

                        loadingMore = true;

                        getItems();
                    }
                }
            }
        });

        if (itemsAdapter.getItemCount() == 0) {

            showMessage(getText(R.string.label_empty_list).toString());

        } else {

            hideMessage();
        }

        if (!restore) {

            showMessage(getText(R.string.msg_loading_2).toString());
            //  String val = Long.toString(App.getInstance().getId());
            //   String acc = App.getInstance().getAccessToken();
            getItems();
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    public void watchYoutubeVideo(String id) {

        final String regex = "v=([^\\s&#]*)";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(id);
        String new_id = "";
        if (matcher.find()) {
            new_id = matcher.group(1);
        }

        if (new_id.length() > 0) {

            if (YOUTUBE_API_KEY.length() > 5) {

                Intent i = new Intent(getActivity(), ViewYouTubeVideoActivity.class);
                i.putExtra("videoCode", new_id);
                startActivity(i);

            } else {

                try {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + new_id));
                    startActivity(intent);

                } catch (ActivityNotFoundException ex) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + new_id));
                    startActivity(intent);
                }
            }
        }
    }


    @Override
    public void onRefresh() {

        if (App.getInstance().isConnected()) {

            itemId = 0;
            getItems();

        } else {

            mItemsContainer.setRefreshing(false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putBoolean("restore", true);
        outState.putInt("itemId", itemId);
        outState.putParcelableArrayList(STATE_LIST, itemsList);
    }

    public void getItems() {

        mItemsContainer.setRefreshing(true);

        CustomRequestNew jsonReq = new CustomRequestNew(Request.Method.POST, METHOD_VIDEO_LIST, null,
                response -> {

                    if (!isAdded() || getActivity() == null) {

                        Log.e("ERROR", "ActiveArtist List Not Added to Activity");

                        return;
                    }

                    if (!loadingMore) {

                        itemsList.clear();
                    }

                    try {

                        arrayLength = response.length();

                        if (arrayLength > 0) {

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject userObj = response.getJSONObject(i);

                                NewVideo community = new NewVideo(userObj);

                                itemsList.add(community);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        loadingComplete();

                    }

                }, error -> {

            if (!isAdded() || getActivity() == null) {

                Log.e("ERROR", "GroupsFragment Not Added to Activity");

                return;
            }

            loadingComplete();
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", Long.toString(App.getInstance().getId()));
                params.put("accessToken", App.getInstance().getAccessToken());

                return params;
            }
        };

        App.getInstance().addToRequestQueue(jsonReq);
    }

    public void loadingComplete() {

        if (arrayLength == LIST_ITEMS) {

            viewMore = true;

        } else {

            viewMore = false;
        }

        itemsAdapter.notifyDataSetChanged();

        if (itemsAdapter.getItemCount() == 0) {

            showMessage(getText(R.string.label_empty_list).toString());

        } else {

            hideMessage();
        }

        loadingMore = false;
        mItemsContainer.setRefreshing(false);
    }

    public void showMessage(String message) {

        mMessage.setText(message);
        mMessage.setVisibility(View.VISIBLE);

        mSplash.setVisibility(View.VISIBLE);
    }

    public void hideMessage() {

        mMessage.setVisibility(View.GONE);

        mSplash.setVisibility(View.GONE);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();

        inflater.inflate(R.menu.menu_groups, menu);

        MainMenu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.action_search_groups: {

                Intent i = new Intent(getActivity(), VideoSearchActivity.class);
                startActivity(i);

                return true;
            }

            default: {

                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}