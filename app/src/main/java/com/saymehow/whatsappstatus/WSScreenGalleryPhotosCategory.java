package com.saymehow.whatsappstatus;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gowthamraj on 05-04-2018.
 */

public class WSScreenGalleryPhotosCategory extends Fragment {

    // Set Tag
    public static final String TAG = WSScreenGalleryPhotosCategory.class
            .getSimpleName();
    private WSFragmentManager myFragmentManager;
    protected GridView myGridView;
    private FragmentActivity myContext;
    private View myView = null;
    private ImageAdapter myAdapter;
    private TextView myNoDataTXT;
    ArrayList<String> myImagesArray = new ArrayList<String>();

    /**
     * Method to Create a View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            // Create a View by inflating a layout
            myView = inflater.inflate(R.layout.screen_photo_category,
                    container, false);

            // Inititialise Variables and Objects
            classAndWidgetInitialize(myView);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the View Object
        return myView;
    }

    /**
     * Method to Initialise View and Objects
     *
     * @param aView
     */
    private void classAndWidgetInitialize(View aView) {
        try {
            myContext = getActivity();
            myFragmentManager = new WSFragmentManager(myContext);

            myGridView = (GridView) aView
                    .findViewById(R.id.screen_gallery_gridView);

            myNoDataTXT = (TextView) aView.findViewById(R.id.screen_photos_no_data_TXT);

            getGalleryData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getGalleryData() {

        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses");
        folder.mkdirs();
        File[] aFiles = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png"));
            }
        });

        for (int i = 0; i < aFiles.length; i++) {
            myImagesArray.add(aFiles[i].getAbsolutePath());
        }

        loadValues(aFiles);
    }

    /**
     * Method for Click listerner Of Images
     */
    private void clickListener(final File[] aGalleryInfo) {

        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("images", myImagesArray);
                bundle.putInt("position", position);

                Intent intent = new Intent(myContext, WSGalleryPhotoView.class);
                intent.putExtra("images", myImagesArray);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }


    /**
     * Method to Load values in Grid View
     *
     * @param aGalleryInfo
     */

    public void loadValues(File[] aGalleryInfo) {
        try {
            if (aGalleryInfo != null) {
                myAdapter = new ImageAdapter(getActivity(),
                        aGalleryInfo);
                ((GridView) myGridView).setAdapter(myAdapter);
            } else {
                myNoDataTXT.setVisibility(View.VISIBLE);
            }
            clickListener(aGalleryInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Image adapter Class
     *
     * @author Angler Technologies
     */
    private class ImageAdapter extends BaseAdapter {

        // Fragment Activity context
        private FragmentActivity myContext;

        // Image Url list to Images
        private File[] myGalleryInfos;

        /**
         * Constructor for Image Adapter
         *
         * @param aContext
         * @param aGalleryInfos
         */
        public ImageAdapter(FragmentActivity aContext,
                            File[] aGalleryInfos) {

            myContext = aContext;
            this.myGalleryInfos = aGalleryInfos;

        }

        // ---returns the number of images---
        public int getCount() {
            return myGalleryInfos.length;
        }

        // ---returns the ID of an item---
        public Object getItem(int position) {
            return position;
        }

        // ---returns the ID of an item---
        public long getItemId(int position) {
            return position;
        }


        // ---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent) {

            View MyView = convertView;

            if (convertView == null) {
                // we define the view that will display on the grid
                LayoutInflater inflater = (LayoutInflater) myContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                MyView = inflater.inflate(R.layout.layout_inflate_gallery_grid,
                        parent, false);
            } else {
                MyView = convertView;
            }
            try {
                final ImageView aImageView = (ImageView) MyView
                        .findViewById(R.id.galler_image);
                aImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                List<File> tempList = new ArrayList<File>();
                tempList.addAll(Arrays.asList(myGalleryInfos));

                Glide.with(myContext)
                        .load(new File(String.valueOf(tempList.get(position))))
                        .into(aImageView);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return MyView;
        }
    }


}
