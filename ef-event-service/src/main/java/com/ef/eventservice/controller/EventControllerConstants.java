package com.ef.eventservice.controller;

public class EventControllerConstants {

  public static final String PUBLISH_PR_EVENT = "/rest/event/publishPREvent";

  public static final String CREATE_SCHEDULE = "/rest/event/createSchedule";

  public static final String SUBSCRIBE_SCHEDULE = "/rest/event/subscribeSchedule";

  public static final String TEST_EVENT = "/rest/event/test";

  public static final String GET_PR_EVENT_LIST = "/rest/event/pr/list";

  public static final String GET_PR_EVENT_LIST_BY_DATE = "/rest/event/pr/list/by/date";

  public static final String GET_PR_EVENT_SCHEDULE_LIST = "/rest/event/schedule/list";

  public static final String PR_EVENT = "prEvent";

  public static final String PR_EVENT_SCHEDULE_BINDING_MODEL = "PR_EVENT_BINDING_MODEL";

  public static final String PR_EVENT_SCHEDULE_PERSIST_RESULT = "PR_EVENT_PERSIST_RESULT";

  public static final String GET_PR_SUBSCRIBER_ELIGIBLE_LIST = "/rest/event/blogger/list/eligible";
}
