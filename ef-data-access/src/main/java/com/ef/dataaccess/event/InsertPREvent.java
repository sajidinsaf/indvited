package com.ef.dataaccess.event;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.common.LRPair;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.model.event.EventVenue;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventBindingModel;
import com.ef.model.event.PREventLocationBindingModel;
import com.ef.model.member.Member;
import com.fasterxml.uuid.Generators;

@Component("insertPREvent")
public class InsertPREvent implements Insert<PREventBindingModel, PREvent> {

  private final String INSERT_STATEMENT = "INSERT INTO event(uuid, event_type_id, domain_id, cap, exclusions, member_email_id, event_venue_id, notes) VALUES (?,?,?,?,?,?,?,?)";

  private final JdbcTemplate jdbcTemplate;
  private final Query<String, PREvent> queryEventByUuid;
  private final EventTypeCache eventTypeCache;
  private final DomainCache domainCache;
  private final Insert<Pair<PREventBindingModel, PREvent>, PREvent> insertEventCriteria;
  private final Insert<Pair<PREventBindingModel, PREvent>, PREvent> insertEventTimeSlot;
  private final Query<PREventLocationBindingModel, EventVenue> queryVenueByKeyFieldOrInsert;
  private final Query<String, Member> queryMemberByEmail;

  @Autowired
  public InsertPREvent(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryEventByUuid") Query<String, PREvent> queryEventByUuid, EventTypeCache eventTypeCache,
      DomainCache domainCache,
      @Qualifier("insertPREventCriteria") Insert<Pair<PREventBindingModel, PREvent>, PREvent> insertEventCriteria,
      @Qualifier("insertPREventTimeSlot") Insert<Pair<PREventBindingModel, PREvent>, PREvent> insertEventTimeSlot,
      @Qualifier("queryVenueByKeyFieldOrInsert") Query<PREventLocationBindingModel, EventVenue> queryVenueByKeyFieldOrInsert,
      @Qualifier("queryMemberByEmail") Query<String, Member> queryMemberByEmail) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryEventByUuid = queryEventByUuid;
    this.eventTypeCache = eventTypeCache;
    this.domainCache = domainCache;
    this.insertEventCriteria = insertEventCriteria;
    this.insertEventTimeSlot = insertEventTimeSlot;
    this.queryVenueByKeyFieldOrInsert = queryVenueByKeyFieldOrInsert;
    this.queryMemberByEmail = queryMemberByEmail;
  }

//  private PREventTimeSlotBindingModel[] prEventTimeSlotBindingModel;

//  private PREventCriteriaBindingModel[] eventCriteria;

//  private String notes;  
//  
  @Override
  public PREvent data(final PREventBindingModel input) {

    String uuid = Generators.timeBasedGenerator().generate().toString();

    EventVenue eventVenue = queryVenueByKeyFieldOrInsert.data(input.getEventLocation());
    input.getEventLocation().setId(eventVenue.getId());

    Integer eventTypeId = eventTypeCache.getEventType(input.getEventType()).getId();
    Integer domainId = domainCache.getDomain(input.getDomainName()).getId();
    String cap = input.getCap() == null ? "" : input.getCap();
    String exclusions = input.getExclusions();
    String emailId = input.getEventCreatorEmailId();
    Integer eventLocationId = getLocationId(input);
    String notes = input.getNotes() == null ? "" : input.getNotes();

//    insertEventTimeSlots(input);

    jdbcTemplate.update(INSERT_STATEMENT,
        new Object[] { uuid, eventTypeId, domainId, cap, exclusions, emailId, eventLocationId, notes });

    final PREvent event = queryEventByUuid.data(uuid);

    event.setEventType(eventTypeCache.getEventType(eventTypeId));

    Pair<PREventBindingModel, PREvent> eventPair = new LRPair<PREventBindingModel, PREvent>(input, event);
    insertEventCriteria.data(eventPair);
    insertEventTimeSlot.data(eventPair);

    Member member = queryMemberByEmail.data(input.getEventCreatorEmailId());
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
