package pl.media30.zamowieniapubliczne;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import pl.media30.zamowieniapubliczne.Adapters.MyAdapter;
import pl.media30.zamowieniapubliczne.Models.ObjectClass;
import pl.media30.zamowieniapubliczne.Models.Zamowienie;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    UltimateRecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    MyAdapter mAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {






        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mRecyclerView = (UltimateRecyclerView) getView().findViewById(R.id.ultimate_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)



        mAdapter = new MyAdapter(new String[]{"adrian","rafa","andrzej"});
        mRecyclerView.setAdapter(mAdapter);


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.mojepanstwo.pl/dane")
                .build();
        MojePanstwoService service = restAdapter.create(MojePanstwoService.class);
        service.listOrders(1, new Callback<Zamowienie>() {
            @Override
            public void success(Zamowienie zamowienie, Response response) {
                Log.d("test", zamowienie.toString());
                ObjectClass oc = new ObjectClass();
                Log.d("jsoooon", oc.toString());
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                // Log error here since request failed
            }
        });


    }




}

