package com.ef.dataaccess.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.spring.rowmapper.event.EventCriteriaMetadataRowMapper;
import com.ef.model.event.EventCriteriaMetadata;

@Component
public class EventCriteriaMetadataCache {

  private final String SELECT_EVENT_CRITERIA_MAP = "select * from event_criteria_meta";

  private final JdbcTemplate jdbcTemplate;

  private final Map<String, EventCriteriaMetadata> eventCriterionNameToEventCriteriaMap;
  private final Map<Integer, EventCriteriaMetadata> idToEventCriteriaMap;

  @Autowired
  public EventCriteriaMetadataCache(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    idToEventCriteriaMap = new HashMap<Integer, EventCriteriaMetadata>();
    eventCriterionNameToEventCriteriaMap = new HashMap<String, EventCriteriaMetadata>();
    refreshCache();
  }

  public EventCriteriaMetadata getEventCriteria(String eventCriteriaName) {
    return eventCriterionNameToEventCriteriaMap.get(eventCriteriaName);
  }

  public EventCriteriaMetadata getEventCriteria(int eventCriteriaId) {
    return idToEventCriteriaMap.get(eventCriteriaId);
  }

  private void refreshCache() {
    List<EventCriteriaMetadata> eventCriteriaMetadataList = jdbcTemplate.query(SELECT_EVENT_CRITERIA_MAP,
        new EventCriteriaMetadataRowMapper());
    for (EventCriteriaMetadata eventCriteriaMetadata : eventCriteriaMetadataList) {
      idToEventCriteriaMap.put(eventCriteriaMetadata.getId(), eventCriteriaMetadata);
      eventCriterionNameToEventCriteriaMap.put(eventCriteriaMetadata.getEventCriterionName(), eventCriteriaMetadata);
    }
  }

  public List<EventCriteriaMetadata> getEventCriteriaMetadataList() {
    return new ArrayList<EventCriteriaMetadata>(idToEventCriteriaMap.values());
  }

  @Override
  public String toString() {
    return "eventCriterionNameToEventCriteriaMap=" + eventCriterionNameToEventCriteriaMap + ", idToEventCriteriaMap="
        + idToEventCriteriaMap + "]";
  }

}
