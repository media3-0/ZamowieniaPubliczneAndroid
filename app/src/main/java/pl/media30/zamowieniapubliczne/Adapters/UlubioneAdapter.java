package pl.media30.zamowieniapubliczne.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.media30.zamowieniapubliczne.Models.SingleElement.ObjectClass;
import pl.media30.zamowieniapubliczne.R;
import pl.media30.zamowieniapubliczne.RepositoryClass;
import pl.media30.zamowieniapubliczne.Activities.ZamowienieActivityFragment;

/**
 * Created by Adrian on 2015-08-03.
 * Adapter wyświetlający dane w UltimateRecyclerView znajdującego się w oknie "Obserwowane".
 */
public class UlubioneAdapter extends RecyclerView.Adapter<UlubioneAdapter.ViewHolder> {
    private List<ObjectClass> mDataset;
    static int position;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            RepositoryClass.getInstance().setStronaUlub(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), ZamowienieActivityFragment.class);
            intent.putExtra("Activity","Ulubione");
            intent.putExtra("strona",Integer.toString(getAdapterPosition()));
            view.getContext().startActivity(intent);
            MainAdapter.position = getAdapterPosition();

        }

        public int getPos() {
            return getAdapterPosition();
        }
    }

    public UlubioneAdapter(List<ObjectClass> myDataset) {
        mDataset = myDataset;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        position = vh.getPos();
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TextView textViewName = holder.textViewName;
        TextView textViewCity = holder.textViewCity;

        textViewName.setText("\n" + mDataset.get(position).dataClass.nazwa + "\n");
        textViewCity.setText((position + 1) + ". " + mDataset.get(position).dataClass.zamawiajacy_miejscowosc);
    }

    public int getPos() {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
