package com.fu.ptlrecyclerview.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fu.ptlrecyclerview.R;
import com.fu.ptlrecyclerview.ptlrecyclerview.Divider.BaseItemDecoration;
import com.fu.ptlrecyclerview.ptlrecyclerview.HeaderAndFooter.OnItemClickListener;
import com.fu.ptlrecyclerview.ptlrecyclerview.HeaderAndFooter.OnItemLongClickListener;
import com.fu.ptlrecyclerview.ptlrecyclerview.LayoutManager.PTLLinearLayoutManager;
import com.fu.ptlrecyclerview.ptlrecyclerview.PullToLoad.OnLoadListener;
import com.fu.ptlrecyclerview.ptlrecyclerview.PullToLoad.PullToLoadRecyclerView;
import com.fu.ptlrecyclerview.ptlrecyclerview.PullToRefresh.OnRefreshListener;
import com.fu.ptlrecyclerview.ptlrecyclerview.SimpleAdapter.SimpleAdapter;
import com.fu.ptlrecyclerview.ptlrecyclerview.SimpleAdapter.ViewHolder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PullToLoadRecyclerView rcv;
    private ArrayList<String> imgs;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgs = ImgDataUtil.getImgDatas();
        handler = new Handler();

        rcv = (PullToLoadRecyclerView) findViewById(R.id.rcv);
        rcv.setLayoutManager(new PTLLinearLayoutManager(PTLLinearLayoutManager.VERTICAL));
//        设置适配器，封装后的适配器只需要实现一个函数
        rcv.setAdapter(new SimpleAdapter<String>(this, imgs, R.layout.item_test) {
            @Override
            protected void onBindViewHolder(ViewHolder holder, String data) {
                ImgDataUtil.loadImage(mContext, data, holder.<ImageView>getView(R.id.iv));
            }
        });
//        设置刷新监听
        rcv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onStartRefreshing() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgs.clear();
                        imgs.addAll(ImgDataUtil.getImgDatas());
                        rcv.completeRefresh();
                        rcv.setNoMore(false);
                    }
                }, 1000);
            }
        });
//        设置加载监听
        rcv.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onStartLoading(int skip) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<String> newImages = ImgDataUtil.getImgDatas();
                        imgs.addAll(newImages);
                        rcv.completeLoad(newImages.size());
                        if (imgs.size() > 10) {
                            rcv.setNoMore(true);
                        }
                    }
                }, 1000);
            }
        });
//        设置分割线
        rcv.addItemDecoration(new BaseItemDecoration(this,R.color.colorAccent));

        rcv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Toast.makeText(MainActivity.this, "item" + position + " has been clicked", Toast.LENGTH_SHORT).show();
            }
        });
        rcv.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                Toast.makeText(MainActivity.this, "item" + position + " has been long clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
