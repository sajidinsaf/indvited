package com.ef.dataaccess.spring.member.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.model.member.Member;
import com.ef.model.member.MemberType;

public class MemberRowMapperWithPassword implements RowMapper<Member> {

  private final MemberType memberType;

  public MemberRowMapperWithPassword(MemberType memberType) {
    this.memberType = memberType;
  }

  @Override
  public Member mapRow(ResultSet rs, int rowNum) throws SQLException {

    Member member = new Member(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
        rs.getString("USERNAME"), rs.getString("EMAIL"), rs.getString("PHONE"), rs.getString("PASSWORD"), memberType,
        rs.getTimestamp("DATE_REGISTERED"), rs.getTimestamp("TIMESTAMP_OF_LAST_LOGIN"));
    return member;
  }

//  Member(String id, String firstname, String lastname, String username, String password, String email, int phone,
//      MemberType memberType, Timestamp date_registered, Timestamp timestamp_of_last_login)
}
