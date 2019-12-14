package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class addingPhoto extends AppCompatActivity {

   ImageView imageView;
   Button uploadBtn;
   TextView tvImageText;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    private static final String TAG = "addingPhoto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_photo);

        imageView = (ImageView)findViewById(R.id.imageViewI);
        uploadBtn = (Button)findViewById(R.id.uploadImage);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

            }
        });



    }

    private void openGallery(){


        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
//            Log.d(TAG, "Image saved: " + getStringImage(imageView2Bitmap((imageView))));
//            tvImageText = findViewById(R.id.texviewtbitmapimage);
//            tvImageText.setText(getStringImage(imageView2Bitmap((imageView))));

//            Log.d(TAG, "getImage: " +  tvImageText.getText().toString());

            Store.bitMapText = getStringImage(imageView2Bitmap((imageView)));
            finish();
        }
    }

    private String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }


    private Bitmap imageView2Bitmap(ImageView view){

        Bitmap bitmap = ((BitmapDrawable)view.getDrawable()).getBitmap();
        return bitmap;
    }

}