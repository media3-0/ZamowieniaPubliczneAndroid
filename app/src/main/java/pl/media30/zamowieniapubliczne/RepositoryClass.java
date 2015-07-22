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

        //.getBaseListClass().searchClass.dataobjects);
        this.baseListClass = baseListClass;
        //   dataObjectList.add(baseListClass.searchClass.dataobjects.get(i));
            if (dataObjectList == null){
                dataObjectList = baseListClass.searchClass.dataobjects;

            }else{
                for(int i=0;i<baseListClass.searchClass.dataobjects.size();i++) {
                    dataObjectList.add(baseListClass.searchClass.dataobjects.get(i));
                }
            }
          //  Log.d("Liczba iteracji: ", i+"");

    }

    public BaseListClass getBaseListClass() {
        return baseListClass;
    }


    public List<DataObjectClass> getList() {
        return dataObjectList;
    }





    private RepositoryClass() {
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



