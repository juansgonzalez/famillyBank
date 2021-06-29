
package com.juanseb.bank.backend.pojo;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ObjectImg {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("status")
    @Expose
    private long status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ObjectImg() {
    }

    /**
     * 
     * @param data
     * @param success
     * @param status
     */
    public ObjectImg(Data data, boolean success, long status) {
        super();
        this.data = data;
        this.success = success;
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public ObjectImg withData(Data data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ObjectImg withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public ObjectImg withStatus(long status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ObjectImg.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("data");
        sb.append('=');
        sb.append(((this.data == null)?"<null>":this.data));
        sb.append(',');
        sb.append("success");
        sb.append('=');
        sb.append(this.success);
        sb.append(',');
        sb.append("status");
        sb.append('=');
        sb.append(this.status);
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.data == null)? 0 :this.data.hashCode()));
        result = ((result* 31)+(this.success? 1 : 0));
        result = ((result* 31)+((int)(this.status^(this.status >>> 32))));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ObjectImg) == false) {
            return false;
        }
        ObjectImg rhs = ((ObjectImg) other);
        return ((((this.data == rhs.data)||((this.data!= null)&&this.data.equals(rhs.data)))&&(this.success == rhs.success))&&(this.status == rhs.status));
    }

}
