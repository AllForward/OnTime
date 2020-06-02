package com.sln.ontime.service;

import com.sln.ontime.model.vo.PlanVo;

import java.util.List;

/**
 * 个人计划
 * @author Red Date.
 * @date 2020/5/9 12:50
 */
public interface PersonalPlanService {

    /**
     * 获取个人计划的内容
     * @param planId 计划的id
     * @return 个人计划的内容
     */
    PlanVo getPersonalPlan(Integer planId);

    /**
     * 获取用户所有的计划包括子任务的内容
     * @param userId 用户的id
     * @return
     */
    List<PlanVo> getPersonalPlanList(Integer userId);

    /**
     * 添加个人计划
     * @param personalPlanVo 个人计划内容
     * @return 插入的内容
     */
    PlanVo insertPersonalPlan(PlanVo personalPlanVo);

    /**
     * 修改个人计划的内容
     * @param personalPlanVo 个人计划的内容
     * @return 修改后的内容
     */
    PlanVo updatePersonalPlan(PlanVo personalPlanVo);

    /**
     * 删除个人计划
     * @param planId 个人计划的id
     * @return 是否删除成功，成功返回true
     */
    boolean deletePersonalPlan(Integer planId);

    /**
     * 删除子任务
     * @param taskId 子任务的id
     * @return 是否删除成功，成功返回true
     */
    boolean deleteTask(Integer taskId);

}
