package pl.media30.zamowieniapubliczne.Models.DownloadList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import pl.media30.zamowieniapubliczne.Models.SingleElement.ObjectClass;

/**
 * Created by Adrian on 2015-07-17.
 */
public class SearchClass {

    public SearchClass getSearchClass() {
        return searchClass;
    }

    public void setSearchClass(SearchClass searchClass) {
        this.searchClass = searchClass;
    }

    private SearchClass searchClass;

    @SerializedName("pagination")
    public PaginationClass paginationClass;

    @SerializedName("performance")
    public PerformanceClass performanceClass;

    @SerializedName("dataobjects")
    public List<DataObjectClass> dataobjects;

}
