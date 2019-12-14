package com.example.project;

import android.app.ListActivity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.BuyingListviewRow;
import com.example.project.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MyItems extends ListActivity {
    Context mContext;
    private static final String TAG = "Myitems";
    ArrayList<ItemCust> itemList = new ArrayList<>();
    private ListView listView;





    private void setList()
    {


        // Setting header
        TextView textView = new TextView(this);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setTextSize(40);
        textView.setText("My Postings");

        final ListView tlistView=(ListView)findViewById(android.R.id.list);
        tlistView.addHeaderView(textView);


        // For populating list data
//       BuyingListviewRow customCountryList = new BuyingListviewRow(this, countryNames, capitalNames, imageid);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", Store.myEmail);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String url = "https://us-central1-faceclass-221a7.cloudfunctions.net/nodecruz/thriftslugmyitems";
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

                    try{
                        JSONArray jArray = new JSONArray(myResponse);

                        for(int i = 0; i < jArray.length(); i++)
                        {
                            JSONObject obj = jArray.getJSONObject(i);

//                            Log.d(TAG, "jsonobject: " + obj.getString("seller_ID"));

                            ItemCust tItem= new ItemCust(obj.getString("ID"),obj.getString("name"),obj.getString("type"),obj.getString("gender"),obj.getString("size"),obj.getString("brand"),obj.getString("color"),obj.getString("condition_item"),obj.getString("price"),
                                    obj.getString("pic_link"), obj.getString("seller_ID"));

                            itemList.add(tItem);
                            Log.d(TAG, "price: " + tItem.getItemPrice());
                        }




                    }
                    catch (JSONException e)
                    {

                    }

                    Log.d(TAG, "onResponse:" + myResponse);
                    MyItems.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyItemsListViewRow customList = new MyItemsListViewRow(mContext,itemList);
                            Log.d(TAG, "size of arraylist: "+ itemList.size());
                            tlistView.setAdapter(customList);

                        }
                    });
                }
            }
        });



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myitems);
        mContext = getApplicationContext();

        listView = findViewById(android.R.id.list);
        setList();

    }
}