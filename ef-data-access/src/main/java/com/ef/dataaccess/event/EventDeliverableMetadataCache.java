package com.ef.dataaccess.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.spring.rowmapper.event.EventDeliverableMetadataRowMapper;
import com.ef.model.event.EventDeliverableMetadata;

@Component
public class EventDeliverableMetadataCache {

  private final String SELECT_EVENT_DELIVERABLE_MAP = "select id, name, description from event_deliverable_meta";

  private final JdbcTemplate jdbcTemplate;

  private final Map<String, EventDeliverableMetadata> nameToEventDeliverableMap;
  private final Map<Integer, EventDeliverableMetadata> idToEventDeliverableMap;

  @Autowired
  public EventDeliverableMetadataCache(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    nameToEventDeliverableMap = new HashMap<String, EventDeliverableMetadata>();
    idToEventDeliverableMap = new HashMap<Integer, EventDeliverableMetadata>();
    refreshCache();
  }

  public EventDeliverableMetadata getEventDeliverable(String eventDeliverableName) {
    return nameToEventDeliverableMap.get(eventDeliverableName);
  }

  public EventDeliverableMetadata getEventDeliverable(int eventDeliverableId) {
    return idToEventDeliverableMap.get(eventDeliverableId);
  }

  private void refreshCache() {
    List<EventDeliverableMetadata> eventDeliverableMetadataList = jdbcTemplate.query(SELECT_EVENT_DELIVERABLE_MAP,
        new EventDeliverableMetadataRowMapper());
    for (EventDeliverableMetadata eventDeliverableMetadata : eventDeliverableMetadataList) {
      nameToEventDeliverableMap.put(eventDeliverableMetadata.getDeliverableName(), eventDeliverableMetadata);
      idToEventDeliverableMap.put(eventDeliverableMetadata.getId(), eventDeliverableMetadata);
    }
  }
}
