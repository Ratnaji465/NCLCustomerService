package com.ncl.nclcustomerservice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ncl.nclcustomerservice.R;

import butterknife.ButterKnife;

public class ViewImageActivity extends AppCompatActivity{
    ImageView ic_close,image_view;
    String imagePath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        ButterKnife.bind(this);
        imagePath = (String) getIntent().getSerializableExtra("ImagePath");
        ic_close=findViewById(R.id.ic_close);
        image_view=findViewById(R.id.image_view);
        ic_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Glide.with(this)
                .load(imagePath)
                .error(R.drawable.ic_profile)
                .into(image_view);
    }
}
