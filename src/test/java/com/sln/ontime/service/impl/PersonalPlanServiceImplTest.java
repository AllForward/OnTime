package com.sln.ontime.service.impl;

import com.sln.ontime.model.po.Task;
import com.sln.ontime.model.vo.PlanVo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/** 
* PersonalPlanServiceImpl Tester. 
* 
* @author Red Date.
* @since 05/10/2020
 @version 1.0 
*/ 
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonalPlanServiceImplTest { 

    @Autowired
    private PersonalPlanServiceImpl personalPlanService;

    @Before
    public void before() throws Exception { 
    } 
    
    @After
    public void after() throws Exception { 
    } 

    /** 
    * Method: getPersonalPlan(Integer planId) 
    */ 
    @Test
    public void testGetPersonalPlan() throws Exception {
        PlanVo planVo =personalPlanService.getPersonalPlan(10000);
        Assert.assertNull(planVo);
        PlanVo planVo1 = personalPlanService.getPersonalPlan(2);
        System.out.println(planVo1);
    } 

    /** 
    * Method: insertPersonalPlan(PlanVo personalPlanVo) 
    */ 
    @Test
    public void testInsertPersonalPlan() throws Exception {
        PlanVo planVo = new PlanVo();
        planVo.setUserId(1);
        planVo.setPlanName("测试个人计划");
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(null,null,1, "测试子任务1",30,
                "2020-05-10 20:21:00","2020-05-11 20:21:00",3,0);
        Task task2 = new Task(null,null, 1, "测试子任务2",40,
                "2020-05-10 16:21:00","2020-05-11 20:21:00",3,0);
        tasks.add(task1);
        tasks.add(task2);
        planVo.setTaskList(tasks);
        PlanVo result = personalPlanService.insertPersonalPlan(planVo);
        System.out.println(result);
    } 

    /** 
    * Method: updatePersonalPlan(PlanVo personalPlanVo) 
    */ 
    @Test
    public void testUpdatePersonalPlan() throws Exception {
        PlanVo planVo1 = personalPlanService.getPersonalPlan(2);
        planVo1.setPlanName("测试修改计划名字");
        PlanVo planVo = personalPlanService.updatePersonalPlan(planVo1);
        System.out.println(planVo);
    } 

    /** 
    * Method: deletePersonalPlan(Integer planId) 
    */ 
    @Test
    public void testDeletePersonalPlan() throws Exception {
        boolean flag = personalPlanService.deletePersonalPlan(3);
        System.out.println(flag);
    } 

    /** 
    * Method: deleteTask(Integer taskId) 
    */ 
    @Test
    public void testDeleteTask() throws Exception { 
        boolean flag = personalPlanService.deleteTask(1);
        System.out.println(flag);
    } 

    
} 
