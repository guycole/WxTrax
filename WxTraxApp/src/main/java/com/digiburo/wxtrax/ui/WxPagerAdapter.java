package com.digiburo.wxtrax.ui;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digiburo.wxtrax.R;

public class WxPagerAdapter extends FragmentStatePagerAdapter {
    public static final String LOG_TAG = WxPagerAdapter.class.getName();



    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object
    ) {
        // Just remove the view from the ViewPager
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // Ensure that the LayoutInflater is instantiated
        if (mInflater == null) {
            mInflater = LayoutInflater.from(MainActivity.this);
        }

        // Get the item for the requested position
        final ContentItem item = mItems.get(position);

        // The view we need to inflate changes based on the type of content
        switch (item.contentType) {
            case ContentItem.CONTENT_TYPE_TEXT: {
                // Inflate item layout for text
                TextView tv = (TextView) mInflater
                        .inflate(R.layout.item_text, container, false);

                // Set text content using it's resource id
                tv.setText(item.contentResourceId);

                // Add the view to the ViewPager
                container.addView(tv);
                return tv;
            }
            case ContentItem.CONTENT_TYPE_IMAGE: {
                // Inflate item layout for images
                ImageView iv = (ImageView) mInflater
                        .inflate(R.layout.item_image, container, false);

                // Load the image from it's content URI
                iv.setImageURI(item.getContentUri());

                // Add the view to the ViewPager
                container.addView(iv);
                return iv;
            }
        }

        return null;
    }
}
