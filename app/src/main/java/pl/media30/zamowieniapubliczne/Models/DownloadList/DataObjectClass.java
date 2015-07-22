package pl.media30.zamowieniapubliczne.Models.DownloadList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import pl.media30.zamowieniapubliczne.Models.SingleElement.BaseClass;
import pl.media30.zamowieniapubliczne.Models.SingleElement.DataClass;

/**
 * Created by Adrian on 2015-07-17.
 */
public class DataObjectClass {

    private DataObjectClass dataObjectClass;

    public DataObjectClass getDataObjectClass() {
        return dataObjectClass;
    }

    public void setDataObjectClass(DataObjectClass dataObjectClass) {
        this.dataObjectClass = dataObjectClass;
    }

    @SerializedName("global_id")
    public String global_id;
    @SerializedName("id")
    public String id;
    @SerializedName("slug")
    public String slug;

    @SerializedName("data")
    public DataClass dataClass;

    @SerializedName("dataobjects")
    public List<BaseClass> dataset;

}
