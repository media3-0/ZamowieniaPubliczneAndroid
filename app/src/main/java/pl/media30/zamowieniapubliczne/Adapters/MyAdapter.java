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
import pl.media30.zamowieniapubliczne.R;
import pl.media30.zamowieniapubliczne.ZamowienieActivityFragment;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<DataObjectClass> mDataset;
    static int position;



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
       public TextView textViewName;
        public TextView textViewCity;
        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener((View.OnClickListener) this);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewCity = (TextView) itemView.findViewById(R.id.textViewCity);
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
                .inflate(R.layout.cards_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
       ///// TextView tvn = (TextView)v.findViewById(android.R.id.text1);
        //v.setOnClickListener((View.OnClickListener) this);
    /////    TextView tvc =  (TextView)v.findViewById(android.R.id.text2);
        ViewHolder vh = new ViewHolder(v);
        position = vh.getPos();
        return vh;
    }




    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        TextView textViewName = holder.textViewName;
        TextView textViewCity = holder.textViewCity;



        textViewName.setText((position+1) + ". " + mDataset.get(position).dataClass.nazwa+"\n");
        textViewCity.setText(mDataset.get(position).dataClass.zamawiajacy_miejscowosc+"\n");
    }
    public int getPos(){
        return position;
    }

    @Override
    public int getItemCount() {
        return  mDataset.size();
    }
}