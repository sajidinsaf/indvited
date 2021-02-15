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
import com.ef.model.event.EventCriteriaMetadata;
import com.ef.model.member.MemberCriteriaData;
import com.ef.model.member.MemberCriteriaDataBindingModel;

@Component("insertMemberCriteriaData")
public class InsertMemberCriteriaData implements Insert<MemberCriteriaDataBindingModel, MemberCriteriaData> {

  private static final Logger logger = LoggerFactory.getLogger(InsertMemberCriteriaData.class);

  private final ServiceLoggingUtil loggingUtil = new ServiceLoggingUtil();
  private final String INSERT_STATEMENT = "INSERT INTO member_criteria_data(id, member_id, criteria_meta_id, member_criteria_value) VALUES (?,?,?,?) ";
  private final String UPDATE_STATEMENT = "UPDATE member_criteria_data SET (member_id, criteria_meta_id, member_criteria_value,timestamp_of_last_update) VALUES (?,?,?,?) where id=?";

  private final JdbcTemplate jdbcTemplate;
  private final EventCriteriaMetadataCache eventCriteriaMetadataCache;
  private final Query<Pair<String, EventCriteriaMetadata>, MemberCriteriaData> queryMemberCriteriaDataById;

  @Autowired
  public InsertMemberCriteriaData(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      EventCriteriaMetadataCache eventCriteriaMetadataCache,
      @Qualifier("queryMemberCriteriaDataById") Query<Pair<String, EventCriteriaMetadata>, MemberCriteriaData> queryMemberCriteriaDataById) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventCriteriaMetadataCache = eventCriteriaMetadataCache;
    this.queryMemberCriteriaDataById = queryMemberCriteriaDataById;
  }

  @Override
  public MemberCriteriaData data(MemberCriteriaDataBindingModel input) {

    int memberId = input.getMemberId();
    int criteriaMetaId = input.getCriteriaMetadataId();
    EventCriteriaMetadata eventCriteriaMetadata = eventCriteriaMetadataCache.getEventCriteria(criteriaMetaId);
    int memberCriteriaValue = input.getMemberCriteriaValue();

    // The id is created as memberId_criteriaMetaId
    String id = new StringBuilder().append(memberId).append("_").append(criteriaMetaId).toString();

    Pair<String, EventCriteriaMetadata> memberCriteriaIdAndEventCriteriaMetadataPair = new LRPair<String, EventCriteriaMetadata>(
        id, eventCriteriaMetadata);
    try {

      queryMemberCriteriaDataById.data(memberCriteriaIdAndEventCriteriaMetadataPair);
      // update if the entry for this memberId_criteriaId exists in the database
      jdbcTemplate.update(UPDATE_STATEMENT, new Object[] { memberId, criteriaMetaId, memberCriteriaValue, id,
          new java.sql.Timestamp(System.currentTimeMillis()) });
    } catch (EmptyResultDataAccessException e) {
      jdbcTemplate.update(INSERT_STATEMENT, new Object[] { id, memberId, criteriaMetaId, memberCriteriaValue });
    }
    return queryMemberCriteriaDataById.data(memberCriteriaIdAndEventCriteriaMetadataPair);

  }

}
