package pl.media30.zamowieniapubliczne.Models.DownloadList;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Adrian on 2015-07-17.
 */
public class PaginationClass implements Serializable {

    @SerializedName("count")
    public int count;
    @SerializedName("total")
    public int total;
    @SerializedName("from")
    public int from;
    @SerializedName("to")
    public int to;

}
