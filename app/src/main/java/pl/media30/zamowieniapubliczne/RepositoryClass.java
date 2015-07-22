package pl.media30.zamowieniapubliczne;

import android.util.Log;

import java.util.List;

import pl.media30.zamowieniapubliczne.Adapters.MyAdapter;
import pl.media30.zamowieniapubliczne.Models.DownloadList.BaseListClass;
import pl.media30.zamowieniapubliczne.Models.DownloadList.DataObjectClass;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.app.Application;
import android.util.Log;

/**
 * Created by Adrian on 2015-07-21.
 */
public class RepositoryClass {

    private static RepositoryClass mInstance = null;
    private String mString;
    public List<DataObjectClass> dataObjectList;
    private BaseListClass baseListClass;


    public void setBaseListClass(BaseListClass baseListClass) {
        this.baseListClass = baseListClass;
    }

    public BaseListClass getBaseListClass() {
        return baseListClass;
    }

    public List<DataObjectClass> getList() {
        return dataObjectList;
    }



    private RepositoryClass() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.mojepanstwo.pl/dane/")
                .build();
        MojePanstwoService service = restAdapter.create(MojePanstwoService.class);

        service.listOrders(new Callback<BaseListClass>() {

            @Override
            public void success(BaseListClass blc, Response response) {
                dataObjectList = blc.searchClass.dataobjects; //new ArrayList<DataObjectClass>();
                //  dol=dataObjectList;
                Log.d("!!!!!!!!", dataObjectList.get(0).global_id);

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                // Log error here since request failed
                Log.d("Wystapil blad", "!!!!!!!!!!!!!!!!!!!!!");
            }
        });
    }

    public static RepositoryClass getInstance() {
        if (mInstance == null) {
            mInstance = new RepositoryClass();
        }
        return mInstance;
    }
/*
        public String getString(){
            return this.mString;
        }

        public void setString(String value){
            mString = value;
        }
*/

}



