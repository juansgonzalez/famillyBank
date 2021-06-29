
package com.juanseb.bank.backend.pojo;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url_viewer")
    @Expose
    private String urlViewer;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("display_url")
    @Expose
    private String displayUrl;
    @SerializedName("size")
    @Expose
    private long size;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("expiration")
    @Expose
    private String expiration;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("thumb")
    @Expose
    private Thumb thumb;
    @SerializedName("medium")
    @Expose
    private Medium medium;
    @SerializedName("delete_url")
    @Expose
    private String deleteUrl;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param displayUrl
     * @param image
     * @param urlViewer
     * @param size
     * @param thumb
     * @param expiration
     * @param deleteUrl
     * @param id
     * @param time
     * @param medium
     * @param title
     * @param url
     */
    public Data(String id, String title, String urlViewer, String url, String displayUrl, long size, String time, String expiration, Image image, Thumb thumb, Medium medium, String deleteUrl) {
        super();
        this.id = id;
        this.title = title;
        this.urlViewer = urlViewer;
        this.url = url;
        this.displayUrl = displayUrl;
        this.size = size;
        this.time = time;
        this.expiration = expiration;
        this.image = image;
        this.thumb = thumb;
        this.medium = medium;
        this.deleteUrl = deleteUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Data withId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Data withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrlViewer() {
        return urlViewer;
    }

    public void setUrlViewer(String urlViewer) {
        this.urlViewer = urlViewer;
    }

    public Data withUrlViewer(String urlViewer) {
        this.urlViewer = urlViewer;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Data withUrl(String url) {
        this.url = url;
        return this;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    public Data withDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
        return this;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Data withSize(long size) {
        this.size = size;
        return this;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Data withTime(String time) {
        this.time = time;
        return this;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public Data withExpiration(String expiration) {
        this.expiration = expiration;
        return this;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Data withImage(Image image) {
        this.image = image;
        return this;
    }

    public Thumb getThumb() {
        return thumb;
    }

    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }

    public Data withThumb(Thumb thumb) {
        this.thumb = thumb;
        return this;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Data withMedium(Medium medium) {
        this.medium = medium;
        return this;
    }

    public String getDeleteUrl() {
        return deleteUrl;
    }

    public void setDeleteUrl(String deleteUrl) {
        this.deleteUrl = deleteUrl;
    }

    public Data withDeleteUrl(String deleteUrl) {
        this.deleteUrl = deleteUrl;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Data.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null)?"<null>":this.title));
        sb.append(',');
        sb.append("urlViewer");
        sb.append('=');
        sb.append(((this.urlViewer == null)?"<null>":this.urlViewer));
        sb.append(',');
        sb.append("url");
        sb.append('=');
        sb.append(((this.url == null)?"<null>":this.url));
        sb.append(',');
        sb.append("displayUrl");
        sb.append('=');
        sb.append(((this.displayUrl == null)?"<null>":this.displayUrl));
        sb.append(',');
        sb.append("size");
        sb.append('=');
        sb.append(this.size);
        sb.append(',');
        sb.append("time");
        sb.append('=');
        sb.append(((this.time == null)?"<null>":this.time));
        sb.append(',');
        sb.append("expiration");
        sb.append('=');
        sb.append(((this.expiration == null)?"<null>":this.expiration));
        sb.append(',');
        sb.append("image");
        sb.append('=');
        sb.append(((this.image == null)?"<null>":this.image));
        sb.append(',');
        sb.append("thumb");
        sb.append('=');
        sb.append(((this.thumb == null)?"<null>":this.thumb));
        sb.append(',');
        sb.append("medium");
        sb.append('=');
        sb.append(((this.medium == null)?"<null>":this.medium));
        sb.append(',');
        sb.append("deleteUrl");
        sb.append('=');
        sb.append(((this.deleteUrl == null)?"<null>":this.deleteUrl));
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
        result = ((result* 31)+((this.image == null)? 0 :this.image.hashCode()));
        result = ((result* 31)+((this.urlViewer == null)? 0 :this.urlViewer.hashCode()));
        result = ((result* 31)+((this.thumb == null)? 0 :this.thumb.hashCode()));
        result = ((result* 31)+((this.medium == null)? 0 :this.medium.hashCode()));
        result = ((result* 31)+((this.title == null)? 0 :this.title.hashCode()));
        result = ((result* 31)+((this.url == null)? 0 :this.url.hashCode()));
        result = ((result* 31)+((this.displayUrl == null)? 0 :this.displayUrl.hashCode()));
        result = ((result* 31)+((int)(this.size^(this.size >>> 32))));
        result = ((result* 31)+((this.expiration == null)? 0 :this.expiration.hashCode()));
        result = ((result* 31)+((this.deleteUrl == null)? 0 :this.deleteUrl.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.time == null)? 0 :this.time.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Data) == false) {
            return false;
        }
        Data rhs = ((Data) other);
        return (((((((((((((this.image == rhs.image)||((this.image!= null)&&this.image.equals(rhs.image)))&&((this.urlViewer == rhs.urlViewer)||((this.urlViewer!= null)&&this.urlViewer.equals(rhs.urlViewer))))&&((this.thumb == rhs.thumb)||((this.thumb!= null)&&this.thumb.equals(rhs.thumb))))&&((this.medium == rhs.medium)||((this.medium!= null)&&this.medium.equals(rhs.medium))))&&((this.title == rhs.title)||((this.title!= null)&&this.title.equals(rhs.title))))&&((this.url == rhs.url)||((this.url!= null)&&this.url.equals(rhs.url))))&&((this.displayUrl == rhs.displayUrl)||((this.displayUrl!= null)&&this.displayUrl.equals(rhs.displayUrl))))&&(this.size == rhs.size))&&((this.expiration == rhs.expiration)||((this.expiration!= null)&&this.expiration.equals(rhs.expiration))))&&((this.deleteUrl == rhs.deleteUrl)||((this.deleteUrl!= null)&&this.deleteUrl.equals(rhs.deleteUrl))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.time == rhs.time)||((this.time!= null)&&this.time.equals(rhs.time))));
    }

}
