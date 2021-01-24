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
import com.ef.model.event.EventDeliverable;
import com.ef.model.event.EventDeliverableMetadata;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventBindingModel;
import com.ef.model.event.PREventDeliverableBindingModel;

@Component("insertPREventDeliverables")
public class InsertPREventDeliverable implements Insert<Pair<PREventBindingModel, PREvent>, PREvent> {

  private static final Logger logger = LoggerFactory.getLogger(InsertPREventDeliverable.class);

  private static final int DEFAULT_DELIVERABLE_ID_WHEN_METADATA_NOT_FOUND = -1;
  private final ServiceLoggingUtil loggingUtil = new ServiceLoggingUtil();
  private final String INSERT_STATEMENT = "INSERT INTO event_deliverable_data(event_id, event_deliverable_id) VALUES (?,?)";

  private final JdbcTemplate jdbcTemplate;
  private final EventDeliverableMetadataCache eventDeliverableMetadataCache;

  @Autowired
  public InsertPREventDeliverable(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      EventDeliverableMetadataCache eventDeliverableMetadataCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventDeliverableMetadataCache = eventDeliverableMetadataCache;
  }

  @Override
  public PREvent data(Pair<PREventBindingModel, PREvent> input) {

    List<PREventDeliverableBindingModel> eventDeliverableModelList = input.getLeft().getEventDeliverable();
    PREvent prEvent = input.getRight();
    if (eventDeliverableModelList == null || eventDeliverableModelList.size() == 0) {
      loggingUtil.debug(logger, "No event criteria specified for event: ", input.getRight().getId());
      return input.getRight();
    }
    EventDeliverable[] eventDeliverable = new EventDeliverable[eventDeliverableModelList.size()];
    int count = 0;
    for (PREventDeliverableBindingModel eventDeliverableModel : eventDeliverableModelList) {
      int eventId = input.getRight().getId();
      EventDeliverableMetadata deliverableMetaData = eventDeliverableMetadataCache
          .getEventDeliverable(eventDeliverableModel.getDeliverableName());

      int deliverableId = DEFAULT_DELIVERABLE_ID_WHEN_METADATA_NOT_FOUND;
      String deliverableName = eventDeliverableModel.getDeliverableName();
      if (deliverableMetaData == null) {
        loggingUtil.warn(logger, "Event deliverables metadata not found for: ",
            eventDeliverableModel.getDeliverableName(), "deliverable id will be set to ", deliverableId);
      } else {
        deliverableId = deliverableMetaData.getId();
      }

      jdbcTemplate.update(INSERT_STATEMENT, new Object[] { eventId, deliverableId });

      EventDeliverable event = new EventDeliverable(eventId, deliverableId, deliverableName);
      eventDeliverable[count] = event;
      ++count;
    }

    prEvent.setEventDeliverables(eventDeliverable);
    return prEvent;
  }

}
