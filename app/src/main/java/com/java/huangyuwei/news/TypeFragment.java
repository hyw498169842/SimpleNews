package com.java.huangyuwei.news;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blcodes.views.refresh.BounceCallBack;
import com.blcodes.views.refresh.BounceLayout;
import com.blcodes.views.refresh.EventForwardingHelper;
import com.blcodes.views.refresh.NormalBounceHandler;
import com.blcodes.views.refresh.footer.DefaultFooter;
import com.blcodes.views.refresh.header.DefaultHeader;

import com.java.huangyuwei.R;
import com.java.huangyuwei.news.newsparser.RefreshServer;
import com.java.huangyuwei.news.newsparser.Server;
import com.java.huangyuwei.news.newssaver.News2Json;

public class TypeFragment extends Fragment {
    int page;
    String type;
    private Activity savedActivity;
    public TypeFragment(String _type) {
        super();
        type = _type;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list, container, false);

        // https://github.com/tangxianqiang/LightRefresh
        FrameLayout rootView = view.findViewById(R.id.fl_root);
        final BounceLayout bounceLayout = view.findViewById(R.id.bl);
        bounceLayout.setHeaderView(new DefaultHeader(savedActivity),rootView);//if HeaderView is null,it just bounce.
        bounceLayout.setFooterView(new DefaultFooter(savedActivity),rootView);
        ScrollView scrollView = view.findViewById(R.id.scroll_view);
        bounceLayout.setBounceHandler(new NormalBounceHandler(), scrollView);
        bounceLayout.setEventForwardingHelper(new EventForwardingHelper() {
            @Override
            public boolean notForwarding(float downX, float downY, float moveX, float moveY) {
                return true;
            }
        });


        final LinearLayout layout = (LinearLayout)view.findViewById(R.id.news_list_layout);
        final TypeFragment _this = this;
        final Handler mainThreadHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what == 1) {
                    layout.removeAllViews();
                }
                String[][] s = (String[][])msg.obj;
                String[][] newsList = News2Json.getNewsList(savedActivity.getFilesDir().getPath());
                if(msg.what == 2 && newsList != null) {
                    s = newsList;
                    layout.removeAllViews();
                }
                int length = s == null ? 0 : s.length;
                for(int i = 0; i < length; i++) {
                    NewsLayout newsLayout = new NewsLayout(savedActivity, s[i][0], s[i][1], s[i][2], s[i][3], s[i][4]);
                    boolean read = false;
                    if(newsList != null) {
                        for (int j = 0; j < newsList.length; j++) {
                            if (s[i][0].equals(newsList[j][0])) {
                                read = true;
                                break;
                            }
                        }
                    }
                    if(read) {
                        newsLayout.setBackgroundColor(Color.rgb(0xe0,0xe0,0xe0));
                    }
                    LinearLayout.LayoutParams newsParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    newsParams.setMargins(25,15,25,15);
                    newsLayout.setLayoutParams(newsParams);
                    layout.addView(newsLayout);
                }
                layout.invalidate();
                bounceLayout.setRefreshCompleted();
                bounceLayout.setLoadingMoreCompleted();
                return false;
            }
        });

        bounceLayout.setBounceCallBack(new BounceCallBack() {
            @Override
            public void startRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RefreshServer refreshServer = new RefreshServer(mainThreadHandler, type);
                        refreshServer.start();

                    }
                },100);
            }

            @Override
            public void startLoadingMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page += 1;
                        Server refreshServer = new Server(mainThreadHandler, type, page);
                        refreshServer.start();

                    }
                },100);
            }
        });
        page = 1;
        Server s = new Server(mainThreadHandler, type, page);
        s.start();
        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        savedActivity = (Activity)context;
    }
}
