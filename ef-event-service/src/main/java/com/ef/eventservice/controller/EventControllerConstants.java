package com.ef.eventservice.controller;

public class EventControllerConstants {

  public static final String PUBLISH_PR_EVENT = "/api/v1/event/publishPREvent";

  public static final String CREATE_SCHEDULE = "/api/v1/event/createSchedule";

  public static final String SUBSCRIBE_SCHEDULE = "/api/v1/event/subscribeSchedule";

  public static final String TEST_EVENT = "/api/v1/event/test";

  public static final String GET_PR_EVENT_LIST = "/api/v1/event/pr/list";

  public static final String GET_PR_EVENT_LIST_BY_DATE = "/api/v1/event/pr/list/by/date";

  public static final String GET_PR_EVENT_SCHEDULE_LIST = "/ap1/v1/event/schedule/list";

  public static final String PR_EVENT = "prEvent";

  public static final String PR_EVENT_SCHEDULE_BINDING_MODEL = "PR_EVENT_BINDING_MODEL";

  public static final String PR_EVENT_SCHEDULE_PERSIST_RESULT = "PR_EVENT_PERSIST_RESULT";

  public static final String GET_SUBSCRIBER_ELIGIBLE_LIST_V1 = "/api/v1/event/blogger/list/eligible";
}
