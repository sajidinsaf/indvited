package com.ef.dataaccess.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.spring.member.rowmapper.EventTypeRowMapper;
import com.ef.model.event.EventType;

@Component
public class EventTypeCache {

  private final String SELECT_EVENT_TYPE = "select id, name from event_type";

  private final JdbcTemplate jdbcTemplate;

  private final Map<String, EventType> nameToEventTypeMap;
  private final Map<Integer, EventType> idToEventTypeMap;

  @Autowired
  public EventTypeCache(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    nameToEventTypeMap = new HashMap<String, EventType>();
    idToEventTypeMap = new HashMap<Integer, EventType>();
    refreshCache();
  }

  public EventType getEventType(String eventName) {
    return nameToEventTypeMap.get(eventName);
  }

  public EventType getEventType(int eventId) {
    return idToEventTypeMap.get(eventId);
  }

  private void refreshCache() {
    List<EventType> domainList = jdbcTemplate.query(SELECT_EVENT_TYPE, new EventTypeRowMapper());
    for (EventType eventType : domainList) {
      nameToEventTypeMap.put(eventType.getName(), eventType);
      idToEventTypeMap.put(eventType.getId(), eventType);
    }
  }
}
