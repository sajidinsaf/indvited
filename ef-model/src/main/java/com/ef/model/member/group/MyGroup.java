package com.ef.model.member.group;

import java.sql.Timestamp;

public class MyGroup {

  private int id, creatorId;
  private String name, description;
  private Timestamp creationTimestamp;
  private MyGroupType myGroupType;

  public MyGroup() {

  }

  public MyGroup(int id, int creatorId, MyGroupType myGroupType, String name, String description,
      Timestamp creationTimestamp) {
    super();
    this.id = id;
    this.creatorId = creatorId;
    this.myGroupType = myGroupType;
    this.name = name;
    this.description = description;
    this.creationTimestamp = creationTimestamp;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public Timestamp getCreationTimestamp() {
    return creationTimestamp;
  }

  public void setCreationTimestamp(Timestamp creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }

  public int getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(int creatorId) {
    this.creatorId = creatorId;
  }

  public MyGroupType getMyGroupType() {
    return myGroupType;
  }

  public void setMyGroupType(MyGroupType myGroupType) {
    this.myGroupType = myGroupType;
  }

  @Override
  public String toString() {
    return "MyGroup [id=" + id + ", creatorId=" + creatorId + ", name=" + name + ", description=" + description
        + ", creationTimestamp=" + creationTimestamp + ", myGroupType=" + myGroupType + "]";
  }

}
