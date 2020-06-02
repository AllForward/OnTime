package com.sln.ontime.controller;

import com.alibaba.fastjson.JSONObject;
import com.sln.ontime.model.dto.ResultBean;
import com.sln.ontime.model.po.UserPo;
import com.sln.ontime.model.vo.PlanVo;
import com.sln.ontime.service.impl.PersonalPlanServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Red Date.
 * @date 2020/5/10 23:47
 */
@RestController
@RequestMapping("/personal_plan/")
@Log4j2
public class PersonalPlanController {

    @Autowired
    private PersonalPlanServiceImpl personalPlanService;

    /**
     * 获取用户的个人计划
     * @return 个人计划
     */
    @GetMapping("get")
    public ResultBean<?> getPersonalPlan(){
        log.info("开始获取用户的个人计划");
        Subject subject = SecurityUtils.getSubject();
        UserPo userPo = (UserPo) subject.getPrincipal();
        List<PlanVo> planVoList = personalPlanService.getPersonalPlanList(userPo.getUserId());
        return new ResultBean<>(planVoList);
    }

    /**
     * 插入个人计划信息
     * @param planContent 个人计划信息的json
     * @return 插入的信息
     */
    @PostMapping("set")
    public ResultBean<?> insertPersonalPlan(@RequestBody String planContent){
        log.info("开始插入个人计划信息");
        PlanVo planVo = JSONObject.parseObject(planContent, PlanVo.class);
        Subject subject = SecurityUtils.getSubject();
        UserPo userPo = (UserPo) subject.getPrincipal();
        planVo.setUserId(userPo.getUserId());
        PlanVo result = personalPlanService.insertPersonalPlan(planVo);
        log.info("插入个人计划信息成功");
        return new ResultBean<>(result);
    }

    /**
     * 修改个人计划的内容
     * @param planContent 修改的内容
     * @return 修改的结果
     */
    @PostMapping("update")
    public ResultBean<?> updatePersonalPlan(@RequestBody String planContent){
        log.info("开始修改个人计划信息");
        PlanVo planVo = JSONObject.parseObject(planContent, PlanVo.class);
        Subject subject = SecurityUtils.getSubject();
        UserPo userPo = (UserPo) subject.getPrincipal();
        planVo.setUserId(userPo.getUserId());
        PlanVo result = personalPlanService.updatePersonalPlan(planVo);
        return new ResultBean<>(result);
    }

    /**
     * 删除个人计划
     * @param planId 个人计划的id
     * @return 是否删除成功
     */
    @GetMapping("delete_plan")
    public ResultBean<?> deletePlan(@RequestParam(value = "planId")Integer planId){
        log.info("开始删除个人计划{}",planId);
        personalPlanService.deletePersonalPlan(planId);
        log.info("删除个人计划{}成功",planId);
        return new ResultBean<>();
    }

    /**
     * 删除子任务
     * @param taskId 子任务id
     * @return 是否删除成功
     */
    @GetMapping("delete_task")
    public ResultBean<?> deleteTask(@RequestParam(value = "taskId")Integer taskId){
        log.info("开始删除子任务{}",taskId);
        personalPlanService.deleteTask(taskId);
        log.info("删除子任务{}成功",taskId);
        return new ResultBean<>();
    }
}
