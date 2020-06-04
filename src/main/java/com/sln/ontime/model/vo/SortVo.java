package com.sln.ontime.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description
 * @author guopei
 * @date 2020-05-15 23:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortVo {

    //算法类型
    private Integer algorithm;

    //日期
    private String date;

    private Integer userId;

    private String startDate;

    private String endDate;

}
