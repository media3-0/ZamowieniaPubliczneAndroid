package pl.media30.zamowieniapubliczne;

import android.util.Log;
import pl.media30.zamowieniapubliczne.Adapters.MyAdapter;
import pl.media30.zamowieniapubliczne.Models.DownloadList.BaseListClass;
import pl.media30.zamowieniapubliczne.Models.SingleElement.BaseClass;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Adrian on 2015-07-24.
 */
public class MyRestClientClass {
    /*
    //class context
    MojePanstwoService service;
    public BaseClass bc;
    public BaseListClass blc;

    //helper method for service instantiation. call this method once
    public void initializeRestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.mojepanstwo.pl/dane/")
                .build();
        MojePanstwoService service = restAdapter.create(MojePanstwoService.class);
        Log.d("Wyk", "inicjalizacja");
    }

    void updateMap() {
        //mService.points(....)

    }

    public void singleOrder() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.mojepanstwo.pl/dane/")
                .build();
        MojePanstwoService service = restAdapter.create(MojePanstwoService.class);
        service.singleOrder(1, new Callback<BaseClass>() {
                    @Override
                    public void success(BaseClass baseClass, Response response) {
                        bc=baseClass;

                        // mAdapter = new MyAdapter(RepositoryClass.getInstance().dataObjectList);  //.getBaseListClass().searchClass.dataobjects);
                        // mRecyclerView.setAdapter(mAdapter);
                        Log.d("yyy", "xxxxxxx");
                       // Log.d("yyy", RepositoryClass.getInstance().baseClass.objectClass.global_id);
                        Log.d("yyy", bc.objectClass.global_id);
                        //  wczytane = true;


                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("dupa", "xxxxxxx");
                        Log.d("error: ", error.getMessage()+"");

                    }
                }
        );
    }

    public void multi() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.mojepanstwo.pl/dane/")
                .build();
        MojePanstwoService service = restAdapter.create(MojePanstwoService.class);
        Log.d("Wyk", "inicjalizacja");
        service.listOrders(1, new Callback<BaseListClass>() {
            @Override
            public void success(BaseListClass blc, Response response) {
                //RepositoryClass.getInstance().setBaseListClass(blc);
                // mAdapter = new MyAdapter(RepositoryClass.getInstance().dataObjectList);  //.getBaseListClass().searchClass.dataobjects);
                // mRecyclerView.setAdapter(mAdapter);
                Log.d("1-sze wczytanie", "To powinno byc tylko 1 raz");
                //  wczytane = true;
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                // Log error here since request failed
                Log.d("Wystapil blad", "!!!!!!!!!!!!!!!!!!!!!");
            }
        });
    }
    */
}