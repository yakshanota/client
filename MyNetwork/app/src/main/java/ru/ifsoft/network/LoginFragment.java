package ru.ifsoft.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ru.ifsoft.network.app.App;
import ru.ifsoft.network.constants.Constants;
import ru.ifsoft.network.util.CustomRequest;
import ru.ifsoft.network.util.Helper;

public class LoginFragment extends Fragment implements Constants {

    CallbackManager callbackManager;

    LoginButton loginButton;

    private ProgressDialog pDialog;

    TextView mForgotPassword,btn_signup,otpLogin;
    ImageView signinBtn;
    EditText signinUsername, signinPassword;
    String username, password, facebook_id = "", fullname = "", email = "";

    private Boolean loading = false;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        if (AccessToken.getCurrentAccessToken()!= null) LoginManager.getInstance().logOut();

        callbackManager = CallbackManager.Factory.create();

        initpDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        if (loading) {

            showpDialog();
        }

        loginButton = (LoginButton) rootView.findViewById(R.id.login_button);
        btn_signup = (TextView) rootView.findViewById(R.id.btn_signup) ;
        otpLogin = (TextView) rootView.findViewById(R.id.otpLogin);
        loginButton.setPermissions("public_profile");

        loginButton.setVisibility(View.GONE);

        // Registering CallbackManager with the LoginButton
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                // Retrieving access token using the LoginResult
                AccessToken accessToken = loginResult.getAccessToken();

                useLoginInformation(accessToken);
            }

            @Override
            public void onCancel() {

            }
            @Override
            public void onError(FacebookException error) {

            }
        });

        if (!FACEBOOK_AUTHORIZATION) {

            loginButton.setVisibility(View.GONE);
        }

        signinUsername = (EditText) rootView.findViewById(R.id.signinUsername);
        signinPassword = (EditText) rootView.findViewById(R.id.signinPassword);

        mForgotPassword = (TextView) rootView.findViewById(R.id.forgotPassword);

        mForgotPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), RecoveryActivity.class);
                startActivity(i);
            }
        });

        otpLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), SendOTPActivity.class);
                startActivity(i);
            }
        });

        signinBtn = (ImageView) rootView.findViewById(R.id.signinBtn);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = signinUsername.getText().toString();
                password = signinPassword.getText().toString();

                if (!App.getInstance().isConnected()) {

                    Toast.makeText(getActivity(), R.string.msg_network_error, Toast.LENGTH_SHORT).show();

                } else if (!checkUsername() || !checkPassword()) {

                } else {

                    signin();
                }
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), RegisterActivity.class);
                startActivity(i);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void useLoginInformation(AccessToken accessToken) {

        /**
         Creating the GraphRequest to fetch user details
         1st Param - AccessToken
         2nd Param - Callback (which will be invoked once the request is successful)
         **/

        showpDialog();

        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {

            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {

                    if (object.has("id")) {

                        facebook_id = object.getString("id");
                    }

                    if (object.has("name")) {

                        fullname = object.getString("name");
                    }

                    if (object.has("email")) {

                        email = object.getString("email");
                    }

                } catch (JSONException e) {

                    Log.e("Facebook Login", "Could not parse malformed JSON: \"" + object.toString() + "\"");

                } finally {

                    if (AccessToken.getCurrentAccessToken() != null) LoginManager.getInstance().logOut();

                    if (!facebook_id.equals("")) {

                        signinByFacebookId();

                    } else {

                        hidepDialog();
                    }
                }
            }
        });

        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        // parameters.putString("fields", "id,name,email,picture.width(200)");
        parameters.putString("fields", "id, name");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
    }

    public void onDestroyView() {

        super.onDestroyView();

        hidepDialog();
    }

    protected void initpDialog() {

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getString(R.string.msg_loading));
        pDialog.setCancelable(false);
    }

    protected void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void signinByFacebookId() {

        CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_ACCOUNT_LOGINBYFACEBOOK, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (App.getInstance().authorize(response)) {

                            if (App.getInstance().getState() == ACCOUNT_STATE_ENABLED) {

                                App.getInstance().updateGeoLocation();
                               // sendOTP(email);

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            } else {

                                if (App.getInstance().getState() == ACCOUNT_STATE_BLOCKED) {

                                    App.getInstance().logout();
                                    Toast.makeText(getActivity(), getText(R.string.msg_account_blocked), Toast.LENGTH_SHORT).show();

                                } else {

                                    App.getInstance().updateGeoLocation();

                                   // sendOTP(email);

                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }

                        } else {

                            if (facebook_id != "") {

                                Intent i = new Intent(getActivity(), SignupActivity.class);
                                i.putExtra("facebookId", facebook_id);
                                i.putExtra("facebookName", fullname);
                                i.putExtra("facebookEmail", email);
                                startActivity(i);

                            } else {

                                Toast.makeText(getActivity(), getString(R.string.error_signin), Toast.LENGTH_SHORT).show();
                            }
                        }

                        loading = false;

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), getText(R.string.error_data_loading), Toast.LENGTH_LONG).show();

                loading = false;

                hidepDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("facebookId", facebook_id);
                params.put("clientId", CLIENT_ID);
                params.put("appType", Integer.toString(APP_TYPE_ANDROID));
                params.put("fcm_regId", App.getInstance().getGcmToken());

                return params;
            }
        };

        App.getInstance().addToRequestQueue(jsonReq);
    }

    public void signin() {


        loading = true;

        showpDialog();

        String fcm = App.getInstance().getGcmToken();
        CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_ACCOUNT_LOGIN, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (App.getInstance().authorize(response)) {

                            if (App.getInstance().getState() == ACCOUNT_STATE_ENABLED) {

                                App.getInstance().updateGeoLocation();

                                //sendOTP(username);

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            } else {

                                if (App.getInstance().getState() == ACCOUNT_STATE_BLOCKED) {

                                    App.getInstance().logout();
                                    Toast.makeText(getActivity(), getText(R.string.msg_account_blocked), Toast.LENGTH_SHORT).show();

                                } else {

                                    App.getInstance().updateGeoLocation();
                                    //sendOTP(username);

                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }

                        } else {

                            Toast.makeText(getActivity(), getString(R.string.error_signin), Toast.LENGTH_SHORT).show();
                        }

                        loading = false;

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), getText(R.string.error_data_loading), Toast.LENGTH_LONG).show();

                loading = false;

                hidepDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                params.put("clientId", CLIENT_ID);
                params.put("appType", Integer.toString(APP_TYPE_ANDROID));
                params.put("fcm_regId", App.getInstance().getGcmToken());

                return params;
            }
        };

        App.getInstance().addToRequestQueue(jsonReq);
    }


    public Boolean checkUsername() {

        username = signinUsername.getText().toString();

        signinUsername.setError(null);

        Helper helper = new Helper();

        if (username.length() == 0) {

            signinUsername.setError(getString(R.string.error_field_empty));

            return false;
        }

        if (username.length() < 5) {

            signinUsername.setError(getString(R.string.error_small_username));

            return false;
        }

        if (!helper.isValidLogin(username) && !helper.isValidPhone(username)) {

            signinUsername.setError(getString(R.string.error_wrong_format));

            return false;
        }

        return  true;
    }

    public Boolean checkPassword() {

        password = signinPassword.getText().toString();

        signinPassword.setError(null);

        Helper helper = new Helper();

        if (username.length() == 0) {

            signinPassword.setError(getString(R.string.error_field_empty));

            return false;
        }

        if (password.length() < 6) {

            signinPassword.setError(getString(R.string.error_small_password));

            return false;
        }

        if (!helper.isValidPassword(password)) {

            signinPassword.setError(getString(R.string.error_wrong_format));

            return false;
        }

        return  true;
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