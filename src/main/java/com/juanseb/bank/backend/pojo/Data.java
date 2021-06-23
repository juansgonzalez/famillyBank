package com.juanseb.bank.backend.pojo;

public class Data {
	 private String id;
	 private String title;
	 private String url_viewer;
	 private String url;
	 private String display_url;
	 private float size;
	 private String time;
	 private String expiration;
	 Image ImageObject;
	 Thumb ThumbObject;
	 private String delete_url;


	 // Getter Methods 

	 public String getId() {
	  return id;
	 }

	 public String getTitle() {
	  return title;
	 }

	 public String getUrl_viewer() {
	  return url_viewer;
	 }

	 public String getUrl() {
	  return url;
	 }

	 public String getDisplay_url() {
	  return display_url;
	 }

	 public float getSize() {
	  return size;
	 }

	 public String getTime() {
	  return time;
	 }

	 public String getExpiration() {
	  return expiration;
	 }

	 public Image getImage() {
	  return ImageObject;
	 }

	 public Thumb getThumb() {
	  return ThumbObject;
	 }

	 public String getDelete_url() {
	  return delete_url;
	 }

	 // Setter Methods 

	 public void setId(String id) {
	  this.id = id;
	 }

	 public void setTitle(String title) {
	  this.title = title;
	 }

	 public void setUrl_viewer(String url_viewer) {
	  this.url_viewer = url_viewer;
	 }

	 public void setUrl(String url) {
	  this.url = url;
	 }

	 public void setDisplay_url(String display_url) {
	  this.display_url = display_url;
	 }

	 public void setSize(float size) {
	  this.size = size;
	 }

	 public void setTime(String time) {
	  this.time = time;
	 }

	 public void setExpiration(String expiration) {
	  this.expiration = expiration;
	 }

	 public void setImage(Image imageObject) {
	  this.ImageObject = imageObject;
	 }

	 public void setThumb(Thumb thumbObject) {
	  this.ThumbObject = thumbObject;
	 }

	 public void setDelete_url(String delete_url) {
	  this.delete_url = delete_url;
	 }

	@Override
	public String toString() {
		return "Data [id=" + id + ", title=" + title + ", url_viewer=" + url_viewer + ", url=" + url + ", display_url="
				+ display_url + ", size=" + size + ", time=" + time + ", expiration=" + expiration + ", ImageObject="
				+ ImageObject + ", ThumbObject=" + ThumbObject + ", delete_url=" + delete_url + "]";
	}
	 
	}

