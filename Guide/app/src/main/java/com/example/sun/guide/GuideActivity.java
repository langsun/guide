package com.example.sun.guide;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class GuideActivity extends Activity implements View.OnClickListener {
    @Bind(R.id.guide_view_pager)
    ViewPager viewPager;
    @Bind(R.id.guide_view)
    View guideView;
    @Bind(R.id.guide_indicator)
    LinearLayout guideIndicator;
    private int imageArrId[] = {R.drawable.guide_0, R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    private List<ImageView> mImageViewList;
    private GuideAdapter mGuideAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        setupView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void setupView() {
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < imageArrId.length; i++) {
            ImageView imageview = new ImageView(this);
            imageview.setImageResource(imageArrId[i]);
            imageview.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageViewList.add(imageview);
        }

        mGuideAdapter = new GuideAdapter();
        viewPager.setAdapter(mGuideAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == imageArrId.length - 1) {
                    guideView.setVisibility(View.VISIBLE);
                } else {
                    guideView.setVisibility(View.GONE);
                }
                for (int i = 0; i < imageArrId.length; i++) {

                    setIndicator(i, i == position ? true : false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        guideView.setOnClickListener(this);
        initPageIndicator();

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(GuideActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViewList == null ? 0 : mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViewList.get(position));
        }
    }

    private void setIndicator(int i, boolean enabled) {
        View view = guideIndicator.getChildAt(i);
        if (view != null) {
            view.setEnabled(enabled);
        }
    }

    private void initPageIndicator() {
        for (int i = 0; i < imageArrId.length; i++) {
            addPageIndicator(i);
        }

        setIndicator(0, true);
    }

    private void addPageIndicator(int i) {
        View dot = new View(this);
        dot.setBackgroundResource(R.drawable.dot_bg_selector);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
        if (i != 0) {
            params.leftMargin = 40;
        }
        dot.setEnabled(false);
        dot.setLayoutParams(params);
        guideIndicator.addView(dot);
    }
}
