package com.ef.member.group.controller;

import static com.ef.member.group.controller.GroupControllerConstants.POST_V1_GROUP_ADD_MEMBER;
import static com.ef.member.group.controller.GroupControllerConstants.POST_V1_GROUP_CREATE;
import static com.ef.member.group.controller.GroupControllerConstants.POST_V1_GROUP_GET_TYPES;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.member.group.MyGroupTypeCache;
import com.ef.model.member.group.MyGroup;
import com.ef.model.member.group.MyGroupBindingModel;
import com.ef.model.member.group.MyGroupMember;
import com.ef.model.member.group.MyGroupMemberBindingModel;

/**
 * Handles requests for the group service.
 */
@Controller
public class GroupController {

  private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final Insert<MyGroupBindingModel, MyGroup> insertMyGroup;
  private final MyGroupTypeCache myGroupTypeCache;
  private final Insert<MyGroupMemberBindingModel, MyGroupMember> insertMyGroupMember;

  @Autowired
  public GroupController(@Qualifier("insertMyGroup") Insert<MyGroupBindingModel, MyGroup> insertMyGroup,
      @Qualifier("insertMyGroupMember") Insert<MyGroupMemberBindingModel, MyGroupMember> insertMyGroupMember,
      MyGroupTypeCache myGroupTypeCache) {
    this.insertMyGroup = insertMyGroup;
    this.myGroupTypeCache = myGroupTypeCache;
    this.insertMyGroupMember = insertMyGroupMember;
  }

  @PostMapping(POST_V1_GROUP_ADD_MEMBER)
  public @ResponseBody ResponseEntity<?> addGroupMember(@RequestBody MyGroupMemberBindingModel input) {

    logUtil.debug(logger, "Received group member details", input);
    MyGroupMember myGroupMember = insertMyGroupMember.data(input);
    return new ResponseEntity<MyGroupMember>(myGroupMember, HttpStatus.OK);
  }

  @PostMapping(POST_V1_GROUP_CREATE)
  public @ResponseBody ResponseEntity<?> createGroup(@RequestBody MyGroupBindingModel input) {

    logUtil.debug(logger, "Received group member details", input);
    MyGroup myGroup = insertMyGroup.data(input);

    return new ResponseEntity<MyGroup>(myGroup, HttpStatus.OK);
  }

  @GetMapping(POST_V1_GROUP_GET_TYPES)
  public @ResponseBody ResponseEntity<?> getMyGroupTypes() {

    return new ResponseEntity<MyGroupTypeCache>(myGroupTypeCache, HttpStatus.OK);
  }
}
