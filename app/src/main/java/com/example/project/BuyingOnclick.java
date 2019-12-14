package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BuyingOnclick extends AppCompatActivity {


    TextView tvName, tvType, tvGender, tvSize, tvBrand, tvColor, tvCondition, tvPrice, tvPhoto, tvID;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buying_onclick);

        initialization();

        Button buyNowBtn = findViewById(R.id.buyNow);
        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it2 = new Intent(BuyingOnclick.this, BuyNow.class);
                startActivity(it2);
            }
        });






    }


    private void initialization()
    {
        tvName = findViewById(R.id.textviewDetailName);
        tvName.setText(Store.selectedItemObject.getItemName());

        iv = findViewById(R.id.imageViewDetailItem);

        Bitmap bm = StringToBitMap(Store.selectedItemObject.getItemPhoto());
        iv.setImageBitmap(bm);

        tvType = findViewById(R.id.textviewDetailType);
        tvType.setText(Store.selectedItemObject.getItemType());

        tvGender = findViewById(R.id.textviewDetailGender);
        tvGender.setText(Store.selectedItemObject.getItemGender());

        tvSize = findViewById(R.id.textviewDetailSize);
        tvSize.setText(Store.selectedItemObject.getItemSize());

        tvBrand = findViewById(R.id.textviewDetailBrand);
        tvBrand.setText(Store.selectedItemObject.getItemBrand());

        tvColor = findViewById(R.id.textviewDetailColor);
        tvColor.setText(Store.selectedItemObject.getItemColor());

        tvCondition = findViewById(R.id.textviewDetailCondition);
        tvCondition.setText(Store.selectedItemObject.getItemCondition());

        tvPrice = findViewById(R.id.textviewDetailPrice);
        tvPrice.setText("\n"+Store.selectedItemObject.getItemPrice());

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
