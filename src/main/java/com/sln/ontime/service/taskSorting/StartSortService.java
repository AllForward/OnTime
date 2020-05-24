package com.sln.ontime.service.taskSorting;

import com.sln.ontime.model.po.Task;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @Class StartSortService
 * @Description 整合了排序流程的业务类
 * @Author Naren
 * @Date 2020/5/11 22:41
 * @Version 1.0
 */
public class StartSortService {

    //TODO 暂时用以替代 GP 获取到的待排任务列表
    private static ArrayList<Task> task_list = new ArrayList<>();

    /**
     * 用于测试：初始化待排序任务列表
     */
    private static void task_list(){

        /*任务列表*/
        Task task1 = new Task();
        Task task2 = new Task();
        Task task3 = new Task();
        Task task4 = new Task();
        Task task5 = new Task();
        Task task6 = new Task();

        /*设置任务属性值*/
        task1.setTaskId(1);
        task2.setTaskId(2);
        task3.setTaskId(3);
        task4.setTaskId(4);
        task5.setTaskId(5);
        task6.setTaskId(6);
        task1.setLasting(50);
        task2.setLasting(120);
        task3.setLasting(80);
        task4.setLasting(20);
        task5.setLasting(30);
        task6.setLasting(40);
        task1.setStartTime("2020-05-11 09:00:00");
        task2.setStartTime("2020-05-12 08:00:00");
        task3.setStartTime("2020-05-11 10:00:00");
        task4.setStartTime("2020-05-11 17:00:00");
        task5.setStartTime("2020-05-11 16:00:00");
        task6.setStartTime("2020-05-11 08:00:00");
        task1.setEndTime("2020-05-12 18:00:00");
        task2.setEndTime("2020-05-12 12:00:00");
        task3.setEndTime("2020-05-12 13:00:00");
        task4.setEndTime("2020-05-13 17:00:00");
        task5.setEndTime("2020-05-14 16:00:00");
        task6.setEndTime("2020-05-11 18:00:00");
        task1.setPriority(3);
        task2.setPriority(2);
        task3.setPriority(2);
        task4.setPriority(1);
        task5.setPriority(3);
        task6.setPriority(4);

        /*添加任务列表*/
        task_list.add(task1);
        task_list.add(task2);
        task_list.add(task3);
        task_list.add(task4);
        task_list.add(task5);
        task_list.add(task6);
    }

    /**
     * 对任务列表开始执行排序的入口
     */
    public void startSort() {
        //获取到待排序任务列表及算法类型，并排序
        sortTaskList(task_list, 3);
        TaskListService taskListService = new TaskListService();
        //设置排序后的任务列表中各任务要传给前端的startTime
        ArrayList<Task> newTask_list = taskListService.generateTaskList(task_list);
        //打印排序后的任务列表
        print(newTask_list);
    }

    /**
     * 获取到待排序任务列表并根据所选算法类型排序
     */
    private void sortTaskList(ArrayList<Task> task_list, int type) {
        TaskSortService taskSortService = new TaskSortService();
        if(type == 1)
            Collections.sort(task_list, new TaskSortService.ShortFirst());
        else if(type == 2)
            Collections.sort(task_list, new TaskSortService.LongFirst());
        else
            Collections.sort(task_list, new TaskSortService.Priority());
    }

    /**
     * 用于测试排序结果：打印排序后的任务列表
     */
    private void print(ArrayList<Task> newTask_list){
        for(Task task: newTask_list){
            System.out.println(task);
        }
    }

}
