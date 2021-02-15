package com.ef.model.member;

import static com.ef.model.core.Domain.KNOWN_DOMAIN_ID_BABY_PRODUCTS;
import static com.ef.model.core.Domain.KNOWN_DOMAIN_ID_DIET_AND_NUTRITION;
import static com.ef.model.core.Domain.KNOWN_DOMAIN_ID_FASHION;
import static com.ef.model.core.Domain.KNOWN_DOMAIN_ID_HEALTH_AND_BEAUTY;
import static com.ef.model.core.Domain.KNOWN_DOMAIN_ID_HOTEL;
import static com.ef.model.core.Domain.KNOWN_DOMAIN_ID_LIFESTYLE;
import static com.ef.model.core.Domain.KNOWN_DOMAIN_ID_RESTAURANT;
import static com.ef.model.core.Domain.KNOWN_DOMAIN_ID_TECHNOLOGY;
import static com.ef.model.core.Domain.KNOWN_DOMAIN_ID_TRAVEL;
import static com.ef.model.core.Forum.KNOWN_FORUM_ID_INSTAGRAM;
import static com.ef.model.core.Forum.KNOWN_FORUM_ID_YOUTUBE;
import static com.ef.model.core.Forum.KNOWN_FORUM_ID_ZOMATO;
import static com.ef.model.member.MemberCriteriaData.KNOWN_CRITERIA_ID_INSTAGRAM_FOLLOWER_COUNT;
import static com.ef.model.member.MemberCriteriaData.KNOWN_CRITERIA_ID_YOUTUBE_FOLLOWER_COUNT;
import static com.ef.model.member.MemberCriteriaData.KNOWN_CRITERIA_ID_ZOMATO_FOLLOWER_COUNT;
import static com.ef.model.member.MemberCriteriaData.KNOWN_CRITERIA_ID_ZOMATO_LEVEL;
import static com.ef.model.member.MemberCriteriaData.KNOWN_CRITERIA_ID_ZOMATO_REVIEWS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;

public class MemberForumCriterionBindingModel {

  private int memberId;

  private String instagramUrl, zomatoUrl, youtubeUrl;
  private int instagramFollowerCount, youtubeFollowerCount, zomatoLevel, zomatoFollowerCount, zomatoReviewCount;
  private boolean catDiet, catFashion, catHealth, catHotel, catLifestyle, catRestaurant, catBabyProducts, catTravel,
      catTechnology;

  private List<Integer> enabledDomainIds = null;
  private Map<Integer, Integer> criteriaValueMap;
  private Map<Integer, String> memberForumUrls;

  public MemberForumCriterionBindingModel() {
    super();
    enabledDomainIds = new ArrayList<Integer>();
    criteriaValueMap = new HashMap<Integer, Integer>();
    memberForumUrls = new HashMap<Integer, String>();
  }

  public MemberForumCriterionBindingModel(int memberId, String instagramUrl, String zomatoUrl, String youtubeUrl,
      int instagramFollowerCount, int youtubeFollowerCount, int zomatoLevel, int zomatoFollowerCount,
      int zomatoReviewCount, boolean catDiet, boolean catFashion, boolean catHealth, boolean catHotel,
      boolean catLifestyle, boolean catRestaurant, boolean catBabyProducts, boolean catTravel, boolean catTechnology) {
    super();
    enabledDomainIds = new ArrayList<Integer>();
    criteriaValueMap = new HashMap<Integer, Integer>();
    memberForumUrls = new HashMap<Integer, String>();
    setMemberId(memberId);
    setInstagramUrl(instagramUrl);
    setZomatoUrl(zomatoUrl);
    setYoutubeUrl(youtubeUrl);
    setInstagramFollowerCount(instagramFollowerCount);
    setYoutubeFollowerCount(youtubeFollowerCount);
    setZomatoLevel(zomatoLevel);
    setZomatoFollowerCount(zomatoFollowerCount);
    setZomatoReviewCount(zomatoReviewCount);
    setCatDiet(catDiet);
    setCatFashion(catFashion);
    setCatHealth(catHealth);
    setCatHotel(catHotel);
    setCatLifestyle(catLifestyle);
    setCatRestaurant(catRestaurant);
    setCatBabyProducts(catBabyProducts);
    setCatTravel(catTravel);
    setCatTechnology(catTechnology);

  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  public String getInstagramUrl() {
    return instagramUrl;
  }

  public void setInstagramUrl(String instagramUrl) {
    memberForumUrls.put(KNOWN_FORUM_ID_INSTAGRAM, instagramUrl);
    this.instagramUrl = instagramUrl;
  }

  public String getZomatoUrl() {
    return zomatoUrl;
  }

  public void setZomatoUrl(String zomatoUrl) {
    memberForumUrls.put(KNOWN_FORUM_ID_ZOMATO, zomatoUrl);
    this.zomatoUrl = zomatoUrl;
  }

  public String getYoutubeUrl() {
    return youtubeUrl;
  }

  public void setYoutubeUrl(String youtubeUrl) {
    memberForumUrls.put(KNOWN_FORUM_ID_YOUTUBE, youtubeUrl);
    this.youtubeUrl = youtubeUrl;
  }

  public int getInstagramFollowerCount() {
    return instagramFollowerCount;
  }

  public void setInstagramFollowerCount(int instagramFollowerCount) {
    criteriaValueMap.put(KNOWN_CRITERIA_ID_INSTAGRAM_FOLLOWER_COUNT, instagramFollowerCount);
    this.instagramFollowerCount = instagramFollowerCount;
  }

  public int getYoutubeFollowerCount() {
    return youtubeFollowerCount;
  }

  public void setYoutubeFollowerCount(int youtubeFollowerCount) {
    criteriaValueMap.put(KNOWN_CRITERIA_ID_YOUTUBE_FOLLOWER_COUNT, youtubeFollowerCount);
    this.youtubeFollowerCount = youtubeFollowerCount;
  }

  public int getZomatoLevel() {
    return zomatoLevel;
  }

  public void setZomatoLevel(int zomatoLevel) {
    criteriaValueMap.put(KNOWN_CRITERIA_ID_ZOMATO_LEVEL, zomatoLevel);
    this.zomatoLevel = zomatoLevel;
  }

  public int getZomatoFollowerCount() {
    return zomatoFollowerCount;
  }

  public void setZomatoFollowerCount(int zomatoFollowerCount) {
    criteriaValueMap.put(KNOWN_CRITERIA_ID_ZOMATO_FOLLOWER_COUNT, zomatoFollowerCount);
    this.zomatoFollowerCount = zomatoFollowerCount;
  }

  public int getZomatoReviewCount() {
    return zomatoReviewCount;
  }

  public void setZomatoReviewCount(int zomatoReviewCount) {
    criteriaValueMap.put(KNOWN_CRITERIA_ID_ZOMATO_REVIEWS, zomatoReviewCount);
    this.zomatoReviewCount = zomatoReviewCount;
  }

  public boolean isCatDiet() {
    return catDiet;
  }

  public void setCatDiet(boolean catDiet) {
    addDomainIfSet(catDiet, KNOWN_DOMAIN_ID_DIET_AND_NUTRITION);
    this.catDiet = catDiet;
  }

  public boolean isCatFashion() {
    return catFashion;
  }

  public void setCatFashion(boolean catFashion) {
    addDomainIfSet(catFashion, KNOWN_DOMAIN_ID_FASHION);
    this.catFashion = catFashion;
  }

  public boolean isCatHealth() {
    return catHealth;
  }

  public void setCatHealth(boolean catHealth) {
    addDomainIfSet(catHealth, KNOWN_DOMAIN_ID_HEALTH_AND_BEAUTY);
    this.catHealth = catHealth;
  }

  public boolean isCatHotel() {
    return catHotel;
  }

  public void setCatHotel(boolean catHotel) {
    addDomainIfSet(catHotel, KNOWN_DOMAIN_ID_HOTEL);
    this.catHotel = catHotel;
  }

  public boolean isCatLifestyle() {
    return catLifestyle;
  }

  public void setCatLifestyle(boolean catLifestyle) {
    addDomainIfSet(catLifestyle, KNOWN_DOMAIN_ID_LIFESTYLE);
    this.catLifestyle = catLifestyle;
  }

  public boolean isCatRestaurant() {
    return catRestaurant;
  }

  public void setCatRestaurant(boolean catRestaurant) {
    addDomainIfSet(catRestaurant, KNOWN_DOMAIN_ID_RESTAURANT);
    this.catRestaurant = catRestaurant;
  }

  public boolean isCatBabyProducts() {
    return catBabyProducts;
  }

  public void setCatBabyProducts(boolean catBabyProducts) {
    addDomainIfSet(catBabyProducts, KNOWN_DOMAIN_ID_BABY_PRODUCTS);
    this.catBabyProducts = catBabyProducts;
  }

  public boolean isCatTravel() {
    return catTravel;
  }

  public void setCatTravel(boolean catTravel) {
    addDomainIfSet(catTravel, KNOWN_DOMAIN_ID_TRAVEL);
    this.catTravel = catTravel;
  }

  public boolean isCatTechnology() {
    return catTechnology;
  }

  public void setCatTechnology(boolean catTechnology) {
    addDomainIfSet(catTechnology, KNOWN_DOMAIN_ID_TECHNOLOGY);
    this.catTechnology = catTechnology;
  }

  public List<Integer> getEnabledDomainIds() {
    return enabledDomainIds;
  }

  public void setEnabledDomainIds(List<Integer> enabledDomainIds) {
    this.enabledDomainIds = enabledDomainIds;
  }

  public Map<Integer, Integer> getCriteriaValueMap() {
    return criteriaValueMap;
  }

  public Map<Integer, String> getMemberForumUrls() {
    return memberForumUrls;
  }

  private void addDomainIfSet(boolean isSet, int domainId) {
    if (isSet) {
      enabledDomainIds.add(domainId);
    }
  }

  @Override
  public String toString() {
    return "MemberForumCriterionBindingModel [memberId=" + memberId + ", instagramUrl=" + instagramUrl + ", zomatoUrl="
        + zomatoUrl + ", youtubeUrl=" + youtubeUrl + ", instagramFollowerCount=" + instagramFollowerCount
        + ", youtubeFollowerCount=" + youtubeFollowerCount + ", zomatoLevel=" + zomatoLevel + ", zomatoFollowerCount="
        + zomatoFollowerCount + ", zomatoReviewCount=" + zomatoReviewCount + ", catDiet=" + catDiet + ", catFashion="
        + catFashion + ", catHealth=" + catHealth + ", catHotel=" + catHotel + ", catLifestyle=" + catLifestyle
        + ", catRestaurant=" + catRestaurant + ", catBabyProducts=" + catBabyProducts + ", catTravel=" + catTravel
        + ", catTechnology=" + catTechnology + ", enabledDomainIds=" + enabledDomainIds + "]";
  }

  public static void main(String[] args) {
    int memberId = new Random().nextInt(99000999);
    String instagramUrl = "http://insta.com/mypage";
    String zomatoUrl = "http://zomato.com/mypage";
    String youtubeUrl = "http://youtube.com/mypage";

    int instagramFollowerCount = new Random().nextInt(99000999);
    int youtubeFollowerCount = new Random().nextInt(3553523);
    int zomatoLevel = new Random().nextInt(15);
    int zomatoFollowerCount = new Random().nextInt(99000999);
    int zomatoReviewCount = new Random().nextInt(224242);

    boolean catDiet = false;
    boolean catFashion = true;
    boolean catHealth = false;
    boolean catHotel = true;
    boolean catLifestyle = false;
    boolean catRestaurant = true;
    boolean catBabyProducts = false;
    boolean catTravel = true;
    boolean catTechnology = false;

    MemberForumCriterionBindingModel a = new MemberForumCriterionBindingModel(memberId, instagramUrl, zomatoUrl,
        youtubeUrl, instagramFollowerCount, youtubeFollowerCount, zomatoLevel, zomatoFollowerCount, zomatoReviewCount,
        catDiet, catFashion, catHealth, catHotel, catLifestyle, catRestaurant, catBabyProducts, catTravel,
        catTechnology);

    System.out.println(new Gson().toJson(a));

  }

}
