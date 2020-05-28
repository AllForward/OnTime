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
        long fst;
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
            System.out.println("类：TaskListService->方法：updateTaskList()中->日期格式转换出错。");
        }
        return format.format(startTime);
    }

    /**
     * 返回排序后的列表
     * @return newTaskList
     */
    List<Task> updateTaskList(List<Task> newTaskList){
        int mark = 0;
        int markj = 0;
        int size = newTaskList.size();
        String startTime;
        String endTime;
        String firstStartTime = getFirstStartTime(newTaskList);
        
        /*为排序好的列表中task对象startTime重新赋值*/
        /*上午的任务从任务列表最早开始时间开始，不超过12：30结束*/
        for( int i = 0; i < size; i++ ){
            if( i == 0 )
                startTime = firstStartTime;
            else
                //每个任务之间休息10分钟
                startTime = startTimeUtil(newTaskList.get(i - 1).getEndTime(),10);

            endTime = startTimeUtil(startTime, newTaskList.get(i).getLasting());
            long endTimel = Long.parseLong(endTime.substring(11).replaceAll("[^0-9]",""));
            if(endTimel > 123000){
                mark = i;
                markj = i;
                break;
            }else{
                newTaskList.get(i).setStartTime(startTime);
                newTaskList.get(i).setEndTime(endTime);
            }
        }
        /*下午的任务从14：00开始*/
        for( ; mark < size; mark++){
            if(mark == markj)
                startTime = firstStartTime.substring(0,11) + "14:00:00";
            else
                startTime = startTimeUtil(newTaskList.get(mark - 1).getEndTime(), 10);

            endTime = startTimeUtil(startTime, newTaskList.get(mark).getLasting());
            newTaskList.get(mark).setStartTime(startTime);
            newTaskList.get(mark).setEndTime(endTime);
        }
        return newTaskList;
    }
}
