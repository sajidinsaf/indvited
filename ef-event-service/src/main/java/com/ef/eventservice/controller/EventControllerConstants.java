package com.ef.eventservice.controller;

public class EventControllerConstants {

  public static final String PUBLISH_PR_EVENT = "/api/v1/event/publishPREvent";

  public static final String CREATE_SCHEDULE = "/api/v1/event/createSchedule";

  public static final String SUBSCRIBE = "/api/v1/event/subscribe";

  public static final String SUBMIT_DELIVERABLE = "/api/v1/event/subscriber/submit/deliverable";

  public static final String TEST_EVENT = "/api/v1/event/test";

  public static final String GET_PR_EVENT_LIST = "/api/v1/event/pr/list";

  public static final String GET_PR_EVENT_LIST_BY_DATE = "/api/v1/event/pr/list/by/date";

  public static final String GET_PR_EVENT_SCHEDULE_LIST = "/ap1/v1/event/schedule/list";

  public static final String PR_EVENT = "prEvent";

  public static final String PR_EVENT_SCHEDULE_BINDING_MODEL = "PR_EVENT_BINDING_MODEL";

  public static final String PR_EVENT_SCHEDULE_PERSIST_RESULT = "PR_EVENT_PERSIST_RESULT";

  public static final String GET_SUBSCRIBER_ELIGIBLE_LIST_V1 = "/api/v1/event/blogger/list/eligible";

  public static final String GET_AWAITING_APPROVAL_SUBSCRIPTIONS_V1 = "/api/v1/event/subscriptions/3";

  public static final String SUBSCRIPTIONS_APPROVE_V1 = "/api/v1/event/subscriptions/approve";

  public static final String SUBSCRIPTIONS_REJECT_V1 = "/api/v1/event/subscriptions/reject";

  public static final String SUBSCRIPTIONS_APPROVE_DELIVERABLE_V1 = "/api/v1/event/subscriptions/approve/deliverable";

  public static final String SUBSCRIPTIONS_REJECT_DELIVERABLE_V1 = "/api/v1/event/subscriptions/reject/deliverable";

  public static final String GET_REVIEW_SCHEDULE_FOR_DATES = "/api/v1/event/pr/reviewScheduleFor/dates";
}
