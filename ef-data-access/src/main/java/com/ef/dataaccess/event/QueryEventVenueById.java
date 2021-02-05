package com.ef.dataaccess.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.rowmapper.event.EventVenueRowMapper;
import com.ef.model.event.EventVenue;

@Component(value = "queryEventVenueById")
public class QueryEventVenueById implements Query<Integer, EventVenue> {

  private final String SELECT_VENUE = "select * from venue where id=?";

  private final JdbcTemplate jdbcTemplate;
//  private final Query<String, MemberType> queryMemberTypeByEmail;

  @Autowired
  public QueryEventVenueById(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
//      @Qualifier("queryMemberTypeByEmail") Query<String, MemberType> queryMemberTypeByEmail) {
    this.jdbcTemplate = jdbcTemplate;
//    this.queryMemberTypeByEmail = queryMemberTypeByEmail;
  }

  @Override
  public EventVenue data(Integer id) {

    EventVenue eventVenue = jdbcTemplate.queryForObject(SELECT_VENUE, new Object[] { id }, new EventVenueRowMapper());
    return eventVenue;
  }

}
