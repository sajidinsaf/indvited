package com.ef.dataaccess.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.member.rowmapper.MemberRowMapper;
import com.ef.model.member.Member;
import com.ef.model.member.MemberType;

@Component(value = "queryMemberByUsername")
public class QueryMemberByUsername implements Query<String, Member> {

  private final String SELECT_MEMBER = "select id, firstname, lastname, username, email, phone, date_registered, timestamp_of_last_login, is_enabled from member where username=?";

  private final JdbcTemplate jdbcTemplate;
  private final Query<String, MemberType> queryMemberTypeByUsername;

  @Autowired
  public QueryMemberByUsername(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryMemberTypeByUsername") Query<String, MemberType> queryMemberTypeByUsername) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryMemberTypeByUsername = queryMemberTypeByUsername;
  }

  @Override
  public Member data(String username) {

    MemberType memberType = queryMemberTypeByUsername.data(username);

    Member member = jdbcTemplate.queryForObject(SELECT_MEMBER, new Object[] { username },
        new MemberRowMapper(memberType));
    return member;
  }

}
