package com.sln.ontime.service.taskSorting;

import com.sln.ontime.model.po.Task;

import java.util.Collections;
import java.util.List;

/**
 * @Class StartSortService
 * @Description 整合了排序流程的业务类
 * @Author Naren
 * @Date 2020/5/11 22:41
 * @Version 1.0
 */
public class StartSortService {

    /**
     * 对任务列表开始执行排序的入口
     */
    public List<Task> startSort(List<Task> taskList, int type) {
        //获取到待排序任务列表及算法类型，并排序
        sortTaskList(taskList, type);
        TaskListService taskListService = new TaskListService();
        //设置排序后的任务列表中各任务要传给前端的startTime
        List<Task> newTaskList = taskListService.generateTaskList(taskList);
        //打印排序后的任务列表
        return newTaskList;
    }
    /**
     * 获取到待排序任务列表并根据所选算法类型排序
     */
    private void sortTaskList(List<Task> task_list, int type) {
        if(type == 1)
            Collections.sort(task_list, new TaskSortService.ShortFirst());
        else if(type == 2)
            Collections.sort(task_list, new TaskSortService.LongFirst());
        else
            Collections.sort(task_list, new TaskSortService.Priority());
    }

}
