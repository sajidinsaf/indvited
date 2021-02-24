package com.ef.dataaccess.event.subscription;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.model.event.EventScheduleSubscription;

@Component(value = "queryEventScheduleSubscriptionCountByScheduleIdAndStatusIds")
public class QueryEventScheduleSubscriptionCountByScheduleIdAndStatusIds implements Query<Pair<Long, int[]>, Integer> {

  private final String SELECT_COUNT = "select count(*) from event_schedule_subscription where event_schedule_id=%d and status_id in (%s)";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QueryEventScheduleSubscriptionCountByScheduleIdAndStatusIds(
      @Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryEventScheduleSubscriptionByScheduleId") Query<Long, List<EventScheduleSubscription>> queryEventScheduleSubscriptionByScheduleId) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Integer data(Pair<Long, int[]> scheduleAndStatusCodes) {

    Long scheduleId = scheduleAndStatusCodes.getLeft();

    String statusIds = Arrays.toString(scheduleAndStatusCodes.getRight()).replaceAll("\\[", "").replaceAll("\\]", "")
        .replaceAll(" ", "");

    String sql = String.format(SELECT_COUNT, new Object[] { scheduleId, statusIds });
    Integer count = jdbcTemplate.queryForObject(sql, Integer.class);

    return count;
  }

}
