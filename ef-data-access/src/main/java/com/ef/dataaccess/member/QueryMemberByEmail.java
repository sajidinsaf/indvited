package com.ef.dataaccess.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.member.rowmapper.MemberRowMapper;
import com.ef.model.member.Member;
import com.ef.model.member.MemberType;

@Component(value = "queryMemberByEmail")
public class QueryMemberByEmail implements Query<String, Member> {

  private final String SELECT_MEMBER = "select id, firstname, lastname, username, email, phone, date_registered, timestamp_of_last_login from member where email=?";

  private final JdbcTemplate jdbcTemplate;
  private final Query<String, MemberType> queryMemberTypeByEmail;

  @Autowired
  public QueryMemberByEmail(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryMemberTypeByEmail") Query<String, MemberType> queryMemberTypeByEmail) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryMemberTypeByEmail = queryMemberTypeByEmail;
  }

  @Override
  public Member data(String email) {

    MemberType memberType = queryMemberTypeByEmail.data(email);

    Member member = jdbcTemplate.queryForObject(SELECT_MEMBER, new Object[] { email }, new MemberRowMapper(memberType));
    return member;
  }

}
