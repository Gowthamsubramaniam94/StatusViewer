package com.saymehow.whatsappstatus;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.List;

public class WSMainActivity extends AppCompatActivity {

    private TabLayout myTabHost;
    private ViewPager myViewPager;
    private Toolbar myToolbar;
    private ViewPagerAdapter myPageAdapter;
    String PHOTOS_TITLE = "Photos";
    String VIDEOS_TITLE = "Videos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        classAndWidgetInitialize();
    }

    private void classAndWidgetInitialize() {

        myTabHost = (TabLayout) findViewById(R.id.screen_gallerytab_tabhost);

        myViewPager = (ViewPager) findViewById(R.id.screen_gallerytab_tabcontent);

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        setupViewPager(myViewPager);

        myViewPager.setOffscreenPageLimit(2);

        myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                myPageAdapter.getItem(position).onResume();

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });


        final int aPageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());

        myViewPager.setPageMargin(aPageMargin);

        myTabHost.setupWithViewPager(myViewPager);

    }

    private void setupViewPager(ViewPager aViewPager) {

        myPageAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        myPageAdapter.getItemPosition(this);

        WSScreenGalleryPhotosCategory aPhotosFragment = new WSScreenGalleryPhotosCategory();
        WSScreenGalleryVideosCategory aVideosFragment = new WSScreenGalleryVideosCategory();

        myPageAdapter.addFragment(aPhotosFragment, PHOTOS_TITLE);
        myPageAdapter.addFragment(aVideosFragment, VIDEOS_TITLE);

        aViewPager.setAdapter(myPageAdapter);

    }

    /**
     * fragement page adapter
     */
    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> myFragmentList = new ArrayList<>();
        private final List<String> myFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager aFragmentmanager) {
            super(aFragmentmanager);
        }

        @Override
        public Fragment getItem(int position) {
            return myFragmentList.get(position);

        }

        @Override
        public int getCount() {
            return myFragmentList.size();
        }

        public void addFragment(Fragment aFragment, String aTitle) {
            myFragmentList.add(aFragment);
            myFragmentTitleList.add(aTitle);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return myFragmentTitleList.get(position);
        }

    }

    /**
     * Method On resume
     */
    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * Method On destroy
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}