package com.example.project;

import android.graphics.Bitmap;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.BitmapFactory;
import com.example.project.R;
import android.util.Base64;

import java.util.ArrayList;

public class BuyingListviewRow extends ArrayAdapter<ItemCust> {
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

    public BuyingListviewRow(Context context,  ArrayList<ItemCust> objects) {
        super(context,R.layout.layout_buying_rows ,objects);
        mObjects = objects;
        mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if(convertView==null)
            row = inflater.inflate(R.layout.layout_buying_rows, null, true);
        TextView textViewCountry = (TextView) row.findViewById(R.id.textViewCountry);
        TextView textViewCapital = (TextView) row.findViewById(R.id.textViewCapital);
        ImageView imageFlag = (ImageView) row.findViewById(R.id.imageViewFlag);

        textViewCountry.setText(getItem(position).getItemName());
        textViewCapital.setText(getItem(position).getItemPrice());

        Bitmap bm = StringToBitMap(getItem(position).getItemPhoto());
        imageFlag.setImageBitmap(bm);


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
