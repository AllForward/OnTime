package com.sln.ontime.controller;

import com.sln.ontime.model.dto.ResultBean;
import com.sln.ontime.model.vo.SortVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description
 * @author guopei
 * @date 2020-05-15 23:38
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    


    public ResultBean<?> sortTask(@RequestBody SortVo sortVo) {
        return new ResultBean<>();
    }


}
