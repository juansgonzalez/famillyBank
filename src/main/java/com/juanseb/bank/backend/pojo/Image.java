package com.juanseb.bank.backend.pojo;


public class Image {
 private String filename;
 private String name;
 private String mime;
 private String extension;
 private String url;


 // Getter Methods 

 public String getFilename() {
  return filename;
 }

 public String getName() {
  return name;
 }

 public String getMime() {
  return mime;
 }

 public String getExtension() {
  return extension;
 }

 public String getUrl() {
  return url;
 }

 // Setter Methods 

 public void setFilename(String filename) {
  this.filename = filename;
 }

 public void setName(String name) {
  this.name = name;
 }

 public void setMime(String mime) {
  this.mime = mime;
 }

 public void setExtension(String extension) {
  this.extension = extension;
 }

 public void setUrl(String url) {
  this.url = url;
 }

@Override
public String toString() {
	return "Image [filename=" + filename + ", name=" + name + ", mime=" + mime + ", extension=" + extension + ", url="
			+ url + "]";
}
 
}
