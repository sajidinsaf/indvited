package com.ef.dataaccess.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.spring.member.rowmapper.MemberTypeRowMapper;
import com.ef.model.member.MemberType;

@Component
public class MemberTypeCache {

  private final String SELECT_MEMBER_TYPE = "select id, name from member_type";

  private final JdbcTemplate jdbcTemplate;

  private final Map<String, MemberType> nameToMemberTypeMap;
  private final Map<Integer, MemberType> idToMemberTypeMap;

  @Autowired
  public MemberTypeCache(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    nameToMemberTypeMap = new HashMap<String, MemberType>();
    idToMemberTypeMap = new HashMap<Integer, MemberType>();
    refreshCache();
  }

  public MemberType getMemberType(String memberTypeName) {
    return nameToMemberTypeMap.get(memberTypeName);
  }

  public MemberType getMemberType(int memberTypeId) {
    return idToMemberTypeMap.get(memberTypeId);
  }

  private void refreshCache() {
    List<MemberType> memberTypeList = jdbcTemplate.query(SELECT_MEMBER_TYPE, new MemberTypeRowMapper());
    for (MemberType memberType : memberTypeList) {
      nameToMemberTypeMap.put(memberType.getName(), memberType);
      idToMemberTypeMap.put(memberType.getId(), memberType);
    }
  }
}
