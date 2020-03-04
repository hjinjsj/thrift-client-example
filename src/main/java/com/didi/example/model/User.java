package com.didi.example.model;


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
}
