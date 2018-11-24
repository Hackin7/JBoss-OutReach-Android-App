package org.jbossoutreach.jbossoutreach;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout pullToRefresh;
    private ArrayList<String> repositories = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Repositories Dataset
        mAdapter = new ListAdapter(repositories,true);
        mRecyclerView.setAdapter(mAdapter);
        //repositories.add("Hi");
        refreshData();

        pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                /*      Snackbar.make(
                                    findViewById(R.id.layout),
                                    data,
                                    Snackbar.LENGTH_LONG
                            ).show();
                        Snackbar.make(
                                findViewById(R.id.layout),
                                "API Error",
                                Snackbar.LENGTH_LONG
                        ).show();*/
                pullToRefresh.setRefreshing(false);
                }
        });

    }
    private void refreshData(){
        try {
            String repoUrl = "https://api.github.com/users/JBossOutreach/repos";
            ArrayList<String> repo = new JSONAPI().execute(repoUrl,"name").get();
            mAdapter = new ListAdapter(repo,true);
            mRecyclerView.swapAdapter(mAdapter, false);
        }
        catch (ExecutionException e) {}
        catch (InterruptedException e) {}
    }

}
