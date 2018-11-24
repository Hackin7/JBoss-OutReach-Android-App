package org.jbossoutreach.jbossoutreach;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class RepoContributors extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout pullToRefresh;
    private ArrayList<String> contributors = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        final String repository = intent.getStringExtra("repository");
        setTitle(repository);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Repositories Dataset
        mAdapter = new ListAdapter(contributors,false);
        mRecyclerView.setAdapter(mAdapter);
        //repositories.add("Hi");
        refreshData(repository);

        pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(repository);
                pullToRefresh.setRefreshing(false);
            }
        });

    }
    private void refreshData(String repository){
        try {
            String contributorsUrl = "https://api.github.com/repos/JBossOutreach/"+repository+"/contributors";
            //String contributorsUrl = "https://api.github.com/repos/JBossOutreach/lead-management-android/contributors";
            ArrayList<String> people = new JSONAPI().execute(contributorsUrl,"login").get();
            mAdapter = new ListAdapter(people,false);
            mRecyclerView.swapAdapter(mAdapter, false);
        }
        catch (ExecutionException e) {}
        catch (InterruptedException e) {}
    }

}
