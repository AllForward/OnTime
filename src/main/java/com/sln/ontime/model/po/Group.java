package com.sln.ontime.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description
 * @author guopei
 * @date 2020-05-06 14:57
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Group {

    private Integer groupId;

    private String groupName;

    private Integer creatorId;

}
