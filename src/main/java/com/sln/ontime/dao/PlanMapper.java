package com.sln.ontime.dao;

import com.sln.ontime.model.po.Plan;
import org.apache.ibatis.annotations.*;

/**
 * @author Red Date.
 * @date 2020/5/8 23:41
 */
@Mapper
public interface PlanMapper {

    @Insert("insert into plan (user_id,plan_name,type) values (#{userId},#{planName},#{type})")
    @Options(useGeneratedKeys = true,keyProperty = "planId", keyColumn = "plan_id")
    Integer insertPlan(Plan plan);

    @Delete("delete from plan where plan_id = #{planId}")
    Integer deletePlanByPlanId(Integer planId);

    @Update("update plan set plan_name = #{planName} where plan_id = #{planId}")
    Integer updatePlanByPlanId(@Param("planName")String planName,@Param("planId")Integer planId);

    @Select("select plan_id,user_id,plan_name,type from plan where plan_id = #{planId}")
    Plan getPlanByPlanId(Integer planId);

}
