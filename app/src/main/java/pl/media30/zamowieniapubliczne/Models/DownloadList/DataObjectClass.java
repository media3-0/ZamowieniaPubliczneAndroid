package pl.media30.zamowieniapubliczne.Models.DownloadList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import pl.media30.zamowieniapubliczne.Models.SingleElement.BaseClass;

/**
 * Created by Adrian on 2015-07-17.
 */
public class DataObjectClass {
    @SerializedName("global_id")
    public String global_id;
    @SerializedName("id")
    public String id;
    @SerializedName("dataobjects")
    public List<BaseClass> dataset;

}
