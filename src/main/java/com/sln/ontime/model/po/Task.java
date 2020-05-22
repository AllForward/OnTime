package com.sln.ontime.model.po;

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

    private Integer userId;

    private String taskName;

    //任务耗时(以分钟为单位)
    private Integer lasting;

    private String startTime;

    private String endTime;

    //优先级(1~4,数字越大优先级越高)
    private Integer priority;

    //完成情况(0表示未完成,1表示已完成)
    private Integer status;

}
