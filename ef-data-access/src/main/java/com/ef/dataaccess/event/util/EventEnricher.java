package com.ef.dataaccess.event.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.core.ForumCache;
import com.ef.dataaccess.event.EventCriteriaMetadataCache;
import com.ef.model.core.Forum;
import com.ef.model.event.EventCriteria;
import com.ef.model.event.EventCriteriaData;
import com.ef.model.event.EventCriteriaMetadata;
import com.ef.model.event.PREvent;

@Component
public class EventEnricher {

  private final EventCriteriaMetadataCache eventCriteriaMetadataCache;
  private final ForumCache forumCache;
  private final Query<Integer, Map<Integer, EventCriteriaData>> queryEventCriteriaDataByEventId;

  @Autowired
  public EventEnricher(EventCriteriaMetadataCache eventCriteriaMetadataCache, ForumCache forumCache,
      @Qualifier("queryEventCriteriaDataByEventId") Query<Integer, Map<Integer, EventCriteriaData>> queryEventCriteriaDataByEventId) {
    this.eventCriteriaMetadataCache = eventCriteriaMetadataCache;
    this.forumCache = forumCache;
    this.queryEventCriteriaDataByEventId = queryEventCriteriaDataByEventId;
  }

  public void populateEventCriteria(PREvent event) {
    Map<Integer, EventCriteriaData> eventCriteriaDataByEventIdMap = queryEventCriteriaDataByEventId.data(event.getId());

    populateEventCriteria(event, new ArrayList<EventCriteriaData>(eventCriteriaDataByEventIdMap.values()));
  }

  public void populateEventCriteria(PREvent event, List<EventCriteriaData> eventCriteriaList) {

    EventCriteria[] eventCriteriaArray = new EventCriteria[eventCriteriaList.size()];
    for (int i = 0; i < eventCriteriaList.size(); i++) {
      EventCriteriaData eventCriterionData = eventCriteriaList.get(i);
      int criterionId = eventCriterionData.getCriterionId();
      EventCriteriaMetadata criterionMeta = eventCriteriaMetadataCache.getEventCriteria(criterionId);

      Forum forum = forumCache.getForum(criterionMeta.getForumId());

      EventCriteria eventCriteria = new EventCriteria(criterionId, criterionMeta.getName(),
          eventCriterionData.getCriterionValue(), forum);
      eventCriteriaArray[i] = eventCriteria;
    }
    event.setEventCriteria(eventCriteriaArray);

  }

}
