package pl.media30.zamowieniapubliczne.Models.DownloadList;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Adrian on 2015-07-17.
 */
public class BaseListClass {

    @SerializedName("search")
    public SearchClass searchClass;

    public SearchClass getSearchClass(){
        return searchClass;
    }
}
