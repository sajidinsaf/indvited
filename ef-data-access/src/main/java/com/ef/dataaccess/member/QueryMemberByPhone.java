package com.ef.dataaccess.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.member.rowmapper.MemberRowMapper;
import com.ef.model.member.Member;
import com.ef.model.member.MemberType;

@Component(value = "queryMemberByPhone")
public class QueryMemberByPhone implements Query<String, Member> {

  private final String SELECT_MEMBER = "select id, firstname, lastname, username, email, phone, date_registered, timestamp_of_last_login from member where phone=?";

  private final JdbcTemplate jdbcTemplate;
  private final Query<String, MemberType> queryMemberTypeByPhone;

  @Autowired
  public QueryMemberByPhone(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryMemberTypeByPhone") Query<String, MemberType> queryMemberTypeByPhone) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryMemberTypeByPhone = queryMemberTypeByPhone;
  }

  @Override
  public Member data(String phone) {

    MemberType memberType = queryMemberTypeByPhone.data(phone);

    Member member = jdbcTemplate.queryForObject(SELECT_MEMBER, new Object[] { phone }, new MemberRowMapper(memberType));
    return member;
  }

}
