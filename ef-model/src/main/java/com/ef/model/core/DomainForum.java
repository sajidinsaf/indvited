package com.ef.model.core;

public class DomainForum implements Identifiable {

  /**
   * 
   */
  private static final long serialVersionUID = -1441988367467965781L;

  private final int id;
  private final Domain domain;
  private final Forum forum;
  private final String name;

  public DomainForum(int id, Domain domain, Forum forum) {
    super();
    this.id = id;
    this.domain = domain;
    this.forum = forum;
    this.name = domain.getName() + "_" + forum.getName();
  }

  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  public Domain getDomain() {
    return domain;
  }

  public Forum getForum() {
    return forum;
  }

  @Override
  public String toString() {
    return "DomainForum [id=" + id + ", domain=" + domain + ", forum=" + forum + ", name=" + name + "]";
  }

}
