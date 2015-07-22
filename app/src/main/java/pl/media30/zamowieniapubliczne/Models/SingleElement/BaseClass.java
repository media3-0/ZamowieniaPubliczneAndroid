package pl.media30.zamowieniapubliczne.Models.SingleElement;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Adrian on 2015-07-17.
 */
public class BaseClass {
    public BaseClass getBaseClass() {
        return baseClass;
    }

    public void setBaseClass(BaseClass baseClass) {
        this.baseClass = baseClass;
    }

    BaseClass baseClass;

    @SerializedName("object")
    public ObjectClass objectClass;
}