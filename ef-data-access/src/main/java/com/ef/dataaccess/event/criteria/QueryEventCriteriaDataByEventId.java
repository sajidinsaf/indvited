package com.ef.dataaccess.event.criteria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.model.event.EventCriteriaData;

@Component(value = "queryEventCriteriaDataByEventId")
public class QueryEventCriteriaDataByEventId implements Query<Integer, Map<Integer, EventCriteriaData>> {

  private final String SELECT_EVENT_CRITERIA_DATA = "select * from event_criteria_data where event_id=%d";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QueryEventCriteriaDataByEventId(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;

  }

  @Override
  public Map<Integer, EventCriteriaData> data(Integer eventId) {

    List<EventCriteriaData> eventCriteriaData = jdbcTemplate.query(String.format(SELECT_EVENT_CRITERIA_DATA, eventId),
        (rs, rowNum) -> new EventCriteriaData(rs.getInt("EVENT_ID"), rs.getInt("CRITERIA_ID"),
            rs.getInt("CRITERIA_VALUE")));

    Map<Integer, EventCriteriaData> criterionIdToCriterionMap = new HashMap<Integer, EventCriteriaData>();
    for (EventCriteriaData eventCriterion : eventCriteriaData) {
      criterionIdToCriterionMap.put(eventCriterion.getCriterionId(), eventCriterion);
    }
    return criterionIdToCriterionMap;
  }

}
