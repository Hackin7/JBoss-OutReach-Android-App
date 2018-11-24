package org.jbossoutreach.jbossoutreach;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private ArrayList<String> data = null;
    private boolean isRepo;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;
        public MyViewHolder(View v) {
            super(v);
            mView = v;
        }
        public void setText(String item) {
            TextView label = (TextView) mView.findViewById(R.id.label);
            label.setText(item);
        }
        public void setContributorsListener(final String item){
            mView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Context context = view.getContext();
                    Intent contributorsIntent = new Intent(context,RepoContributors.class);
                    contributorsIntent.putExtra("repository", item);//contributors.get(position));
                    context.startActivity(contributorsIntent);
                }
            });
        }
        public void clearListener(){
            mView.setOnClickListener(null);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter(ArrayList<String> givenData,boolean givenIsRepo){
        data = givenData;
        isRepo = givenIsRepo;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        //...
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mView
        holder.setText(data.get(position));
        if(isRepo){
            holder.setContributorsListener(data.get(position));
        }else{
            holder.clearListener();
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.size();
    }


}