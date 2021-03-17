package com.ef.dataaccess.member.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.model.member.Member;
import com.ef.model.member.group.MyGroup;
import com.ef.model.member.group.MyGroupMember;
import com.ef.model.member.group.MyGroupMemberBindingModel;

@Component("insertMyGroupMember")
public class InsertMyGroupMember implements Insert<MyGroupMemberBindingModel, MyGroupMember> {

  private final String INSERT_STATEMENT = "INSERT INTO my_group_member(my_group_id, member_id) VALUES (?,?)";
  private final String QUERY_MY_GROUP_MEMBER = "SELECT * from my_group_member where my_group_id=%d and member_id=%d";

  private final JdbcTemplate jdbcTemplate;

  private final Query<Integer, Member> queryMemberById;

  private final Query<Integer, MyGroup> queryMyGroupByGroupId;

  @Autowired
  public InsertMyGroupMember(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryMemberById") Query<Integer, Member> queryMemberById,
      @Qualifier("queryMyGroupByGroupId") Query<Integer, MyGroup> queryMyGroupByGroupId) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryMemberById = queryMemberById;
    this.queryMyGroupByGroupId = queryMyGroupByGroupId;

  }

  @Override
  public MyGroupMember data(MyGroupMemberBindingModel input) {

    int myGroupId = input.getMyGroupId();
    int memberId = input.getMemberId();

    Member member = queryMemberById.data(memberId);

    MyGroup myGroup = queryMyGroupByGroupId.data(myGroupId);

    jdbcTemplate.update(INSERT_STATEMENT, new Object[] { myGroupId, memberId });

    MyGroupMember myGroupMember = jdbcTemplate.query(String.format(QUERY_MY_GROUP_MEMBER, myGroupId, memberId),
        (rs, rowNum) -> new MyGroupMember(myGroup, member, rs.getTimestamp("ADDED_TIMESTAMP"))).get(0);

    return myGroupMember;
  }

}
