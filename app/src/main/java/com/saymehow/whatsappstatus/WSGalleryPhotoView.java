package com.saymehow.whatsappstatus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;

public class WSGalleryPhotoView extends AppCompatActivity {

    public static final String TAG = WSGalleryPhotoView.class
            .getSimpleName();
    private FragmentActivity myContext;
    private ViewPager mPager;
    private static final String[] IMAGES = null;
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    private int selectedPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_gallery_photos_view);

        classAndWidgetInitialize();
    }


    private void classAndWidgetInitialize() {

        myContext = WSGalleryPhotoView.this;

        mPager = (ViewPager) findViewById(R.id.pager);

        Bundle extras = getIntent().getExtras();

        ImagesArray = extras.getStringArrayList("images");

        selectedPosition = extras.getInt("position");

        mPager.setAdapter(new WSImageViewPager(myContext, ImagesArray));

    }

}
