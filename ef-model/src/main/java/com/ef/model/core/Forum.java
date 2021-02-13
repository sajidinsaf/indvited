package com.ef.model.core;

public class Forum implements Identifiable {

  private static final long serialVersionUID = -3401800981091512218L;

  public static final int KNOWN_FORUM_ID_INSTAGRAM = 1;
  public static final int KNOWN_FORUM_ID_ZOMATO = 2;
  public static final int KNOWN_FORUM_ID_YOUTUBE = 3;

  private final int id;
  private final String name;
  private final String baseUrl;

  public Forum(int id, String name, String baseUrl) {
    super();
    this.id = id;
    this.name = name;
    this.baseUrl = baseUrl;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  @Override
  public String toString() {
    return "Forum [id=" + id + ", name=" + name + ", baseUrl=" + baseUrl + "]";
  }

}
