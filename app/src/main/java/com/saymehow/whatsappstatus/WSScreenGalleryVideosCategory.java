package com.saymehow.whatsappstatus;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class WSScreenGalleryVideosCategory extends Fragment {

    // Set Tag
    public static final String TAG = WSScreenGalleryVideosCategory.class
            .getSimpleName();

    protected GridView myGridView;
    private FragmentActivity myContext;
    private View myView = null;
    private VideosAdapter myAdapter;
    private TextView myNoDataTXT;

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
                return (name.endsWith(".mp4"));
            }
        });
        loadValues(aFiles);
    }

    /**
     * Method for Click listerner Of Images
     */
    private void clickListener(final File[] aGalleryInfo) {

       /* myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle aBundle = new Bundle();
                aBundle.putInt(POSITION, position);
                aBundle.putString(GALLERY_CATEGORY_NAME, aGalleryInfo.get(position).getCategoryName());
                aBundle.putString(TYPE, aGalleryInfo.get(position).getType());
                myFragmentMananager.updateContent(
                        new RTCScreenGalleryPhotoView(), null,
                        RTCScreenGalleryPhotoView.TAG, myCategoryNameStr,
                        aBundle);
            }
        });*/
    }


    /**
     * Method to Load values in Grid View
     *
     * @param aGalleryInfo
     */

    public void loadValues(File[] aGalleryInfo) {
        try {
            if (aGalleryInfo != null) {
                myAdapter = new VideosAdapter(getActivity(),
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

    class VideosAdapter extends BaseAdapter {
        private Context context;
        private File[] videos;

        public VideosAdapter(Context context, File[] videos) {
            this.context = context;
            this.videos = videos;
        }

        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @TargetApi(16)
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) myContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                View gridView = inflater.inflate(R.layout.layout_inflate_gallery_grid, null);
                //gridView.setLayoutParams(new ViewGroup.LayoutParams(70, 70));
                ImageView img = (ImageView) gridView.findViewById(R.id.galler_image);
                Glide.with(myContext).load(this.videos[position].getAbsolutePath()).into(img);
                Log.i("v", this.videos[position].getName());
                return gridView;
            }
            View gridView = convertView;
            ImageView img = (ImageView) gridView.findViewById(R.id.galler_image);
            Glide.with(myContext).load(this.videos[position].getAbsolutePath()).crossFade().into(img);
            return gridView;
        }

        public int getCount() {
            if (this.videos != null) {
                return this.videos.length;
            }
            return 0;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }
    }


}
