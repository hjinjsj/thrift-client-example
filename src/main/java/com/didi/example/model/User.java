package com.didi.example.model;


import com.didi.example.dto.UserDto;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author huangjin
 */
@Builder
@Data
@ToString
public class User {
    long id;
    String name;
    String mobile;
    boolean isDeleted;

    public UserDto toDto() {
        return new UserDto()
            .setId(id)
            .setName(name)
            .setMobile(mobile)
            .setIsDeleted(isDeleted);
    }
}
