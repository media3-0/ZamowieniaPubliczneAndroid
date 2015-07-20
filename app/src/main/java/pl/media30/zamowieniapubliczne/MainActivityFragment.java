package pl.media30.zamowieniapubliczne;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import pl.media30.zamowieniapubliczne.Adapters.MyAdapter;
import pl.media30.zamowieniapubliczne.Models.DownloadList.BaseListClass;
import pl.media30.zamowieniapubliczne.Models.DownloadList.DataObjectClass;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment  {

    UltimateRecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    MyAdapter mAdapter;


/*
    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
    */
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
       // mAdapter = new MyAdapter(new String[]{"Trwa wczytywanie danych"});
 //       mRecyclerView.setAdapter(mAdapter);



/*Wyswietlenie pojedynczego przetargu. Wyswietlony kod kodu pocztowego i strona www.
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.mojepanstwo.pl/dane/")
                .build();
        MojePanstwoService service = restAdapter.create(MojePanstwoService.class);

            service.singleOrder(1, new Callback<BaseClass>() {
            @Override
            public void success(BaseClass bc, Response response) {
                Log.d("tester", bc.objectClass.dataClass.kod_pocztowy_id);
                mAdapter = new MyAdapter(new String[]{"adrian","rafa","Kod Kodu pocztowego: " +  bc.objectClass.dataClass.kod_pocztowy_id.toString(), "Strona www: "+ bc.objectClass.dataClass.zamawiajacy_www.toString(), "kolejny"});
                mAdapter = new MyAdapter(new String[]{"adrian","rafa","Kod Kodu pocztowego: " +  bc.objectClass.dataClass.kod_pocztowy_id.toString(), "Strona www: "+ bc.objectClass.dataClassdata.zamawiajacy_www.toString(), "kolejny"});
                mRecyclerView.setAdapter(mAdapter);
            }

*/

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.mojepanstwo.pl/dane/")
                .build();
        MojePanstwoService service = restAdapter.create(MojePanstwoService.class);

        service.listOrders(new Callback<BaseListClass>() {

            @Override
            public void success(BaseListClass blc, Response response) {
                List<DataObjectClass> dataObjectList = blc.searchClass.dataobjects; //new ArrayList<DataObjectClass>();
                //List<DataObjectClass> lista = new List<DataObjectClass>();
                String output = "";
                int maxLength = 50;
             /*   for (int i = 0; i <= 19; i++) {
                    if (blc.searchClass.dataobjects.get(i). dataClass.nazwa.length() > maxLength) {
                        output += blc.searchClass.dataobjects.get(i).dataClass.nazwa.substring(0, maxLength) + "...\n\n";
                    } else {
                        output += blc.searchClass.dataobjects.get(i).dataClass.nazwa + "\n\n";
                    }
                }*/

                Log.d("!!!!!!!!",dataObjectList.get(0).global_id );
               // Logger. ("geee: "+dataObjectList.size());
                 mAdapter = new MyAdapter(dataObjectList);
                mRecyclerView.setAdapter(mAdapter);//Streing[] {}
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                // Log error here since request failed
                Log.d("Wystapil blad", "!!!!!!!!!!!!!!!!!!!!!");
            }
        });

    }

}

