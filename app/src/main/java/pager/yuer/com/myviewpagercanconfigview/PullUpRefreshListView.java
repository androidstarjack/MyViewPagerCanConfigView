package pager.yuer.com.myviewpagercanconfigview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class PullUpRefreshListView extends ListView implements AbsListView.OnScrollListener {

    /** 底部显示的页面 */
    private View footerView = null;
    /** 上拉刷新的ListView的回调监听 */
    private MyPullUpListViewCallBack myPullUpListViewCallBack;
    int downY;
    int upY;
    private boolean isBottom=false;
    public PullUpRefreshListView(Context context) {
        super(context);
        initListView();
    }

    public PullUpRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initListView();
    }

    /**
     * 初始化ListView
     */
    private void initListView() {

        // 为ListView设置滑动监听
        setOnScrollListener(this);
        // 去掉底部分割线
        setFooterDividersEnabled(true);
    }

    /**
     * 初始化话底部页面
     */
    public void initBottomView(View mfooterView) {
        footerView=mfooterView;
        if (footerView != null) {
            addFooterView(footerView);
        }

    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        if (footerView != null) {
            //判断可视Item是否能在当前页面完全显示
            if (visibleItemCount == totalItemCount) {
                // removeFooterView(footerView);
                footerView.setVisibility(View.GONE);//隐藏底部布局
            } else {
                // addFooterView(footerView);
                footerView.setVisibility(View.VISIBLE);//显示底部布局
            }
            if (firstVisibleItem+visibleItemCount==totalItemCount){
                isBottom=true;
            }else {
                isBottom=false;
            }
        }

    }

    public void setMyPullUpListViewCallBack(
            MyPullUpListViewCallBack myPullUpListViewCallBack) {
        this.myPullUpListViewCallBack = myPullUpListViewCallBack;
    }

    /**
     * 上拉刷新的ListView的回调监听
     *
     */
    public interface MyPullUpListViewCallBack {

        void scrollBottomState();
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_UP :
                upY= (int) ev.getY();
                if ((upY-downY)<0 && upY!=0){//向上滑动而且松手
                    if (isBottom){
                        myPullUpListViewCallBack.scrollBottomState();
                    }

                }
                break;

        }
        return super.onTouchEvent(ev);
    }
}