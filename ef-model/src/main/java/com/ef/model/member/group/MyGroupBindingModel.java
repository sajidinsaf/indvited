package com.ef.model.member.group;

public class MyGroupBindingModel {

  private int creatorId, myGroupTypeId;
  private String name, description;

  public MyGroupBindingModel() {

  }

  public MyGroupBindingModel(int creatorId, int myGroupTypeId, String name, String description) {
    super();
    this.creatorId = creatorId;
    this.myGroupTypeId = myGroupTypeId;
    this.name = name;
    this.description = description;

  }

  public int getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(int creatorId) {
    this.creatorId = creatorId;
  }

  public int getMyGroupTypeId() {
    return myGroupTypeId;
  }

  public void setMyGroupTypeId(int myGroupTypeId) {
    this.myGroupTypeId = myGroupTypeId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "MyGroupBindingModel [creatorId=" + creatorId + ", myGroupTypeId=" + myGroupTypeId + ", name=" + name
        + ", description=" + description + "]";
  }

}
