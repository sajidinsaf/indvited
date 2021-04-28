package com.ef.dataaccess.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.member.rowmapper.MemberRowMapper;
import com.ef.model.member.Member;

@Component(value = "queryMemberById")
public class QueryMemberById implements Query<Integer, Member> {

  private final String SELECT_MEMBER = "select * from member where id=?";

  private final JdbcTemplate jdbcTemplate;
  private final MemberTypeCache memberTypeCache;

  @Autowired
  public QueryMemberById(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      MemberTypeCache memberTypeCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.memberTypeCache = memberTypeCache;
  }

  @Override
  public Member data(Integer id) {
    try {
      Member member = jdbcTemplate.queryForObject(SELECT_MEMBER, new Object[] { id },
          new MemberRowMapper(memberTypeCache));
      return member;
    } catch (EmptyResultDataAccessException e) {
      EmptyResultDataAccessException e1 = new EmptyResultDataAccessException("No member found for id: " + id, 1);
      e1.initCause(e);
      throw e1;
    }

  }

}
