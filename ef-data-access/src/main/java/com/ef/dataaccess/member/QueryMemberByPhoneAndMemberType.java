package com.ef.dataaccess.member;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.member.rowmapper.MemberRowMapper;
import com.ef.model.member.Member;
import com.ef.model.member.MemberType;

@Component(value = "queryMemberByPhoneAndMemberType")
public class QueryMemberByPhoneAndMemberType implements Query<Pair<String, MemberType>, Member> {

  private final String SELECT_MEMBER = "select id, firstname, lastname, email, gender, phone, date_registered, timestamp_of_last_login, is_enabled from member where phone=? and member_type_id=?";

  private final JdbcTemplate jdbcTemplate;
  private final MemberTypeCache memberTypeCache;

  @Autowired
  public QueryMemberByPhoneAndMemberType(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      MemberTypeCache memberTypeCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.memberTypeCache = memberTypeCache;
  }

  @Override
  public Member data(Pair<String, MemberType> phoneAndMemberType) {

    String phone = phoneAndMemberType.getLeft();
    MemberType memberType = phoneAndMemberType.getRight();
    Integer memberTypeId = phoneAndMemberType.getRight().getId();
    Member member = jdbcTemplate.queryForObject(SELECT_MEMBER, new Object[] { phone, memberTypeId },
        new MemberRowMapper(memberTypeCache));
    return member;
  }

}
