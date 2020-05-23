package com.sln.ontime.controller;

import com.sln.ontime.model.dto.ResultBean;
import com.sln.ontime.model.po.Task;
import com.sln.ontime.model.po.UserPo;
import com.sln.ontime.model.vo.SortVo;
import com.sln.ontime.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description
 * @author guopei
 * @date 2020-05-15 23:38
 */
@RestController
@RequestMapping("/schedule")
@Log4j2
public class ScheduleController {

    @Resource
    private TaskService taskService;

    @PostMapping("/sortTask")
    public ResultBean<?> sortTask(@RequestBody SortVo sortVo) {
        log.info("执行子任务排序方法");
        Subject subject = SecurityUtils.getSubject();
        UserPo userPo = (UserPo) subject.getPrincipal();
        return new ResultBean<>(taskService.getSortTasks(sortVo, userPo));
    }

    @PostMapping("/updateTaskStatus")
    public ResultBean<?> updateTaskStatus(@RequestBody Task task) {
        log.info("执行修改子任务状态方法");
        return new ResultBean<>(taskService.updateTaskStatus(task));
    }


}
