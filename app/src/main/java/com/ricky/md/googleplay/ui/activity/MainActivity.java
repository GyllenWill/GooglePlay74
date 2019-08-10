package com.ricky.md.googleplay.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.ricky.md.googleplay.R;
import com.ricky.md.googleplay.ui.fragment.BaseFragment;
import com.ricky.md.googleplay.ui.fragment.FragmentFactory;
import com.ricky.md.googleplay.utils.UIUtils;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout=findViewById(R.id.pager_tab);
        mViewPager=findViewById(R.id.viewpager);



        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myAdapter);
        //绑定
        mTabLayout.setupWithViewPager(mViewPager);

        //注意，viewpager的setOnPageChangeListener过时了，使用addOnPageChangeListener
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //开始加载数据
                BaseFragment fragment=FragmentFactory.createFragment(position);
                fragment.loadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private class MyAdapter extends FragmentPagerAdapter {

        private final String[] titleName;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            titleName = UIUtils.getStringArray(R.array.tab_names);
        }

        // 返回页签标题
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleName[position];
        }

        // 返回当前页面位置的fragment对象
        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        // fragment数量
        @Override
        public int getCount() {
            return titleName.length;
        }
    }
}
