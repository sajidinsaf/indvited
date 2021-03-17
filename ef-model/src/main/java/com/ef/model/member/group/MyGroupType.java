package com.ef.model.member.group;

public class MyGroupType {

  public static final int KNOWN_GROUP_TYPE_ID_ALL = 1;
  public static final int KNOWN_GROUP_TYPE_ID_PRS = 2;
  public static final int KNOWN_GROUP_TYPE_ID_BLOGGERS = 3;

  private int id;
  private String name, description;

  public MyGroupType() {

  }

  public MyGroupType(int id, String name, String description) {
    super();
    this.id = id;
    this.name = name;
    this.description = description;
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

  @Override
  public String toString() {
    return "MyGroupType [id=" + id + ", name=" + name + ", description=" + description + "]";
  }

}
