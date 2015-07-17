package pl.media30.zamowieniapubliczne.Models.DownloadList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import pl.media30.zamowieniapubliczne.Models.ObjectClass;
import pl.media30.zamowieniapubliczne.Models.Zamowienie;

/**
 * Created by Adrian on 2015-07-17.
 */
public class SearchClass {
    @SerializedName("pagination")
    public PaginationClass paginationClass;

    @SerializedName("performance")
    public PerformanceClass performanceClass;

    @SerializedName("dataobjects")
    public List<ObjectClass> dataobjects;

}
