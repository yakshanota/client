package ru.ifsoft.network;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ru.ifsoft.network.app.App;
import ru.ifsoft.network.constants.Constants;
import ru.ifsoft.network.util.CustomRequest;

public class UpgradesFragment extends Fragment implements Constants {

    private static final String STATE_LIST = "State Adapter Data";
    RelativeLayout mWebViewLoadingScreen, mWebViewErrorScreen, mWebViewContentScreen;

    WebView mWebView;

    public UpgradesFragment() {
        // Required empty public constructor
    }
    public boolean canGoBack() {
        return mWebView.canGoBack();
    }

    public void goBack() {
        mWebView.goBack();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(false);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_inactive_artist, container, false);

        mWebView = (WebView) rootView.findViewById(R.id.webView);
        mWebViewErrorScreen = (RelativeLayout) rootView.findViewById(R.id.WebViewErrorScreen);
        mWebViewLoadingScreen = (RelativeLayout) rootView.findViewById(R.id.WebViewLoadingScreen);
        mWebViewContentScreen = (RelativeLayout) rootView.findViewById(R.id.WebViewContentScreen);

        mWebView.setWebViewClient(new WebViewClient() {

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                showLoadingScreen();
            }

            public void onPageFinished(WebView view, String url) {

                showContentScreen();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                showErrorScreen();
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }

        });

        mWebView.getSettings().setJavaScriptEnabled(true);

        if (App.getInstance().isConnected()) {
            WebSettings settings = mWebView.getSettings();
            settings.setSupportZoom(false);
            mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            mWebView.getSettings().setBuiltInZoomControls(false);
            mWebView.loadUrl(METHOD_ADVERTISE);


        } else {

            showErrorScreen();
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    public void showLoadingScreen() {

        mWebViewErrorScreen.setVisibility(View.GONE);
        mWebViewContentScreen.setVisibility(View.GONE);

        mWebViewLoadingScreen.setVisibility(View.VISIBLE);
    }

    public void showErrorScreen() {

        mWebViewLoadingScreen.setVisibility(View.GONE);
        mWebViewContentScreen.setVisibility(View.GONE);

        mWebViewErrorScreen.setVisibility(View.VISIBLE);
    }

    public void showContentScreen() {

        mWebViewErrorScreen.setVisibility(View.GONE);
        mWebViewLoadingScreen.setVisibility(View.GONE);

        mWebViewContentScreen.setVisibility(View.VISIBLE);
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