package pl.media30.zamowieniapubliczne.Models.DownloadList;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Adrian on 2015-07-17.
 */
public class PaginationClass {

    private PaginationClass paginationClass;

    public PaginationClass getPaginationClass() {
        return paginationClass;
    }

    public void setPaginationClass(PaginationClass paginationClass) {
        this.paginationClass = paginationClass;
    }

    @SerializedName("count")
    public int count;
    @SerializedName("total")
    public int total;
    @SerializedName("from")
    public int from;
    @SerializedName("to")
    public int to;

}
