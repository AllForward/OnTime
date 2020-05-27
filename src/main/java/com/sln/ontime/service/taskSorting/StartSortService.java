package com.sln.ontime.service.taskSorting;

import com.sln.ontime.model.po.Task;

import java.util.ArrayList;
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
        List<Task> newTaskList;
        if(type != 4){
            TaskListService taskListService = new TaskListService();
            //设置排序后的任务列表中各任务要传给前端的startTime
            newTaskList = taskListService.updateTaskList(taskList);
        }else {
            newTaskList = new ArrayList<>(taskList);
        }
        //返回排序后的任务列表
        return newTaskList;
    }
    /**
     * 获取到待排序任务列表并根据所选算法类型排序
     */
    private void sortTaskList(List<Task> taskList, int type) {
        switch(type) {
            case 1:
                Collections.sort(taskList, new TaskSortService.ShortFirst());
                break;
            case 2:
                Collections.sort(taskList, new TaskSortService.LongFirst());
                break;
            case 3:
                Collections.sort(taskList, new TaskSortService.Priority());
                break;
            case 4:
                Collections.sort(taskList, new TaskSortService.UserDefine());
                break;
        }
    }

}
