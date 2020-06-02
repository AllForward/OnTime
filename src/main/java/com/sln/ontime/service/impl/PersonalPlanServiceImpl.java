package com.sln.ontime.service.impl;

import com.sln.ontime.dao.PlanMapper;
import com.sln.ontime.dao.TaskMapper;
import com.sln.ontime.exception.ErrorException;
import com.sln.ontime.model.po.Plan;
import com.sln.ontime.model.po.Task;
import com.sln.ontime.model.vo.PlanVo;
import com.sln.ontime.service.PersonalPlanService;
import com.sln.ontime.util.VerifyUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Red Date.
 * @date 2020/5/9 13:00
 */
@Service
@Log4j2
public class PersonalPlanServiceImpl implements PersonalPlanService {

    @Resource
    private PlanMapper planMapper;
    @Resource
    private TaskMapper taskMapper;

    /**
     * 获取个人计划的内容
     * @param planId 计划的id
     * @return 个人计划的内容
     */
    @Override
    public PlanVo getPersonalPlan(Integer planId) {
        Plan plan = planMapper.getPlanByPlanId(planId);
        if(VerifyUtil.isNull(plan)){
            log.info("{}该计划不存在",planId);
            throw new ErrorException("该计划"+planId+"不存在");
        }
        List<Task> taskList = taskMapper.getTaskByPlanId(planId);
        PlanVo planVo = new PlanVo();
        BeanUtils.copyProperties(plan,planVo);
        planVo.setTaskList(taskList);
        log.info("获取计划:{}的内容成功",planId);
        return planVo;
    }

    /**
     * 获取用户所有的计划包括子任务的内容
     *
     * @param userId 用户的id
     * @return
     */
    @Override
    public List<PlanVo> getPersonalPlanList(Integer userId) {
        List<PlanVo> planVoList = new ArrayList<>();
        List<Integer> planIdList = planMapper.getPlanIdList(userId);
        if(VerifyUtil.isEmpty(planIdList)){
            log.info("该用户({})没有发布过计划",userId);
            return null;
        }
        for (Integer planId:planIdList){
            PlanVo planVo = getPersonalPlan(planId);
            planVoList.add(planVo);
        }
        return planVoList;
    }

    /**
     * 添加个人计划
     *
     * @param personalPlanVo 个人计划内容
     * @return 插入的内容
     */
    @Transactional
    @Override
    public PlanVo insertPersonalPlan(PlanVo personalPlanVo) {
        Plan plan = new Plan();
        plan.setType(0);
        BeanUtils.copyProperties(personalPlanVo,plan);
        if(planMapper.insertPlan(plan)!=1){
            log.info("个人计划插入数据库失败,可能部分字段为空");
            throw new ErrorException("系统出现异常，请稍后重试");
        }
        List<Task> taskList = personalPlanVo.getTaskList();
        List<Task> result = new ArrayList<>();
        for (Task task : taskList){
            task.setPlanId(plan.getPlanId());
            task.setUserId(personalPlanVo.getUserId());
            if(taskMapper.insertTask(task)!=1){
                log.info("子任务插入数据库失败");
                throw new ErrorException("系统出现异常，请稍后重试");
            }
            result.add(task);
        }
        personalPlanVo.setPlanId(plan.getPlanId());
        personalPlanVo.setTaskList(result);
        return personalPlanVo;
    }

    /**
     * 修改个人计划的内容
     * @param personalPlanVo 个人计划的内容
     * @return 修改后的内容
     */
    @Transactional
    @Override
    public PlanVo updatePersonalPlan(PlanVo personalPlanVo) {
        if(planMapper.updatePlanByPlanId(personalPlanVo.getPlanName(),personalPlanVo.getPlanId()) != 1){
            log.info("计划{}修改失败",personalPlanVo.getPlanId());
            throw new ErrorException("系统出现异常，请稍后重试");
        }
        for (Task task:personalPlanVo.getTaskList()){
            if(VerifyUtil.isEmpty(task.getTaskId())){
                task.setUserId(personalPlanVo.getUserId());
                if(taskMapper.insertTask(task)!=1){
                    log.info("修改计划时添加子计划{}失败",task.getTaskName());
                    throw new ErrorException("系统出现异常，请稍后重试");
                }
            }else{
                if(taskMapper.updateTask(task) != 1){
                    log.info("子计划{}修改失败",task.getTaskId());
                    throw new ErrorException("系统出现异常，请稍后重试");
                }
            }
        }
        return personalPlanVo;
    }

    /**
     * 删除个人计划
     * @param planId 个人计划的id
     * @return 是否删除成功，成功返回true
     */
    @Override
    public boolean deletePersonalPlan(Integer planId) {
        if(planMapper.deletePlanByPlanId(planId)!=1){
            log.info("该个人计划{}不存在",planId);
            throw new ErrorException("该个人计划:"+planId+"不存在");
        }
        log.info("个人计划{}删除成功",planId);
        return true;
    }

    /**
     * 删除子任务
     * @param taskId 子任务的id
     * @return 是否删除成功，成功返回true
     */
    @Override
    public boolean deleteTask(Integer taskId) {
        if(taskMapper.deleteTaskByTaskId(taskId)!=1){
            log.info("该子任务不存在{}",taskId);
            throw new ErrorException("该子任务:"+taskId+"不存在");
        }
        log.info("子任务{}删除成功",taskId);
        return true;
    }
}
