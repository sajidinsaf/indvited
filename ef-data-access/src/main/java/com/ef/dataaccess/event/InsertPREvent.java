package com.ef.dataaccess.event;

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
import com.ef.dataaccess.common.UuidGenerator;
import com.ef.model.event.EventType;
import com.ef.model.event.EventVenue;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventBindingModel;
import com.ef.model.event.PREventLocationBindingModel;
import com.ef.model.member.Member;

@Component("insertPREvent")
public class InsertPREvent implements Insert<PREventBindingModel, PREvent> {

  private static final Logger logger = LoggerFactory.getLogger(InsertPREvent.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final String INSERT_STATEMENT = "INSERT INTO event(uuid, event_type_id, domain_id, cap, exclusions, member_email_id, event_venue_id, notes) VALUES (?,?,?,?,?,?,?,?)";

  private final JdbcTemplate jdbcTemplate;
  private final Query<String, PREvent> queryEventByUuid;
  private final EventTypeCache eventTypeCache;
  private final DomainCache domainCache;
  private final Insert<Pair<PREventBindingModel, PREvent>, PREvent> insertEventCriteria;
  private final Insert<Pair<PREventBindingModel, PREvent>, PREvent> insertEventDeliverables;
  private final Query<PREventLocationBindingModel, EventVenue> queryVenueByKeyFieldOrInsert;
  private final Query<String, Member> queryMemberByEmail;
  private final UuidGenerator uuidGenerator;

  @Autowired
  public InsertPREvent(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryEventByUuid") Query<String, PREvent> queryEventByUuid, EventTypeCache eventTypeCache,
      DomainCache domainCache,
      @Qualifier("insertPREventCriteria") Insert<Pair<PREventBindingModel, PREvent>, PREvent> insertEventCriteria,
      @Qualifier("insertPREventDeliverables") Insert<Pair<PREventBindingModel, PREvent>, PREvent> insertEventDeliverables,
      @Qualifier("queryVenueByKeyFieldOrInsert") Query<PREventLocationBindingModel, EventVenue> queryVenueByKeyFieldOrInsert,
      @Qualifier("queryMemberByEmail") Query<String, Member> queryMemberByEmail,
      @Qualifier("uuidGenerator") UuidGenerator uuidGenerator) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryEventByUuid = queryEventByUuid;
    this.eventTypeCache = eventTypeCache;
    this.domainCache = domainCache;
    this.insertEventCriteria = insertEventCriteria;
    this.insertEventDeliverables = insertEventDeliverables;
    this.queryVenueByKeyFieldOrInsert = queryVenueByKeyFieldOrInsert;
    this.queryMemberByEmail = queryMemberByEmail;
    this.uuidGenerator = uuidGenerator;
  }

//  private PREventTimeSlotBindingModel[] prEventTimeSlotBindingModel;

//  private PREventCriteriaBindingModel[] eventCriteria;

//  private String notes;  
//  
  @Override
  public PREvent data(final PREventBindingModel input) {

    logUtil.debug(logger, " Input Event Details: ", input);
    Member member = null;
    String emailId = input.getEventCreatorEmailId();
    try {

      member = queryMemberByEmail.data(emailId);
    } catch (EmptyResultDataAccessException e) {
      logUtil.warn(logger, "No member information found for emailId: ", emailId, " Input Event Details: ", input);
      return null;
    }

    String uuid = uuidGenerator.getUuid();

    EventVenue eventVenue = queryVenueByKeyFieldOrInsert.data(input.getEventLocation());
    input.getEventLocation().setId(eventVenue.getId());

    EventType eventType = eventTypeCache.getEventType(input.getEventType());
    if (eventType == null) {
      logUtil.warn(logger, "Invalid event type. No mapping entry found. ", input.getEventType(),
          " Input Event Details: ", input);
      return null;

    }

    logUtil.debug(logger, eventType);

    Integer eventTypeId = eventType.getId();
    logUtil.debug(logger, "EventType Id:", eventTypeId);

    Integer domainId = domainCache.getDomain(eventType.getDomainId()).getId();

    logUtil.debug(logger, "Domain Id:", domainId);

    String cap = input.getCap() == null ? "" : input.getCap();
    String exclusions = input.getExclusions();

    Integer eventLocationId = getLocationId(input);
    String notes = input.getNotes() == null ? "" : input.getNotes();

//    insertEventTimeSlots(input);

    jdbcTemplate.update(INSERT_STATEMENT,
        new Object[] { uuid, eventTypeId, domainId, cap, exclusions, emailId, eventLocationId, notes });

    final PREvent event = queryEventByUuid.data(uuid);

    event.setEventType(eventTypeCache.getEventType(eventTypeId));

    Pair<PREventBindingModel, PREvent> eventPair = new LRPair<PREventBindingModel, PREvent>(input, event);
    insertEventCriteria.data(eventPair);
    insertEventDeliverables.data(eventPair);

    event.setMember(member);
    event.setEventVenue(eventVenue);
    return event;
  }

  private Integer getLocationId(PREventBindingModel input) {
    return input.getEventLocation().getId();
  }

}

//
//private String eventCreatorId;
//private String eventType;
//private PREventTimeSlotBindingModel[] prEventTimeSlotBindingModel;
//private String cap;
//private String exclusions;
//private PREventCriteriaBindingModel[] eventCriteria;
//private PREventLocationBindingModel eventLocation;
//private String notes; 
