package ru.ifsoft.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ru.ifsoft.network.adapter.AddsListAdapter;
import ru.ifsoft.network.adapter.EventsListAdapter;
import ru.ifsoft.network.app.App;
import ru.ifsoft.network.constants.Constants;
import ru.ifsoft.network.model.Adds;
import ru.ifsoft.network.model.Item;
import ru.ifsoft.network.model.MEvent;
import ru.ifsoft.network.model.NewVideo;
import ru.ifsoft.network.util.CustomRequest;
import ru.ifsoft.network.util.CustomRequestNew;
import ru.ifsoft.network.view.LineItemDecoration;

public class EventCalenderFragment extends Fragment implements Constants, SwipeRefreshLayout.OnRefreshListener {

    private static final String STATE_LIST = "State Adapter Data";
    private FloatingActionButton floatingActionButton;

    private RecyclerView mRecyclerView;
    private NestedScrollView mNestedView;

    private SwipeRefreshLayout mItemsContainer;

    private ArrayList<MEvent> itemsList;
    private EventsListAdapter itemsAdapter;

    private int itemId = 0;
    private int arrayLength = 0;
    private Boolean loadingMore = false;
    private Boolean viewMore = false;
    private Boolean restore = false;

    private EditText searchView;
    public String queryText = "";

    private TextView mMessage;
    private ImageView mSplash,btn_search;

    public EventCalenderFragment() {
        // Required empty public constructor
    }

    public EventCalenderFragment newInstance(Boolean pager) {

        EventCalenderFragment myFragment = new EventCalenderFragment();

        Bundle args = new Bundle();
        args.putBoolean("pager", pager);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_events, container, false);

        if (savedInstanceState != null) {

            itemsList = savedInstanceState.getParcelableArrayList(STATE_LIST);
            itemsAdapter = new EventsListAdapter(getActivity(), itemsList);

            restore = savedInstanceState.getBoolean("restore");
            itemId = savedInstanceState.getInt("itemId");

        } else {

            itemsList = new ArrayList<MEvent>();
            itemsAdapter = new EventsListAdapter(getActivity(), itemsList);

            restore = false;
            itemId = 0;
        }


        mItemsContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.container_items);
        mItemsContainer.setOnRefreshListener(this);

        mMessage = (TextView) rootView.findViewById(R.id.message);
        mSplash = (ImageView) rootView.findViewById(R.id.splash);
        btn_search = (ImageView) rootView.findViewById(R.id.btn_search);

        searchView = (EditText) rootView.findViewById(R.id.et_search);
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.feedButton);

        //
        searchView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_ACTION_SEARCH);
        searchView.setHint(getString(R.string.placeholder_toolbar_search));


        mNestedView = (NestedScrollView) rootView.findViewById(R.id.nested_view);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //    mRecyclerView.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayout.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(itemsAdapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateEvent.class);
                startActivity(intent);
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryText = searchView.getText().toString().trim();
                if (queryText.length() > 0) {

                    searchStart();

                } else {
                    if (App.getInstance().isConnected()) {

                        itemId = 0;
                        getItems();

                    } else {

                        mItemsContainer.setRefreshing(false);
                    }
                }
            }
        });

        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    searchView.clearFocus();
                    //  hideKeyboard();

                    queryText = searchView.getText().toString().trim();
                    if (queryText.length() > 0) {

                        searchStart();

                    }


                    return true;
                }

                return false;
            }
        });

        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {

                    //got focus

                    searchView.setCursorVisible(true);

                } else {

                    searchView.setCursorVisible(false);
                    searchView.clearFocus();
                    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                }
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

    public void getItems() {

        mItemsContainer.setRefreshing(true);

        CustomRequest jsonReq = new CustomRequest(Request.Method.GET, METHOD_EVENTS_LIST, null,
                response -> {

                    if (!loadingMore) {

                        itemsList.clear();
                    }

                    try {
                        if (!response.getBoolean("error")) {
                            JSONArray array = response.getJSONArray("data");
                            arrayLength = array.length();
                            if (arrayLength > 0) {

                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject userObj = array.getJSONObject(i);

                                    MEvent community = new MEvent(userObj);

                                    itemsList.add(community);
                                }
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        loadingComplete();

                    }

                }, error -> {

            loadingComplete();
            Toast.makeText(getActivity(), getString(R.string.error_data_loading), Toast.LENGTH_LONG).show();
        }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        App.getInstance().addToRequestQueue(jsonReq);
    }

    public void searchStart() {

        if (App.getInstance().isConnected()) {

            itemId = 0;
            search();

        } else {

            Toast.makeText(getActivity(), getText(R.string.msg_network_error), Toast.LENGTH_SHORT).show();
        }
    }

    public void search() {

        mItemsContainer.setRefreshing(true);

        CustomRequest jsonReq = new CustomRequest(Request.Method.GET, METHOD_EVENTS_LIST+"?search_key="+queryText, null,
                response -> {

                    if (!loadingMore) {

                        itemsList.clear();
                    }

                    try {
                        if (!response.getBoolean("error")) {
                            JSONArray array = response.getJSONArray("data");
                            arrayLength = array.length();
                            if (arrayLength > 0) {

                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject userObj = array.getJSONObject(i);

                                    MEvent community = new MEvent(userObj);

                                    itemsList.add(community);
                                }
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        loadingComplete();

                    }

                }, error -> {

            loadingComplete();
            Toast.makeText(getActivity(), getString(R.string.error_data_loading), Toast.LENGTH_LONG).show();
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        App.getInstance().addToRequestQueue(jsonReq);
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