package com.sln.ontime.service.impl;

import com.sln.ontime.dao.TaskMapper;
import com.sln.ontime.exception.ErrorException;
import com.sln.ontime.model.po.Task;
import com.sln.ontime.model.po.UserPo;
import com.sln.ontime.model.vo.SortVo;
import com.sln.ontime.service.TaskService;
import com.sln.ontime.service.taskSorting.StartSortService;
import com.sln.ontime.util.TimeUtil;
import com.sln.ontime.util.VerifyUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description
 * @author guopei
 * @date 2020-05-16 00:11
 */
@Service
@Log4j2
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;

    /**
     * 更新子任务状态
     * @param task
     * @return
     */
    @Override
    public Task updateTaskStatus(Task task) {

        if (VerifyUtil.isNull(task) || VerifyUtil.isEmpty(task.getTaskId()) || VerifyUtil.isEmpty(task.getStatus())) {
            log.info("前端传过来的部分数据为空");
            throw new ErrorException("请选择要修改的子任务");
        }
        if (task.getStatus() != 1 && task.getStatus() != 0) {
            log.info("前端传过来的数据格式错误");
            throw new ErrorException("请正确填写子任务状态");
        }
        if (taskMapper.updateTaskStatus(task) != 1) {
            log.info("更新子任务{}失败", task.getTaskId());
            throw new ErrorException("系统异常，请稍后重试");
        }
        return task;
    }

    @Override
    public List<Task> getSortTasks(SortVo sortVo, UserPo userPo) {
        if (VerifyUtil.isNull(sortVo) || VerifyUtil.isEmpty(sortVo.getAlgorithm()) || VerifyUtil.isEmpty(sortVo.getDate())) {
            log.info("前端传过来的部分数据为空");
            throw new ErrorException("请选择要获取的子任务时间");
        }
        //对日期格式进行校验
        if (!sortVo.getDate().matches("^((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")) {
            log.info("日期格式错误");
            throw new ErrorException("日期错误");
        }
        sortVo.setStartDate(sortVo.getDate() + " 00:00:00");
        sortVo.setEndDate(TimeUtil.nextDay(sortVo.getStartDate()));
        sortVo.setUserId(userPo.getUserId());
        List<Task> taskList = taskMapper.getTasksByUserIdAndTime(sortVo);
        if (VerifyUtil.isEmpty(taskList)) {
            log.info("用户暂无子任务");
            return null;
        }
        StartSortService startSortService = new StartSortService();
        return startSortService.startSort(taskList, sortVo.getAlgorithm());
    }

    @Override
    public boolean deleteTask(Integer taskId, UserPo userPo) {
        if (VerifyUtil.isEmpty(taskId)) {
            log.info("前端的taskId参数为空");
            throw new ErrorException("请选择要删除的子任务");
        }
        Task task = taskMapper.getTaskByTaskId(taskId);
        if (VerifyUtil.isNull(task)) {
            log.info("id为{}的子任务不存在", taskId);
            throw new ErrorException("该子计划不存在");
        }
        if (task.getUserId().equals(userPo.getUserId())) {
            //说明该计划是用户创建的
            taskMapper.deleteTaskByTaskId(taskId);
            return true;
        }
        else {
            log.info("id为{}的子任务不是用户{}所创建的", taskId, userPo.getUserId());
            throw new ErrorException("该子任务不是您创建的，您无权限删除");
        }
    }
}
