package com.fu.ptlrecyclerview.ptlrecyclerview.PullToLoad;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/9/28.
 */
public  abstract class LoadFooterCreator {

    /**
     * 上拉
     * @param distance 距离
     * @return 是否继续上拉
     */
    protected abstract boolean onStartPull(float distance,int lastState);

    /**
     * 松手加载
     * @param distance 距离
     * @return 是否继续上拉
     */
    protected abstract boolean onReleaseToLoad(float distance,int lastState);

    /**开始加载*/
    protected abstract void onStartLoading();

    /**加载结束*/
    protected abstract void onStopLoad();


    protected abstract View getLoadView(Context context, RecyclerView recyclerView);

    protected View getNoMoreView(Context context, RecyclerView recyclerView) {return null;}

}
