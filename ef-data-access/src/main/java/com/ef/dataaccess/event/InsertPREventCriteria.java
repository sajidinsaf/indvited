package com.ef.dataaccess.event;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.core.ForumCache;
import com.ef.model.core.Forum;
import com.ef.model.event.EventCriteria;
import com.ef.model.event.EventCriteriaMetadata;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventBindingModel;
import com.ef.model.event.PREventCriteriaBindingModel;

@Component("insertPREventCriteria")
public class InsertPREventCriteria implements Insert<Pair<PREventBindingModel, PREvent>, PREvent> {

  private static final Logger logger = LoggerFactory.getLogger(InsertPREventCriteria.class);

  private static final int DEFAULT_CRITERIA_ID_WHEN_METADATA_NOT_FOUND = -1;
  private final ServiceLoggingUtil loggingUtil = new ServiceLoggingUtil();
  private final String INSERT_STATEMENT = "INSERT INTO event_criteria_data(event_id, criteria_id, criteria_value) VALUES (?,?,?)";

  private final JdbcTemplate jdbcTemplate;
  private final EventCriteriaMetadataCache eventCriteriaMetadataCache;
  private final ForumCache forumCache;

  @Autowired
  public InsertPREventCriteria(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      EventCriteriaMetadataCache eventCriteriaMetadataCache, ForumCache forumCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventCriteriaMetadataCache = eventCriteriaMetadataCache;
    this.forumCache = forumCache;
  }

  @Override
  public PREvent data(Pair<PREventBindingModel, PREvent> input) {
    loggingUtil.debug(logger, "Creating entry for: ", input);
    List<PREventCriteriaBindingModel> eventCriteriaModelList = input.getLeft().getEventCriteria();
    PREvent prEvent = input.getRight();
    if (eventCriteriaModelList == null || eventCriteriaModelList.size() == 0) {
      loggingUtil.debug(logger, "No event criteria specified for event: ", input.getRight().getId());
      return input.getRight();
    }
    EventCriteria[] eventCriteria = new EventCriteria[eventCriteriaModelList.size()];
    int count = 0;
    for (PREventCriteriaBindingModel eventCriteriaModel : eventCriteriaModelList) {
      int eventId = input.getRight().getId();
      String criteriaName = eventCriteriaModel.getCriterionName();
      EventCriteriaMetadata criteriaMetaData = eventCriteriaMetadataCache.getEventCriteria(criteriaName);

      loggingUtil.debug(logger, "Got event criterion metadata : ", criteriaMetaData, " for metadata name: ",
          criteriaName);
      int criteriaId = DEFAULT_CRITERIA_ID_WHEN_METADATA_NOT_FOUND;

      if (criteriaMetaData == null) {
        loggingUtil.warn(logger, "Event criteria metadata not found for: ", criteriaName,
            ". Criteria id will be set to ", criteriaId + " [" + eventCriteriaMetadataCache + "]");
      } else {
        criteriaId = criteriaMetaData.getId();
      }

      int criteriaValue = eventCriteriaModel.getMinValue();

      jdbcTemplate.update(INSERT_STATEMENT, new Object[] { eventId, criteriaId, criteriaValue });

      Forum forum = forumCache.getForum(criteriaMetaData.getForumId());
      EventCriteria event = new EventCriteria(criteriaId, criteriaName, criteriaValue, forum);
      eventCriteria[count] = event;
      ++count;
    }

    prEvent.setEventCriteria(eventCriteria);
    return prEvent;
  }

}
