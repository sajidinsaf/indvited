package com.ef.dataaccess.event.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.rowmapper.event.schedule.PREventScheduleRowMapper;
import com.ef.model.event.PREventSchedule;

@Component(value = "queryEventScheduleById")
public class QueryEventScheduleById implements Query<Long, PREventSchedule> {

  private final String SELECT_EVENT = "select * from event_schedule where id=?";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QueryEventScheduleById(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public PREventSchedule data(Long id) {

    PREventSchedule prEvent = jdbcTemplate.queryForObject(SELECT_EVENT, new Object[] { id },
        new PREventScheduleRowMapper());
    return prEvent;
  }

}
