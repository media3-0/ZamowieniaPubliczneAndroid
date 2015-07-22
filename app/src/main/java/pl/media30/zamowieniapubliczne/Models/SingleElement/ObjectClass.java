package pl.media30.zamowieniapubliczne.Models.SingleElement;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Adrian on 2015-07-17.
 */
public class ObjectClass {
    public ObjectClass getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(ObjectClass objectClass) {
        this.objectClass = objectClass;
    }

    ObjectClass objectClass;

    @SerializedName("data")
    public DataClass dataClass;
    @SerializedName("global_id")
    public String global_id;

}
