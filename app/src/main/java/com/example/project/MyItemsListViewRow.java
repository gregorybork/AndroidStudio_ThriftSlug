package com.example.project;

import android.graphics.Bitmap;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.BitmapFactory;
import com.example.project.R;
import android.util.Base64;

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

public class MyItemsListViewRow extends ArrayAdapter<ItemCust> {
//    private String[] countryNames;
//    private String[] capitalNames;
//    private Integer[] imageid;
//    private Activity context;

    private static final String TAG = "BuyingListviewRow";
    ArrayList<ItemCust> mObjects;
    private Context mContext;

//    public BuyingListviewRow(Activity context, String[] countryNames, String[] capitalNames, Integer[] imageid) {
//        super(context, R.layout.layout_buying_rows, countryNames);
//        this.context = context;
//        this.countryNames = countryNames;
//        this.capitalNames = capitalNames;
//        this.imageid = imageid;
//
//    }

    public MyItemsListViewRow(Context context,  ArrayList<ItemCust> objects) {
        super(context,R.layout.layout_buying_rowsforseller ,objects);
        mObjects = objects;
        mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if(convertView==null)
            row = inflater.inflate(R.layout.layout_buying_rowsforseller, null, true);
        TextView textViewCountry = (TextView) row.findViewById(R.id.textViewCountry);
        TextView textViewCapital = (TextView) row.findViewById(R.id.textViewCapital);
        ImageView imageFlag = (ImageView) row.findViewById(R.id.imageViewFlag);

        textViewCountry.setText(getItem(position).getItemName());
        textViewCapital.setText(getItem(position).getItemPrice());

        Bitmap bm = StringToBitMap(getItem(position).getItemPhoto());
        imageFlag.setImageBitmap(bm);


        final Button delBtn = row.findViewById(R.id.btnDelItem);


        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(getItem(position).getItemId());


                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", getItem(position).getItemId());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                String url = "https://us-central1-faceclass-221a7.cloudfunctions.net/nodecruz/thriftslugdelitem";
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

                    }
                });


                mObjects.remove(getItem(position));
                notifyDataSetChanged();
            }
        });

        return  row;
    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
