package pl.media30.zamowieniapubliczne.Models.DownloadList;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import pl.media30.zamowieniapubliczne.Models.SingleElement.ObjectClass;

/**
 * Created by Adrian on 2015-07-17.
 */
public class SearchClass implements Serializable {

    @SerializedName("pagination")
    public PaginationClass paginationClass;

    @SerializedName("performance")
    public PerformanceClass performanceClass;

    @SerializedName("dataobjects")
    public List<DataObjectClass> dataobjects;

    public List<DataObjectClass> getDataObjectClass(){
        return dataobjects;
    }

}
