package com.ef.dataaccess.member;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.common.LRPair;
import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.dataaccess.event.EventCriteriaMetadataCache;
import com.ef.model.member.MemberCriteriaData;
import com.ef.model.member.MemberCriteriaDataBindingModel;

@Component("insertMemberCriteriaData")
public class InsertMemberCriteriaData implements Insert<MemberCriteriaDataBindingModel, MemberCriteriaData> {

  private static final Logger logger = LoggerFactory.getLogger(InsertMemberCriteriaData.class);

  private final ServiceLoggingUtil loggingUtil = new ServiceLoggingUtil();
  private final String INSERT_STATEMENT = "INSERT INTO member_criteria_data(member_id, criteria_meta_id, member_criteria_value) VALUES (?,?,?) ";
  private final String UPDATE_STATEMENT = "UPDATE member_criteria_data SET criteria_meta_id=?, member_criteria_value=?, timestamp_of_last_update=? where member_id=? and criteria_meta_id=?";

  private final JdbcTemplate jdbcTemplate;
  private final EventCriteriaMetadataCache eventCriteriaMetadataCache;
  private final Query<Pair<Integer, Integer>, MemberCriteriaData> queryMemberCriteriaDataByMemberIdAndCriteriaId;

  @Autowired
  public InsertMemberCriteriaData(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      EventCriteriaMetadataCache eventCriteriaMetadataCache,
      @Qualifier("queryMemberCriteriaDataByMemberIdAndCriteriaId") Query<Pair<Integer, Integer>, MemberCriteriaData> queryMemberCriteriaDataByMemberIdAndCriteriaId) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventCriteriaMetadataCache = eventCriteriaMetadataCache;
    this.queryMemberCriteriaDataByMemberIdAndCriteriaId = queryMemberCriteriaDataByMemberIdAndCriteriaId;
  }

  @Override
  public MemberCriteriaData data(MemberCriteriaDataBindingModel input) {

    int memberId = input.getMemberId();
    int criteriaMetaId = input.getCriteriaMetadataId();
    int memberCriteriaValue = input.getMemberCriteriaValue();

    MemberCriteriaData memberCriteriaData = null;
    try {

      memberCriteriaData = queryMemberCriteriaDataByMemberIdAndCriteriaId
          .data(new LRPair<Integer, Integer>(memberId, criteriaMetaId));
      // update if the entry for this memberId_criteriaId exists in the database
      jdbcTemplate.update(UPDATE_STATEMENT, new Object[] { criteriaMetaId, memberCriteriaValue,
          new java.sql.Timestamp(System.currentTimeMillis()), memberId, criteriaMetaId });
    } catch (EmptyResultDataAccessException e) {
      jdbcTemplate.update(INSERT_STATEMENT, new Object[] { memberId, criteriaMetaId, memberCriteriaValue });
    }

    if (memberCriteriaData == null) {
      memberCriteriaData = queryMemberCriteriaDataByMemberIdAndCriteriaId
          .data(new LRPair<Integer, Integer>(memberId, criteriaMetaId));
    }

    return memberCriteriaData;
  }

}
