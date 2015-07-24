package pl.media30.zamowieniapubliczne.Adapters;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import pl.media30.zamowieniapubliczne.Models.DownloadList.DataObjectClass;
import pl.media30.zamowieniapubliczne.ZamowienieActivityFragment;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<DataObjectClass> mDataset;
    static int position;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View v, TextView vh) {
            super(v);
            v.setOnClickListener((View.OnClickListener) this);
            mTextView = vh;
        }
        @Override
        public void onClick(View view) {

            Toast.makeText(view.getContext(), "position = " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(view.getContext(), ZamowienieActivityFragment.class);
            DataObjectClass dataObjectClass = mDataset.get(getAdapterPosition());
            String objToStr= new Gson().toJson(dataObjectClass);
            Bundle objClass = new Bundle();
            objClass.putString("myObject", objToStr);
            intent.putExtras(objClass);
            view.getContext().startActivity(intent);
            MyAdapter.position = getAdapterPosition();

        }

        public int getPos(){
            return getAdapterPosition();
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<DataObjectClass> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        // set the view's size, margins, paddings and layout parameters
        TextView tv = (TextView)v.findViewById(android.R.id.text1);
        ViewHolder vh = new ViewHolder(v, tv);
        position = vh.getPos();
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText((position+1) + ". " + mDataset.get(position).dataClass.nazwa+"\n");
    }
    public int getPos(){
        return position;
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return  mDataset.size();
    }
}