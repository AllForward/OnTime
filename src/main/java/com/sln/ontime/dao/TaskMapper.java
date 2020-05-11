package com.sln.ontime.dao;

import com.sln.ontime.model.po.Task;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Red Date.
 * @date 2020/5/9 0:08
 */
@Mapper
public interface TaskMapper {

    @Delete("delete from task where task_id = #{taskId}")
    Integer deleteTaskByTaskId(Integer taskId);

    @Options(useGeneratedKeys = true,keyProperty = "taskId", keyColumn = "task_id")
    @Insert("insert into task(plan_id,task_name,lasting,start_time,end_time,priority,status)" +
            "values (#{planId},#{taskName},#{lasting},#{startTime},#{endTime},#{priority},#{status})")
    Integer insertTask(Task task);

    @Update("update task set task_name = #{taskName},lasting = #{lasting},start_time = #{startTime}," +
            "end_time = #{endTime},priority = #{priority},status = #{status} where task_id = #{taskId}")
    Integer updateTask(Task task);

    @Select("select task_id,plan_id,task_name,lasting,start_time,end_time,priority,status from task where plan_id = #{planId}")
    List<Task> getTaskByPlanId(Integer planId);
}
