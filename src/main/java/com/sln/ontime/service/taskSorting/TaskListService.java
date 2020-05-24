package com.sln.ontime.service.taskSorting;

import com.sln.ontime.model.po.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @Class TaskListService
 * @Description 生成前端需要的task对象列表
 * @Author Naren
 * @Date 2020/5/15 10:07
 * @Version 1.0
 */
public class TaskListService {

    /**
     * 获取当前任务列表中子任务最早开始时间(String)
     * @return String
     */
    private static String getFirstStartTime(ArrayList<Task> newTask_list){
        String firstStartTime = newTask_list.get(0).getStartTime();
        long fst = Long.parseLong(firstStartTime.replaceAll("[^0-9]",""));
        for( int i = 0; i < newTask_list.size(); i++ ){
            if( fst > Long.valueOf(newTask_list.get(i).getStartTime().replaceAll("[^0-9]","")) ) {
                firstStartTime = newTask_list.get(i).getStartTime();
            }

        }
        return firstStartTime;
    }

    /**
     * 计算每个任务的起始时间
     * @return String
     */
    private static String startTimeUtil(String firstStartTime, int totalLasting){
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
    static ArrayList generateTaskList(ArrayList<Task> newTask_list){

        /*为排序好的列表中task对象startTime重新赋值*/
        String firstStartTime = getFirstStartTime(newTask_list);
        String startTime = "";
        int totalLasting = 0;
        for( int i = 0; i < newTask_list.size(); i++ ){
            for( int j = 0; j < i; j++ ){
                totalLasting += newTask_list.get(j).getLasting();
            }
            startTime = startTimeUtil(firstStartTime, totalLasting);
            newTask_list.get(i).setStartTime(startTime);
        }
        return newTask_list;
    }
}
