package com.ricky.md.googleplay.ui.adapter;

import android.content.IntentFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.ricky.md.googleplay.ui.hodler.BaseHodler;
import com.ricky.md.googleplay.ui.hodler.MoreHodler;
import com.ricky.md.googleplay.utils.UIUtils;

import java.util.ArrayList;

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    //注意: 此处必须要从0开始写
    private static final int TYPE_NORMAL = 0;// 正常布局类型
    private static final int TYPE_MORE = 1;// 加载更多类型

    private ArrayList<T> data;
    public MyBaseAdapter(ArrayList<T> data){
        this.data=data;
    }
    @Override
    public int getCount() {
        //+1 增加加载更多布局数量
        return data.size()+1 ;
    }

    @Override
    public T getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * 	 返回布局类型个数
     * @return
     */
    @Override
    public int getViewTypeCount() {
        //返回两种类型,普通布局+加载更多布局
        return 2;
    }

    /**
     * 	 返回当前位置应该展示那种布局类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        //表示最后一个item
        if (position==getCount()-1){
            return TYPE_MORE;
        }else {
            return getInnerType();
        }
    }

    /**
     *    创建这个方法，是为了防止其他listView里面的条目item不仅仅只有两种，可以通过重写该方法，创建多个类型的item
     * @return
     */
    public int getInnerType(){
        // 子类可以重写此方法来更改返回的布局类型
        return TYPE_NORMAL;// 默认就是普通类型
    }

    /**
     *    其作用同上
     * @return
     */
    public boolean hasMore(){
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BaseHodler<T> baseHodler=null;
        if (view==null){
            // 1. 加载布局文件
            // 2. 初始化控件 findViewById
            // 3. 打一个标记tag
            //子类返回具体对象

            if (getItemViewType(i)==TYPE_MORE){
                //判断item的类型，来显示加载更多的item
                baseHodler= (BaseHodler<T>) new MoreHodler(hasMore());

            }else {
                baseHodler = getHodler();
            }
        }else {
            baseHodler= (BaseHodler<T>) view.getTag();
        }
        if (getItemViewType(i)==TYPE_MORE){
            //加载更多布局
            // 一旦加载更多布局展示出来, 就开始加载更多
            // 只有在有更多数据的状态下才加载更多
            //注意此处应该是MoreHodler对象，此处显示加载更多的item布局类型
            MoreHodler moreHodler = (MoreHodler) baseHodler;
            //只有状态为  可以加载更多STATE_MORE_MORE  才能继续加载数据
            if (moreHodler.getData()==MoreHodler.STATE_MORE_MORE){
                loadMore(moreHodler);
            }


        }else {
            baseHodler.setData(getItem(i));
        }
        // 4. 根据数据来刷新界面
        return baseHodler.getRootView();
    }
    // 返回当前页面的holder对象, 必须子类实现
    public abstract BaseHodler<T> getHodler();

    private boolean isLoading=false;

    //加载更多数据
    public void loadMore(final BaseHodler<Integer> baseHodler){
        if (!isLoading){
            new Thread(new Runnable() {
                @Override
                public void run() {

                    //正在加载数据，将开关关闭
                    isLoading=true;

                    //具体加载数据，需要子类来实现
                    final ArrayList<T> loadList = onLoadMore();
                    //更新了UI，需要更换为主线程
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (loadList!=null){
                                //如果加载的list数据集中小于20个，则设置为，没有更多数据
                                if (loadList.size()<20){
                                    baseHodler.setData(MoreHodler.STATE_MORE_NONE);
                                    Toast.makeText(UIUtils.getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                                }else {
                                    //说明还有更多数据，需要加载，将标记设置为  可以加载更多
                                    baseHodler.setData(MoreHodler.STATE_MORE_MORE);
                                }
                                //将加载的数据，添加至原数据集合中
                                data.addAll(loadList);
                                // 刷新界面
                                MyBaseAdapter.this.notifyDataSetChanged();

                            }else {
                                //加载的数据为空，说明加载失败
                                baseHodler.setData(MoreHodler.STATE_MORE_ERROR);
                            }
                            //数据加载完毕，将开关打开
                            isLoading=false;
                        }
                    });

                }
            }).start();
        }

    }

    //具体加载数据的操作不同，将其交给子类去实现
    public abstract ArrayList<T> onLoadMore();

}
