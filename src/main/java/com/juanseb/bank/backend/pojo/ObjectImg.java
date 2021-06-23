package com.juanseb.bank.backend.pojo;

public class ObjectImg {
 Data DataObject;
 private boolean success;
 private float status;


 // Getter Methods 

 public Data getData() {
  return DataObject;
 }

 public boolean getSuccess() {
  return success;
 }

 public float getStatus() {
  return status;
 }

 // Setter Methods 

 public void setData(Data dataObject) {
  this.DataObject = dataObject;
 }

 public void setSuccess(boolean success) {
  this.success = success;
 }

 public void setStatus(float status) {
  this.status = status;
 }

@Override
public String toString() {
	return "ObjectImg [DataObject=" + DataObject + ", success=" + success + ", status=" + status + "]";
}
 
}
