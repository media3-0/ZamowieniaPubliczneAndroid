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

import pl.media30.zamowieniapubliczne.MainActivityFragment;
import pl.media30.zamowieniapubliczne.Models.DownloadList.BaseListClass;
import pl.media30.zamowieniapubliczne.Models.DownloadList.DataObjectClass;
import pl.media30.zamowieniapubliczne.Models.SingleElement.BaseClass;
import pl.media30.zamowieniapubliczne.Models.SingleElement.DataClass;
import pl.media30.zamowieniapubliczne.Models.SingleElement.ObjectClass;
import pl.media30.zamowieniapubliczne.ZamowienieActivity;
import pl.media30.zamowieniapubliczne.ZamowienieActivityFragment;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<DataObjectClass> mDataset;

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

            Toast.makeText(view.getContext(), "position = " + getPosition(), Toast.LENGTH_SHORT).show();
            Bundle koszyk = new Bundle();

            String wpisanyTekst = mDataset.get(getPosition()).id+"";
            koszyk.putString("dane", wpisanyTekst);
/*
            Bundle przetarg = new Bundle();
            ObjectClass dataset = mDataset.get(getPosition()).dataset.get(getPosition()).objectClass.dataClass"";
            przetarg.view.getContext().
                    Intent activity = new Intent(MainActivityFragment.this,ZamowienieActivityFragment.class);
            activity.putExtra("myObject", new Gson().toJson(myobject));
            startActivity(activity);*/
            Intent intent = new Intent(view.getContext(), ZamowienieActivityFragment.class);
                                 //mDataset.get(getPosition()).id+"";
            DataObjectClass dataObjectClass = mDataset.get(getPosition());
            //BaseClass baseClass = mDataset.get(getPosition()).dataset.get(getPosition());
            String objToStr= new Gson().toJson(dataObjectClass);
            Bundle objClass = new Bundle();
            objClass.putString("myObject", objToStr);
          //  intent.putExtras(objClass);
            intent.putExtras(koszyk);
            view.getContext().startActivity(intent);



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
        //...//co podpisuje pod widok
        TextView tv = (TextView)v.findViewById(android.R.id.text1);
        ViewHolder vh = new ViewHolder(v, tv);
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
       // holder.mTextView.setText(mDataset.get(position).id);
        holder.mTextView.setText(mDataset.get(position).slug);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}