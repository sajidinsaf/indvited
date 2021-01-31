package com.ef.dataaccess.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.member.rowmapper.PREventTableRowMapper;
import com.ef.model.event.PREvent;

@Component(value = "queryEventById")
public class QueryEventById implements Query<Integer, PREvent> {

  private final String SELECT_EVENT = "select id, uuid, event_type_id, domain_id, event_venue_id, cap, created_date, exclusions, member_email_id, notes from event where id=?";

  private final JdbcTemplate jdbcTemplate;
//  private final Query<String, MemberType> queryMemberTypeByEmail;

  @Autowired
  public QueryEventById(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
//      @Qualifier("queryMemberTypeByEmail") Query<String, MemberType> queryMemberTypeByEmail) {
    this.jdbcTemplate = jdbcTemplate;
//    this.queryMemberTypeByEmail = queryMemberTypeByEmail;
  }

  @Override
  public PREvent data(Integer id) {

    PREvent prEvent = jdbcTemplate.queryForObject(SELECT_EVENT, new Object[] { id }, new PREventTableRowMapper());
    return prEvent;
  }

}
