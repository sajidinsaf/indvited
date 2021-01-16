package com.ef.dataaccess.spring.member.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginControl;
import com.ef.model.member.MemberType;

public class MemberRowMapper implements RowMapper<Member> {

  private final MemberType memberType;
  private final MemberLoginControl memberLoginControl;

  public MemberRowMapper(MemberType memberType) {
    this.memberType = memberType;
    this.memberLoginControl = null;
  }

  public MemberRowMapper(MemberType memberType, MemberLoginControl memberLoginControl) {
    this.memberType = memberType;
    this.memberLoginControl = memberLoginControl;
  }

  @Override
  public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
    Member member = null;
    if (memberLoginControl != null) {
      member = new Member(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
          rs.getString("USERNAME"), rs.getString("EMAIL"), rs.getString("PHONE"), memberType,
          rs.getTimestamp("DATE_REGISTERED"), rs.getTimestamp("TIMESTAMP_OF_LAST_LOGIN"), memberLoginControl);
    } else {
      member = new Member(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
          rs.getString("USERNAME"), rs.getString("EMAIL"), rs.getString("PHONE"), memberType,
          rs.getTimestamp("DATE_REGISTERED"), rs.getTimestamp("TIMESTAMP_OF_LAST_LOGIN"));

    }
    return member;
  }

//  Member(String id, String firstname, String lastname, String username, String password, String email, int phone,
//      MemberType memberType, Timestamp date_registered, Timestamp timestamp_of_last_login)
}
