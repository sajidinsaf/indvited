package com.ef.dataaccess.event.subscription.web.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.event.EventCriteriaMetadataCache;
import com.ef.model.event.EventCriteriaMetadata;
import com.ef.model.member.MemberCriteriaData;

@Component
public class InputDataUtil {

  private final EventCriteriaMetadataCache eventCriteriaMetadataCache;

  @Autowired
  public InputDataUtil(EventCriteriaMetadataCache eventCriteriaMetadataCache) {
    this.eventCriteriaMetadataCache = eventCriteriaMetadataCache;
  }

  public List<MemberCriteriaData> buildMemberCriteriaList(String criteriaString) {
    // {"eventId":"50","firstName":"Sam","lastName":"sdfjn","email":"sfsjf@ssk.com","phone":"9773636366","address":"23
    // Msnjtd","city":"Ornv","preferredDate":"Tue 20 Apr
    // 2021","preferredTime":"1400","criteria":"criterionSepcriterionId1nameValSep3234criterionSepcriterionId2nameValSep13242criterionSepcriterionId3nameValSep12","gender":"F"}
    String criterionSeparator = "criterionSep";
    String criterionIdPropName = "criterionId";
    String nameValueSeparator = "nameValSep";
    String spaceInValue = "spaceInVal";

    // remove the leading criterion separator if any
    if (criteriaString.startsWith(criterionSeparator)) {
      criteriaString = criteriaString.replaceFirst(criterionSeparator, "");
    }

    String[] criteriaStrings = criteriaString.split(criterionSeparator);

    List<MemberCriteriaData> memberCriteriaList = new ArrayList<MemberCriteriaData>();

    for (String criterionString : criteriaStrings) {
      criterionString = criterionString.replaceAll(spaceInValue, " ");
      String criterionIdAttr = criterionString.split(nameValueSeparator)[0];
      Integer memberCriteriaValue = Integer.parseInt(criterionString.split(nameValueSeparator)[1]);
      int criterionId = Integer.parseInt(criterionIdAttr.replaceFirst(criterionIdPropName, ""));

      EventCriteriaMetadata criteriaMetadata = eventCriteriaMetadataCache.getEventCriteria(criterionId);

      MemberCriteriaData memberCriteriaData = new MemberCriteriaData(-1, -1, criteriaMetadata, memberCriteriaValue);

      memberCriteriaList.add(memberCriteriaData);
    }

    return memberCriteriaList;
  }

}
