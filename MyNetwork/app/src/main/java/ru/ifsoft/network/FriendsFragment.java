package ru.ifsoft.network;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.balysv.materialripple.MaterialRippleLayout;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ru.ifsoft.network.adapter.PeopleListAdapter;
import ru.ifsoft.network.app.App;
import ru.ifsoft.network.constants.Constants;
import ru.ifsoft.network.model.Profile;
import ru.ifsoft.network.util.CustomRequest;
import ru.ifsoft.network.util.Helper;

public class FriendsFragment extends Fragment implements Constants, SwipeRefreshLayout.OnRefreshListener {

    private static final String STATE_LIST = "State Adapter Data";

    private CardView mSearchFriendsBox;
    private MaterialRippleLayout mSearchFriendsButton;
    private CircularImageView mSearchFriendsImage;
    private TextView mSearchFriendsTitle;

    private RecyclerView mRecyclerView;
    private NestedScrollView mNestedView;

    private TextView mDesc;
    private TextView mMessage;
    private ImageView mSplash;

    SwipeRefreshLayout mItemsContainer;

    private ArrayList<Profile> itemsList;
    private PeopleListAdapter itemsAdapter;

    private long profileId = 0;

    private int itemId = 0;
    private int arrayLength = 0;
    private Boolean loadingMore = false;
    private Boolean viewMore = false;
    private Boolean restore = false;
    private Boolean loaded = false;
    private Boolean pager = false;

    Menu MainMenu;
    SearchView searchView = null;
    private String queryText = "";
    MenuItem searchItem, searchIcon;
    private int search_gender = -1, search_online = -1, search_photo = -1;


    public FriendsFragment() {
        // Required empty public constructor
    }

    public FriendsFragment newInstance(Boolean pager) {

        FriendsFragment myFragment = new FriendsFragment();

        Bundle args = new Bundle();
        args.putBoolean("pager", pager);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (savedInstanceState != null) {

            itemsList = savedInstanceState.getParcelableArrayList(STATE_LIST);
            itemsAdapter = new PeopleListAdapter(getActivity(), itemsList);

            viewMore = savedInstanceState.getBoolean("viewMore");
            restore = savedInstanceState.getBoolean("restore");
            loaded = savedInstanceState.getBoolean("loaded");
            pager = savedInstanceState.getBoolean("pager");
            itemId = savedInstanceState.getInt("itemId");

        } else {

            itemsList = new ArrayList<Profile>();
            itemsAdapter = new PeopleListAdapter(getActivity(), itemsList);

            restore = false;
            loaded = false;
            pager = false;
            itemId = 0;
        }

        Intent i = getActivity().getIntent();

        profileId = i.getLongExtra("profileId", 0);

        if (profileId == 0) profileId = App.getInstance().getId();

        if (getArguments() != null) {

            pager = getArguments().getBoolean("pager", false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

        mItemsContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.container_items);
        mItemsContainer.setOnRefreshListener(this);

        //

        mDesc = (TextView) rootView.findViewById(R.id.desc);
        mMessage = (TextView) rootView.findViewById(R.id.message);
        mSplash = (ImageView) rootView.findViewById(R.id.splash);

        mDesc.setVisibility(View.GONE);

        // Search Friends spotlight

        mSearchFriendsBox = (CardView) rootView.findViewById(R.id.searchFriendsBox);
        mSearchFriendsButton = (MaterialRippleLayout) rootView.findViewById(R.id.searchFriendsButton);

        if (!loaded) mSearchFriendsBox.setVisibility(View.GONE);

        mSearchFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), NearbyActivity.class);
                startActivityForResult(intent, 1001);
            }
        });

        mSearchFriendsImage = (CircularImageView) rootView.findViewById(R.id.searchFriendsImage);
        mSearchFriendsTitle = (TextView) rootView.findViewById(R.id.searchFriendsTitle);

        //

        mNestedView = (NestedScrollView) rootView.findViewById(R.id.nested_view);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        final LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), Helper.getGridSpanCount(getActivity()));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(itemsAdapter);

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

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Profile item = (Profile) itemsList.get(position);

                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("profileId", item.getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));

        if (itemsAdapter.getItemCount() == 0) {

            showMessage(getText(R.string.label_empty_list).toString());

        } else {

            hideMessage();
        }

        if (!pager && !restore && !loaded) {

            showMessage(getText(R.string.msg_loading_2).toString());

            getItems();
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {

            if (loaded) updateProfileInfo();

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (isAdded()) {

                        if (!loaded) {

                            showMessage(getText(R.string.msg_loading_2).toString());

                            getItems();

                        } else {

                            if (App.getInstance().getNewFriendsCount() > 0) {

                                loaded = false;

                                showMessage(getText(R.string.msg_loading_2).toString());

                                mSearchFriendsBox.setVisibility(View.GONE);

                                itemId = 0;

                                getItems();
                            }
                        }
                    }
                }
            }, 50);
        }
    }

    private void updateProfileInfo() {

        if (isAdded()) {

            if (profileId == 0 || App.getInstance().getId() == profileId) {

                if (isAdded()) {

                    mSearchFriendsBox.setVisibility(View.GONE);

                    if (App.getInstance().getPhotoUrl() != null && App.getInstance().getPhotoUrl().length() > 0) {

                        App.getInstance().getImageLoader().get(App.getInstance().getPhotoUrl(), ImageLoader.getImageListener(mSearchFriendsImage, R.drawable.profile_default_photo, R.drawable.profile_default_photo));

                    } else {

                        mSearchFriendsImage.setImageResource(R.drawable.profile_default_photo);
                    }

                    if (App.getInstance().getPhotoUrl() != null && App.getInstance().getPhotoUrl().length() > 0) {

                        App.getInstance().getImageLoader().get(App.getInstance().getPhotoUrl(), ImageLoader.getImageListener(mSearchFriendsImage, R.drawable.profile_default_photo, R.drawable.profile_default_photo));

                    } else {

                        mSearchFriendsImage.setImageResource(R.drawable.profile_default_photo);
                    }

                    SpannableStringBuilder txt = new SpannableStringBuilder(String.format(Locale.getDefault(), getString(R.string.msg_search_friends_promo), App.getInstance().getFullname()));
                    txt.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, App.getInstance().getFullname().length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    mSearchFriendsTitle.setText(txt);
                }

            } else {

                mSearchFriendsBox.setVisibility(View.GONE);
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

        outState.putBoolean("viewMore", viewMore);
        outState.putBoolean("restore", true);
        outState.putBoolean("loaded", loaded);
        outState.putBoolean("pager", pager);
        outState.putInt("itemId", itemId);
        outState.putParcelableArrayList(STATE_LIST, itemsList);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();

        inflater.inflate(R.menu.menu_friends, menu);

        MainMenu = menu;

        searchIcon = menu.findItem(R.id.action_search_friends);
        searchItem = menu.findItem(R.id.options_menu_main_search);


        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {

            searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        }

        if (searchView != null) {

            searchView.setQuery(queryText, false);

            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setIconifiedByDefault(false);
            searchView.setIconified(false);

            SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
            searchAutoComplete.setHint(getText(R.string.placeholder_search));
            searchAutoComplete.setHintTextColor(getResources().getColor(R.color.white));

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {

                    queryText = newText;

                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {

                    queryText = query;
                    if (App.getInstance().isConnected()) {
                        searchItem.setVisible(false);
                        searchIcon.setVisible(true);
                        search();
                    } else {

                        Toast.makeText(getActivity(), getText(R.string.msg_network_error), Toast.LENGTH_SHORT).show();
                    }

                    return false;
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.action_search_friends: {
                searchIcon.setVisible(false);
                searchItem.setVisible(true);
                return true;
            }

            default: {

                return super.onOptionsItemSelected(item);
            }
        }
    }

    public void getItems() {

        mItemsContainer.setRefreshing(true);

        CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_FRIENDS_GET, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (!isAdded() || getActivity() == null) {

                            Log.e("ERROR", "Friends Fragment Not Added to Activity");

                            return;
                        }

                        if (!loadingMore) {

                            itemsList.clear();
                        }

                        try {

                            arrayLength = 0;

                            if (!response.getBoolean("error")) {

                                if (itemId == 0 && App.getInstance().getId() == profileId) {

                                    App.getInstance().setNewFriendsCount(0);
                                }

                                itemId = response.getInt("itemId");

                                if (response.has("items")) {

                                    JSONArray usersArray = response.getJSONArray("items");

                                    arrayLength = usersArray.length();

                                    if (arrayLength > 0) {

                                        for (int i = 0; i < usersArray.length(); i++) {

                                            JSONObject userObj = (JSONObject) usersArray.get(i);

                                            Profile item = new Profile(userObj);

                                            itemsList.add(item);
                                        }
                                    }
                                }

                            }

                        } catch (JSONException e) {

                            e.printStackTrace();

                        } finally {

                            Log.d("Friends", response.toString());

                            loadingComplete();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (!isAdded() || getActivity() == null) {

                    Log.e("ERROR", "Friends Fragment Not Added to Activity");

                    return;
                }

                loadingComplete();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", Long.toString(App.getInstance().getId()));
                params.put("accessToken", App.getInstance().getAccessToken());
                params.put("profileId", Long.toString(profileId));
                params.put("itemId", Long.toString(itemId));
                params.put("language", "en");

                return params;
            }
        };

        App.getInstance().addToRequestQueue(jsonReq);
    }

    public void loadingComplete() {

        loaded = true;

        updateProfileInfo();

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

    static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

        public interface OnItemClickListener {

            void onItemClick(View view, int position);

            void onItemLongClick(View view, int position);
        }

        private OnItemClickListener mListener;

        private GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {

            mListener = listener;

            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {

                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {

                    View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if (childView != null && mListener != null) {

                        mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {

            View childView = view.findChildViewUnder(e.getX(), e.getY());

            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {

                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public void showMessage(String message) {

        if (loaded) {

            mDesc.setVisibility(View.VISIBLE);

        } else {

            mDesc.setVisibility(View.GONE);
        }

        mMessage.setText(message);
        mMessage.setVisibility(View.VISIBLE);

        mSplash.setVisibility(View.VISIBLE);
    }

    public void hideMessage() {

        mDesc.setVisibility(View.GONE);
        mMessage.setVisibility(View.GONE);

        mSplash.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void search() {

        mItemsContainer.setRefreshing(true);

        CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_APP_SEARCH, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (!isAdded() || getActivity() == null) {

                            Log.e("ERROR", "SearchFragment Not Added to Activity");

                            return;
                        }

                        try {

                            if (!loadingMore) {

                                itemsList.clear();
                            }

                            arrayLength = 0;

                            if (!response.getBoolean("error")) {


                                if (response.has("items")) {

                                    JSONArray usersArray = response.getJSONArray("items");

                                    arrayLength = usersArray.length();

                                    if (arrayLength > 0) {

                                        for (int i = 0; i < usersArray.length(); i++) {

                                            JSONObject profileObj = (JSONObject) usersArray.get(i);

                                            Profile profile = new Profile(profileObj);

                                            itemsList.add(profile);
                                        }
                                    }
                                }
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();

                        } finally {

                            loadingComplete();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (!isAdded() || getActivity() == null) {

                    Log.e("ERROR", "SearchFragment Not Added to Activity");

                    return;
                }

                loadingComplete();

                Log.e("SearchFragment search()", error.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", Long.toString(App.getInstance().getId()));
                params.put("accessToken", App.getInstance().getAccessToken());
                params.put("query", queryText);
                params.put("userId", Long.toString(profileId));
                params.put("gender", Integer.toString(search_gender));
                params.put("online", Integer.toString(search_online));
                params.put("photo", Integer.toString(search_photo));

                return params;
            }
        };

        App.getInstance().addToRequestQueue(jsonReq);
    }

}