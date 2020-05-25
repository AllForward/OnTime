package com.sln.ontime.service;

import com.sln.ontime.model.po.Task;
import com.sln.ontime.model.po.UserPo;
import com.sln.ontime.model.vo.SortVo;

import java.util.List;


public interface TaskService {

    Task updateTaskStatus(Task task);

    List<Task> getSortTasks(SortVo sortVo, UserPo userPo);

    boolean deleteTask(Integer taskId, UserPo userPo);

}
