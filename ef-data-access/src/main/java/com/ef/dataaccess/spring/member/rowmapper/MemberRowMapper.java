package com.ef.dataaccess.spring.member.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.dataaccess.member.MemberTypeCache;
import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginControl;
import com.ef.model.member.MemberType;

public class MemberRowMapper implements RowMapper<Member> {

  private final MemberTypeCache memberTypeCache;
  private final MemberLoginControl memberLoginControl;

  public MemberRowMapper(MemberTypeCache memberTypeCache) {
    this.memberTypeCache = memberTypeCache;
    this.memberLoginControl = null;
  }

  public MemberRowMapper(MemberTypeCache memberTypeCache, MemberLoginControl memberLoginControl) {
    this.memberTypeCache = memberTypeCache;
    this.memberLoginControl = memberLoginControl;
  }

  @Override
  public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
    Member member = null;
    MemberType memberType = memberTypeCache.getMemberType(rs.getInt("MEMBER_TYPE_ID"));
    if (memberLoginControl != null) {
      member = new Member(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getString("EMAIL"),
          rs.getString("GENDER"), rs.getString("PHONE"), memberType, rs.getTimestamp("DATE_REGISTERED"),
          rs.getTimestamp("TIMESTAMP_OF_LAST_LOGIN"), memberLoginControl, rs.getBoolean("IS_ENABLED"));
    } else {
      member = new Member(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getString("EMAIL"),
          rs.getString("GENDER"), rs.getString("PHONE"), memberType, rs.getTimestamp("DATE_REGISTERED"),
          rs.getTimestamp("TIMESTAMP_OF_LAST_LOGIN"), rs.getBoolean("IS_ENABLED"));

    }
    return member;
  }

//  Member(String id, String firstname, String lastname, String username, String password, String email, int phone,
//      MemberType memberType, Timestamp date_registered, Timestamp timestamp_of_last_login)
}
