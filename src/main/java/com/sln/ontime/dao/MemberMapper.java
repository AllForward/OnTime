package com.sln.ontime.dao;

import com.sln.ontime.model.po.Member;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapper {

    @Insert("insert into member(group_id, member_id) values(#{groupId}, #{memberId})")
    Integer insertMember(Member member);


    @Select("select count(member_id) from member where group_id = #{groupId}")
    Integer getGroupMemberNum(Integer groupId);

    @Select("select group_id, member_id from member where group_id = #{groupId}")
    List<Member> getMemberByGroupId(Integer groupId);

    @Delete("delete from member where member_id = #{memberId} and group_id = #{groupId}")
    Integer deleteGroupMember(Member member);

    @Select("select group_id from member where member_id = #{userId}")
    List<Member> getGroupsByMemberId(Integer userId);

    @Select("select group_id, member_id from member where group_id = #{groupId} and member_id = #{memberId}")
    Member getMember(Integer groupId, Integer memberId);
}
