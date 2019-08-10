package com.ricky.md.googleplay.ui.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.RequiresApi;

import com.ricky.md.googleplay.R;
import com.ricky.md.googleplay.utils.UIUtils;

/**
 * * 根据当前状态来显示不同页面的自定义控件
 * *
 * * - 未加载 - 加载中 - 加载失败 - 数据为空 - 加载成功
 * <p>
 * 重写FrameLayout，可以将其传递给viewPager中的Fragment作为布局
 */
public abstract class LoadingPage extends FrameLayout {

    private static final int STATE_LOAD_UNDO = 1;// 未加载
    private static final int STATE_LOAD_LOADING = 2;// 正在加载
    private static final int STATE_LOAD_ERROR = 3;// 加载失败
    private static final int STATE_LOAD_EMPTY = 4;// 数据为空
    private static final int STATE_LOAD_SUCCESS = 5;// 加载成功

    private int mCurrentState = STATE_LOAD_UNDO;// 当前状态

    public LoadingPage(Context context) {
        super(context);
        initView();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }


    private View mLoadingPage;
    private View mErrorPage;
    private View mEmptyPage;
    private View mSuccessPage;

    private void initView() {

        if (mLoadingPage == null) {
            mLoadingPage = UIUtils.inflate(R.layout.page_loading);
            //添加给当前的帧布局
            addView(mLoadingPage);
        }
        if (mErrorPage == null) {
            mErrorPage = UIUtils.inflate(R.layout.page_error);
            Button button1=findViewById(R.id.button1);
            button1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onLoad();
                }
            });

            addView(mErrorPage);
        }
        if (mEmptyPage == null) {
            mEmptyPage = UIUtils.inflate(R.layout.page_empty);
            addView(mEmptyPage);
        }

        showRightPage();

    }
    private void showRightPage() {
        mLoadingPage.setVisibility((mCurrentState==STATE_LOAD_UNDO||mCurrentState==STATE_LOAD_LOADING)?View.VISIBLE:View.GONE);
        mErrorPage.setVisibility((mCurrentState==STATE_LOAD_ERROR)?View.VISIBLE:View.GONE);
        mEmptyPage.setVisibility((mCurrentState==STATE_LOAD_EMPTY)?View.VISIBLE:View.GONE);

        if(mSuccessPage==null&&mCurrentState==STATE_LOAD_SUCCESS){
            mSuccessPage= onCreateSuccessView();
            //应为子类有可能没有实现onCreateSuccessView方法，所以，mSuccessPage有可能为空
            if (mSuccessPage!=null){
                addView(mSuccessPage);
            }
        }
        if (mSuccessPage!=null){
            mSuccessPage.setVisibility((mCurrentState==STATE_LOAD_SUCCESS)?View.VISIBLE:View.GONE);
        }
    }

    //开始加载数据
    public void loadData(){

        System.out.println("------------------loadData");

        //只有在不是  加载中-状态，才能进行加载
        if (mCurrentState!=STATE_LOAD_LOADING){
            mCurrentState=STATE_LOAD_LOADING;

            System.out.println("---------------------loadData->if (mCurrentState!=STATE_LOAD_LOADING){");

            //在子线程中加载数据--从服务器上
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //但是加载数据在子类中执行
                    final ResultState resultState=onLoad();
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultState!=null){
                                //获取传来的信息，赋值给mCurrentState开关
                                mCurrentState=resultState.getState();

                                System.out.println("------------------mCurrentState="+mCurrentState);

                                //判断该page是处于加载成功、没有加载、加载失败 状态，并进行页面刷新
                                showRightPage();
                            }
                        }
                    });
                }
            }).start();
        }


    }

    public abstract View onCreateSuccessView() ;

    // 加载网络数据, 返回值表示请求网络结束后的状态
    public abstract ResultState onLoad();

    //创建枚举--其巧妙之处：
    //让两个类可以传递信息，发出去的乙方添加了对方的可选项，而甲方调用者可以用state用来存放自己的选择，乙方收到分析state即可
    public enum ResultState {
        STATE_SUCCESS(STATE_LOAD_SUCCESS),
        STATE_EMPTY(STATE_LOAD_EMPTY),
        STATE_ERROR(STATE_LOAD_ERROR);

        private int state;

        private ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }

}
