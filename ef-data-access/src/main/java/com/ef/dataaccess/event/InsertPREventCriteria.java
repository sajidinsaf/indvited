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

  @Autowired
  public InsertPREventCriteria(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      EventCriteriaMetadataCache eventCriteriaMetadataCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventCriteriaMetadataCache = eventCriteriaMetadataCache;
  }

  @Override
  public PREvent data(Pair<PREventBindingModel, PREvent> input) {

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
      EventCriteriaMetadata criteriaMetaData = eventCriteriaMetadataCache
          .getEventCriteria(eventCriteriaModel.getCriterionName());

      int criteriaId = DEFAULT_CRITERIA_ID_WHEN_METADATA_NOT_FOUND;
      String criteriaName = eventCriteriaModel.getCriterionName();
      if (criteriaMetaData == null) {
        loggingUtil.warn(logger, "Event criteria metadata not found for: ", eventCriteriaModel.getCriterionName(),
            "criteria id will be set to ", criteriaId);
      } else {
        criteriaId = criteriaMetaData.getId();
      }

      int criteriaValue = eventCriteriaModel.getMinValue();

      jdbcTemplate.update(INSERT_STATEMENT, new Object[] { eventId, criteriaId, criteriaValue });

      EventCriteria event = new EventCriteria(criteriaId, criteriaName, criteriaValue);
      eventCriteria[count] = event;
      ++count;
    }

    prEvent.setEventCriteria(eventCriteria);
    return prEvent;
  }

}
