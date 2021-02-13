package com.ef.model.member;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

import com.google.gson.Gson;

public class MemberProfileInfoBindingModel implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 7559225941379739923L;

  private int memberId;
  private int[] domains;
  private MemberForumCriterionBindingModel[] criteria;

  public MemberProfileInfoBindingModel() {

  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  public int[] getDomains() {
    return domains;
  }

  public void setDomains(int[] domains) {
    this.domains = domains;
  }

  public MemberForumCriterionBindingModel[] getCriteria() {
    return criteria;
  }

  public void setCriteria(MemberForumCriterionBindingModel[] criteria) {
    this.criteria = criteria;
  }

  @Override
  public String toString() {
    return "MemberProfileInfoBindingModel [memberId=" + memberId + ", domains=" + Arrays.toString(domains)
        + ", criteria=" + Arrays.toString(criteria) + "]";
  }

  public static void main(String[] args) {

    MemberProfileInfoBindingModel a = new MemberProfileInfoBindingModel();
    a.setMemberId(new Random().nextInt(99999999));
    a.setDomains(new int[] { new Random().nextInt(99), new Random().nextInt(99), new Random().nextInt(99) });

//    a.setCriteria(new MemberForumCriterionBindingModel[] {
//        new MemberForumCriterionBindingModel(new Random().nextInt(99), new Random().nextInt(99),
//            "http://zomato.com/" + new Random().nextInt(999999)),
//        new MemberForumCriterionBindingModel(new Random().nextInt(99), new Random().nextInt(99),
//            "http://instagram.com/" + new Random().nextInt(999999)),
//
//    });

    System.out.println(new Gson().toJson(a));

  }
}
