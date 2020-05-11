package com.sln.ontime.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description
 * @author guopei
 * @date 2020-05-06 15:00
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Task {

    private Integer taskId;

    private Integer planId;

    private String taskName;

    //任务耗时(以分钟为单位)
    private Integer lasting;

    private String startTime;

    private String endTime;

    //优先级(1~4,数字越大优先级越高)
    private Integer priority;

    //完成情况(0表示未完成,1表示已完成)
    private Integer status;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getLasting() {
        return lasting;
    }

    public void setLasting(Integer lasting) {
        this.lasting = lasting;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



}
