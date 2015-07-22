package pl.media30.zamowieniapubliczne.Models.DownloadList;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Adrian on 2015-07-17.
 */
public class BaseListClass {

    private BaseListClass baseListClass;

    public BaseListClass getBaseListClass() {
        return baseListClass;
    }

    public void setBaseListClass(BaseListClass baseListClass) {
        this.baseListClass = baseListClass;
    }

    @SerializedName("search")
    public SearchClass searchClass;
}
