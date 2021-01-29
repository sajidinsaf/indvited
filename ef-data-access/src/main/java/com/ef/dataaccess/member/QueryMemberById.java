package com.ef.dataaccess.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.member.rowmapper.MemberRowMapper;
import com.ef.model.member.Member;
import com.ef.model.member.MemberType;

@Component(value = "queryMemberById")
public class QueryMemberById implements Query<Integer, Member> {

  private final String SELECT_MEMBER = "select id, firstname, lastname, email, gender, phone, date_registered, timestamp_of_last_login, is_enabled from member where id=?";

  private final JdbcTemplate jdbcTemplate;
  private final Query<Integer, MemberType> queryMemberTypeById;

  @Autowired
  public QueryMemberById(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryMemberTypeById") Query<Integer, MemberType> queryMemberTypeById) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryMemberTypeById = queryMemberTypeById;
  }

  @Override
  public Member data(Integer id) {

    MemberType memberType = queryMemberTypeById.data(id);

    Member member = jdbcTemplate.queryForObject(SELECT_MEMBER, new Object[] { id }, new MemberRowMapper(memberType));
    return member;
  }

}
