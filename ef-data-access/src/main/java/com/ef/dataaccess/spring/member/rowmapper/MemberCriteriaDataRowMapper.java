package com.ef.dataaccess.spring.member.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.model.event.EventCriteriaMetadata;
import com.ef.model.member.MemberCriteriaData;

public class MemberCriteriaDataRowMapper implements RowMapper<MemberCriteriaData> {

  private final EventCriteriaMetadata eventCriteriaMetadata;

  public MemberCriteriaDataRowMapper(EventCriteriaMetadata eventCriteriaMetadata) {
    this.eventCriteriaMetadata = eventCriteriaMetadata;
  }

  @Override
  public MemberCriteriaData mapRow(ResultSet rs, int rowNum) throws SQLException {

    MemberCriteriaData memberCriteriaData = new MemberCriteriaData(rs.getString("ID"), rs.getInt("MEMBER_ID"),
        eventCriteriaMetadata, rs.getInt("MEMBER_CRITERIA_VALUE"));
    return memberCriteriaData;
  }

}
