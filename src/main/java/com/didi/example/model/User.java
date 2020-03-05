package com.didi.example.model;


import com.didi.example.dto.UserDto;
import com.didi.example.lib.DateUtil;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import java.util.Date;

/**
 * @author huangjin
 */
@Builder
@Data
@ToString
public class User {
    long userId;
    String name;
    String mobile;
    Date createTime;
    Date updateTime;
    Boolean isDeleted;

    public UserDto toDto() {
        return new UserDto()
            .setUserId(userId)
            .setName(name)
            .setMobile(mobile)
            .setCreateTime(DateUtil.dateToMillisecond(createTime))
            .setUpdateTime(DateUtil.dateToMillisecond(updateTime))
            .setIsDeleted(isDeleted);
    }
}
