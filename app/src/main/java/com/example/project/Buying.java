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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Buying extends ListActivity {
    Context mContext;
    private static final String TAG = "Buying";
    ArrayList<ItemCust> itemList = new ArrayList<>();
    private ListView listView;
    private String countryNames[] = {
            "NorthFace Shirt",
            "Cute Dress",
            "UCSC Hat",
            "Retro Jacket",
            "UCSC Hoodie",
            "Vintage Overalls",
            "Multicolor Crew Neck",
            "Boots",
            "2 Supreme hats",
            "UCSC Tie-Dye hoodie",
            "New Balance Shoes",
            "Windbreaker Hoodie",
    };

    private String capitalNames[] = {
            "$5",
            "$20",
            "$7",
            "$10",
            "$10",
            "$15",
            "$25",
            "$18",
            "Two for $15, One for $10",
            "$12",
            "$30",
            "$20"
    };

    private Integer imageid[] = {
            R.drawable.northface,
            R.drawable.dress,
            R.drawable.ucschat,
            R.drawable.retro,
            R.drawable.ucschoodie,
            R.drawable.trousers,
            R.drawable.greenjacket,
            R.drawable.boots,
            R.drawable.supremehat,
            R.drawable.ucsctiedye,
            R.drawable.retroshoes,
            R.drawable.vansjacket

    };

    private void setList()
    {


        // Setting header
        TextView textView = new TextView(this);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setTextSize(40);

        textView.setText("Gallery");

        final ListView tlistView=(ListView)findViewById(android.R.id.list);
        tlistView.addHeaderView(textView);


        // For populating list data
//       BuyingListviewRow customCountryList = new BuyingListviewRow(this, countryNames, capitalNames, imageid);

        OkHttpClient client = new OkHttpClient();
        String url = "https://us-central1-faceclass-221a7.cloudfunctions.net/nodecruz/thriftslug";
        Request request = new Request.Builder()
                .url(url)
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
                    Buying.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BuyingListviewRow customList = new BuyingListviewRow(mContext,itemList);
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
        setContentView(R.layout.activity_buying);
        mContext = getApplicationContext();

        listView = findViewById(android.R.id.list);
        setList();

        //get the spinner from the xml.
        //Spinner dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        //String[] items = new String[]{"Bottoms", "Shirts and Polos", "Formal Suits", "Swimwear", "Shoes", "Jackets", "Sleepwear", "Tees and Tanks"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        //dropdown.setAdapter(adapter);

        //Spinner dropdown2 = findViewById(R.id.spinner2);
        //create a list of items for the spinner.
        //tring[] items2 = new String[]{"Price: High to Low", "Price: Low to High"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        //ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        //set the spinners adapter to the previously created one.
        //dropdown2.setAdapter(adapter2);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d(TAG, "onItemClick: " + position);

//                Log.d(TAG, "onItemClick: " + itemList.get(position-1).getItemName());

                Store.selectedItemObject = itemList.get(position-1);


                Intent intent = new Intent(Buying.this, BuyingOnclick.class);
                startActivity(intent);

//                Toast.makeText(getApplicationContext(),"You Selected "+countryNames[position-1]+ " as Country",Toast.LENGTH_SHORT).show();
                }
        });
    }
}