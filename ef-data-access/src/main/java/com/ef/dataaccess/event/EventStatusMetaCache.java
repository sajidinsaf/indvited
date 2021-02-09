package com.ef.dataaccess.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.spring.rowmapper.event.EventStatusMetaRowMapper;
import com.ef.model.event.EventStatusMeta;

@Component
public class EventStatusMetaCache {

  private final String SELECT_STATUS = "select id, name, description from event_status_meta";

  private final JdbcTemplate jdbcTemplate;

  private final Map<String, EventStatusMeta> nameToEventStatusMetaMap;
  private final Map<Integer, EventStatusMeta> idToEventStatusMetaMap;

  @Autowired
  public EventStatusMetaCache(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    nameToEventStatusMetaMap = new HashMap<String, EventStatusMeta>();
    idToEventStatusMetaMap = new HashMap<Integer, EventStatusMeta>();
    refreshCache();
  }

  public EventStatusMeta getEventStatusMeta(String eventStatusMetaName) {
    return nameToEventStatusMetaMap.get(eventStatusMetaName);
  }

  public EventStatusMeta getEventStatusMeta(int eventStatusMetaId) {
    return idToEventStatusMetaMap.get(eventStatusMetaId);
  }

  private void refreshCache() {
    List<EventStatusMeta> domainList = jdbcTemplate.query(SELECT_STATUS, new EventStatusMetaRowMapper());
    for (EventStatusMeta domain : domainList) {
      nameToEventStatusMetaMap.put(domain.getName(), domain);
      idToEventStatusMetaMap.put(domain.getId(), domain);
    }
  }
}
