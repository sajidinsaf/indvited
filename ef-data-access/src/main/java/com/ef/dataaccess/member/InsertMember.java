package com.ef.dataaccess.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.model.member.Member;
import com.ef.model.member.MemberRegistrationBindingModel;

@Component("insertMember")
public class InsertMember implements Insert<MemberRegistrationBindingModel, Member> {

  private final String INSERT_STATEMENT = "INSERT INTO member(firstname, lastname, username, password, email, phone, member_type_id) VALUES (?,?,?,?,?,?,?)";
  private final JdbcTemplate jdbcTemplate;
  private final Query<String, Member> queryMemberByName;
  private final MemberTypeCache memberTypeCache;
  private final PasswordEncoder encoder;

  @Autowired
  public InsertMember(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryMemberByUsername") Query<String, Member> queryMemberByName, MemberTypeCache memberTypeCache,
      PasswordEncoder encoder) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryMemberByName = queryMemberByName;
    this.memberTypeCache = memberTypeCache;
    this.encoder = encoder;
  }

  @Override
  public Member data(MemberRegistrationBindingModel input) {

    int memberTypeId = memberTypeCache.getMemberType(input.getMemberType()).getId();
    String password = encryptPassword(input.getPassword());

    jdbcTemplate.update(INSERT_STATEMENT, new Object[] { input.getFirstName(), input.getLastName(), input.getUsername(),
        password, input.getEmail(), input.getPhone(), memberTypeId });

    Member member = queryMemberByName.data(input.getUsername());

    return member;
  }

  private String encryptPassword(String password) {

    return encoder.encode(password);
  }

}
