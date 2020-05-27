package com.sln.ontime.service.taskSorting;

import com.sln.ontime.model.po.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Class TaskListService
 * @Description 生成前端需要的task对象列表
 * @Author Naren
 * @Date 2020/5/15 10:07
 * @Version 1.0
 */
class TaskListService {

    /**
     * 获取当前任务列表中子任务最早开始时间(String)
     * @return firstStartTime
     */
    private String getFirstStartTime(List<Task> newTaskList){
        String firstStartTime = newTaskList.get(0).getStartTime();
        long fst = 0;
        for( int i = 0; i < newTaskList.size(); i++ ){
            fst = Long.valueOf( firstStartTime.replaceAll("[^0-9]","") );
            if( fst > Long.valueOf(newTaskList.get(i).getStartTime().replaceAll("[^0-9]","")) ) {
                firstStartTime = newTaskList.get(i).getStartTime();
            }
        }
        return firstStartTime;
    }

    /**
     * 计算每个任务的起始时间
     * @return startTime
     */
    private String startTimeUtil(String firstStartTime, int totalLasting){
        //24时格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = null;
        try {
            startTime = format.parse(firstStartTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(startTime);
            cal.add(Calendar.MINUTE, totalLasting);
            startTime = cal.getTime();
        } catch (ParseException e) {
            System.out.println("类：TaskListService->方法：generateTaskList()中->日期格式转换出错。");
        }
        return format.format(startTime);
    }

    /**
     * 返回排序后的列表
     * @return ArrayList
     */
    List<Task> generateTaskList(List<Task> newTaskList){

        /*为排序好的列表中task对象startTime重新赋值*/
        String firstStartTime = getFirstStartTime(newTaskList);
        String startTime = "";
        int totalLasting = 0;
        for( int i = 0; i < newTaskList.size(); i++ ){
            for( int j = 0; j < i; j++ ){
                totalLasting += newTaskList.get(j).getLasting();
            }
            startTime = startTimeUtil(firstStartTime, totalLasting);
            newTaskList.get(i).setStartTime(startTime);
            totalLasting = 0;
        }
        return newTaskList;
    }
}
