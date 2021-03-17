package com.ef.dataaccess.member.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.model.member.group.MyGroupType;

@Component
public class MyGroupTypeCache {

  private final String SELECT = "select id, name, description from my_group_type";

  private final JdbcTemplate jdbcTemplate;

  private final Map<String, MyGroupType> nameToMyGroupTypeMap;
  private final Map<Integer, MyGroupType> idToMyGroupTypeMap;
  private List<MyGroupType> myGroupTypes;

  @Autowired
  public MyGroupTypeCache(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    nameToMyGroupTypeMap = new HashMap<String, MyGroupType>();
    idToMyGroupTypeMap = new HashMap<Integer, MyGroupType>();
    refreshCache();
  }

  public MyGroupType getMyGroupType(String memberTypeName) {
    return nameToMyGroupTypeMap.get(memberTypeName);
  }

  public MyGroupType getMemberType(int memberTypeId) {
    return idToMyGroupTypeMap.get(memberTypeId);
  }

  private void refreshCache() {
    myGroupTypes = jdbcTemplate.query(SELECT,
        (rs, rowNum) -> new MyGroupType(rs.getInt("ID"), rs.getString("NAME"), rs.getString("DESCRIPTION")));
    for (MyGroupType myGroupType : myGroupTypes) {
      nameToMyGroupTypeMap.put(myGroupType.getName(), myGroupType);
      idToMyGroupTypeMap.put(myGroupType.getId(), myGroupType);
    }
  }

  public List<MyGroupType> getMyGroupTypes() {
    return myGroupTypes;
  }

}
