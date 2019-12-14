package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserProfile extends AppCompatActivity {

    TextView tvName, tvEmail, tvVenmo, etVenmo, tvPhone, etPhone;
    Context mContext;
    GoogleSignInClient mGoogleSignInClient;
    Button btnSignOut, saveBtn;
    JSONObject jObj;
    ImageButton venmoBtn;
    String personEmail, strVenmo, strPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mContext = getApplicationContext();

        tvName = findViewById(R.id.textViewName);
        tvEmail = findViewById(R.id.textViewEmail);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            tvName.setText(personGivenName);
            tvEmail.setText(personEmail);
            btnSignOut = findViewById(R.id.buttonSignOut);
            btnSignOut.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  signOut();

                                              }
                                          }
            );
        }


        tvVenmo = findViewById(R.id.textViewVenmoUserName);
        tvPhone = findViewById(R.id.textViewUserPhone);
        etVenmo = findViewById(R.id.editTextVenmo);
        etPhone = findViewById(R.id.editTextPhone);



        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", personEmail);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "https://us-central1-faceclass-221a7.cloudfunctions.net/nodecruz/thriftsluguserinfo";

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();

//                    System.out.println(myResponse);
                    try
                    {
                        JSONArray jArray = new JSONArray(myResponse);
                        jObj = jArray.getJSONObject(0);
                        strVenmo = jObj.getString("venmoid");
                        strPhone = jObj.getString("phonenumber");

                    }
                    catch (JSONException e)
                    {

                    }


//                    Log.d(TAG, "onResponse:" + myResponse);
                    UserProfile.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvPhone.setText(strPhone);
                            tvVenmo.setText(strVenmo);
                        }
                    });
                }
            }
        });



        tvVenmo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("venmo pressed");
                etVenmo.setText(tvVenmo.getText());
                tvVenmo.setVisibility(View.INVISIBLE);
                etVenmo.setVisibility(View.VISIBLE);
            }
        });

        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("venmo pressed");
                etPhone.setText(tvPhone.getText());
                tvPhone.setVisibility(View.INVISIBLE);
                etPhone.setVisibility(View.VISIBLE);
            }
        });




        ImageButton venmoBtn = (ImageButton) findViewById(R.id.venmoBtn);
        venmoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_MAIN);
                PackageManager managerclock = getPackageManager();
                i = managerclock.getLaunchIntentForPackage("com.venmo");
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                startActivity(i);
            }
        });

        Button saveBtn = (Button) findViewById(R.id.save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent it = new Intent(UserProfile.this, MainActivity.class);
//                startActivity(it);



                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email", personEmail);

                    if(etVenmo.getText().toString() != null && !etVenmo.getText().toString().isEmpty() )
                    {
                        jsonObject.put("venmoid", etVenmo.getText().toString());
                    }
                    else
                    {
                        jsonObject.put("venmoid", tvVenmo.getText().toString());
                    }


                    if(etPhone.getText().toString() != null && !etPhone.getText().toString().isEmpty() )
                    {
                        jsonObject.put("phonenumber", etPhone.getText().toString());
                    }
                    else
                    {
                        jsonObject.put("phonenumber", tvPhone.getText().toString());
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = "https://us-central1-faceclass-221a7.cloudfunctions.net/nodecruz/thriftslugchangeuserinfo";

                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                // put your json here
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();


                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String myResponse = response.body().string();

//
                        }
                    }
                });


                finish();
            }
        });

    }


    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent homeIntent = new Intent(UserProfile.this, HomeActivity.class);
                        startActivity(homeIntent);

                    }
                });
    }
}
