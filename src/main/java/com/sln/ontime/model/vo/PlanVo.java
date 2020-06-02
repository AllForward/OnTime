package com.sln.ontime.model.vo;

import com.sln.ontime.model.po.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Red Date.
 * @date 2020/5/9 12:51
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlanVo {

    private Integer userId;

    private String planName;

    private Integer planId;

    //若为团队计划则为groupId
    private Integer type;

    private List<Task> taskList;

}
