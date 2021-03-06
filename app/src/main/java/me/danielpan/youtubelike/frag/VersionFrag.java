package me.danielpan.youtubelike.frag;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import me.danielpan.youtubelike.adapter.CstmAdapter;
import me.danielpan.youtubelike.R;
import me.danielpan.youtubelike.util.ColorGenerator;

/**
 * A simple {@link Fragment} subclass.
 */
public class VersionFrag extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private String[] versions;
    private int ydy = 0;

    public static VersionFrag newInstance() {
        return new VersionFrag();
    }

    public VersionFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_layout, container, false);
        rootView.setBackgroundColor(ColorGenerator.getRandomColor());
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.swipe_refresh_color_scheme_blue, R.color.swipe_refresh_color_scheme_red,
                R.color.swipe_refresh_color_scheme_green, R.color.swipe_refresh_color_scheme_blue_1, R.color.swipe_refresh_color_scheme_red_1,
                R.color.swipe_refresh_color_scheme_green_1);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
                Toast.makeText(getActivity(), "Load Data normally at this time", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        versions = getResources().getStringArray(R.array.android_ver_titles);
        recyclerView.setAdapter(new CstmAdapter(getActivity(), versions, CstmAdapter.TYPE_VERSION));
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int offset = dy - ydy;
                ydy = dy;
                boolean shouldRefresh = (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0)
                        && (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING) && offset > 30;
                if (shouldRefresh) {
                    swipeRefreshLayout.setRefreshing(true);
                    return;
                }
                boolean shouldPullUpRefresh = linearLayoutManager.findLastCompletelyVisibleItemPosition() == linearLayoutManager.getChildCount() - 1
                        && recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING && offset < -30;
                if (shouldPullUpRefresh) {
                    swipeRefreshLayout.setRefreshing(true);
                    return;
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return rootView;
    }
}
