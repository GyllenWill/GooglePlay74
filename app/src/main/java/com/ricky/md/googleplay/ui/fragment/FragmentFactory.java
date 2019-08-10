package com.ricky.md.googleplay.ui.fragment;

import androidx.fragment.app.Fragment;

import java.util.HashMap;

public class FragmentFactory {

    private static HashMap<Integer, BaseFragment> fragmentHashMap= new HashMap<Integer, BaseFragment>();


    //直接静态方法更便捷
    public static BaseFragment createFragment(int position) {

        // 先从集合中取, 如果没有,才创建对象, 提高性能

        BaseFragment fragment = fragmentHashMap.get(position);

        if (fragment==null){
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new AppFragment();
                    break;
                case 2:
                    fragment = new GameFragment();
                    break;
                case 3:
                    fragment = new SubjectFragment();
                    break;
                case 4:
                    fragment = new RecommendFragment();
                    break;
                case 5:
                    fragment = new CategoryFragment();
                    break;
                case 6:
                    fragment = new HotFragment();
                    break;

                default:
                    break;
            }

            fragmentHashMap.put(position,fragment);
        }

        return fragment;

    }


}
