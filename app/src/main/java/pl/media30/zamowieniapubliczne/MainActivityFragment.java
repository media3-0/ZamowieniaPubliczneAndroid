package pl.media30.zamowieniapubliczne;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class MainActivityFragment extends Fragment {

    UltimateRecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    MyAdapter mAdapter;
    int strona = 2;
    boolean wczytane = false;
    int position = 10;

    public MainActivityFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        position = mAdapter.getPos();
        outState.putInt("getPos", position);
        Log.d("wartosc", position + "");
    }

   /* @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try{
            if (savedInstanceState.getInt("getPos")==-1){
                position = 1;
            }else {
                position = savedInstanceState.getInt("getPos");
            }
            Log.d("wartosc to", position+"");
            //mLayoutManager.scrollToPosition(20);
            //mLayoutManager.item
        }catch(Exception e){
            position = 1;
            Log.d("nie dziala", "no niestety");
        }
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.scrollToPosition(position);

    } */

  /*  @Override
    public void onResume() {
        try {
            if (savedInstanceState.getInt("getPos") == -1) {
                //position = 10;
            } else {
                position = savedInstanceState.getInt("getPos");
            }
            Log.d("wartosc to", position + "");
            //mLayoutManager.scrollToPosition(20);
            //mLayoutManager.item
        } catch (Exception e) {
            //position = 10;
            Log.d("nie dziala", "no niestety");
        }
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.scrollToPosition(position);
    } */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            if (savedInstanceState.getInt("getPos") == -1) {
                //position = 10;
            } else {
                position = savedInstanceState.getInt("getPos");
            }
            Log.d("wartosc to", position + "");
            //mLayoutManager.scrollToPosition(20);
            //mLayoutManager.item
        } catch (Exception e) {
            //position = 10;
            Log.d("nie dziala", "no niestety");
        }
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.scrollToPosition(position);


        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.mojepanstwo.pl/dane/")
                .build();
        final MojePanstwoService service = restAdapter.create(MojePanstwoService.class);

        mRecyclerView = (UltimateRecyclerView) getView().findViewById(R.id.ultimate_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                Log.d("Koniec listy", "Doczytanie danych...");
                service.listOrders(strona, new Callback<BaseListClass>() {
                    @Override
                    public void success(BaseListClass blc, Response response) {
                        RepositoryClass.getInstance().setBaseListClass(blc);

                        mAdapter = new MyAdapter(RepositoryClass.getInstance().dataObjectList);  //.getBaseListClass().searchClass.dataobjects);

                        //  mAdapter.dodajKolejnyElement();
                        mRecyclerView.setAdapter(mAdapter);
                        //mLayoutManager.getFocusedChild();
                        View view;
                        mLayoutManager.scrollToPositionWithOffset(position, 20);
                        //        mLayoutManager.getPosition(mAdapter);
                        Log.d("Aktualna strona: ", strona + "");
                        strona++;

                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        // Log error here since request failed
                        Log.d("Wystapil blad", "!!!!!!!!!!!!!!!!!!!!!");
                    }
                });
            }
        });

        // specify an adapter (see also next example)
        if (wczytane == false) {
            service.listOrders(1, new Callback<BaseListClass>() {
                @Override
                public void success(BaseListClass blc, Response response) {
                    RepositoryClass.getInstance().setBaseListClass(blc);
                    mAdapter = new MyAdapter(RepositoryClass.getInstance().dataObjectList);  //.getBaseListClass().searchClass.dataobjects);
                    mRecyclerView.setAdapter(mAdapter);
                    Log.d("1-sze wczytanie", "To powinno byc tylko 1 raz");
                    wczytane = true;
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    // Log error here since request failed
                    Log.d("Wystapil blad", "!!!!!!!!!!!!!!!!!!!!!");
                }
            });
        }
    }

}


//Toast.makeText(getActivity().getApplicationContext(),RepositoryClass.getInstance().getString(), Toast.LENGTH_SHORT).show();
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