package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.jetbrains.annotations.NotNull;
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

public class Selling extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    GoogleSignInClient mGoogleSignInClient;
    String userEmail = "";
    String strItemType, strItemGender, strItemSize, strItemCondition;
    TextView itemName, itemBrand, itemColor, itemPrice, tvImageText;
    private static final String TAG = "Selling";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling);
        initializeWidgets();
        setUserEmail();

        initializeSpinnerType();
        intializeSpinnerGender();
        initializeSpinnerSize();
        initializeSpinnerCondition();


        Button addPhoto = findViewById(R.id.addPhoto);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Selling.this, addingPhoto.class);
                startActivity(it);
            }
        });

    }


    private void initializeWidgets() {
        itemName = findViewById(R.id.itemName);
        itemBrand = findViewById(R.id.itemBrand);
        itemColor = findViewById(R.id.itemColor);
        itemPrice = findViewById(R.id.itemPrice);
        tvImageText = findViewById(R.id.texviewtbitmapimage);
    }

    private void setUserEmail() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null) {
            userEmail = acct.getEmail();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String text = parent.getSelectedItem().toString();
//        String text = parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void buttonSubmitListener(View view) {
        Log.d(TAG, "length of bitmaptext: " + Store.bitMapText.length());
//        System.out.println(Store.bitMapText);
//        Log.d(TAG, "get from the textview: " + tvImageText.getText().toString());
        Log.d(TAG, "buttonSubmitListener: submitted");

        // create your json here
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("seller_ID", userEmail);
            jsonObject.put("name", itemName.getText());
            jsonObject.put("type", strItemType);
            jsonObject.put("gender", strItemGender);
            jsonObject.put("size", strItemSize);
            jsonObject.put("brand", itemBrand.getText());
            jsonObject.put("color", itemColor.getText());
            jsonObject.put("condition_item", strItemCondition);
            jsonObject.put("price", itemPrice.getText());
            jsonObject.put("pic_link", Store.bitMapText);
            jsonObject.put("status", "Open");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "https://us-central1-faceclass-221a7.cloudfunctions.net/nodecruz/thriftslugadd";

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

                    Log.d(TAG, "onResponse:" + myResponse);
//                    Buying.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    });
                }
            }
        });

        finish();
    }


    private void initializeSpinnerType() {
        Spinner spinner = findViewById(R.id.typeSpin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strItemType = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemSelected: " + strItemType);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void intializeSpinnerGender() {
        Spinner spinner2 = findViewById(R.id.genderSpin);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strItemGender = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void initializeSpinnerSize() {
        Spinner spinner3 = findViewById(R.id.sizeSpin);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.size, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strItemSize = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initializeSpinnerCondition()
    {
        Spinner spinner4 = findViewById(R.id.conditionSpin);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.condition, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strItemCondition = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}