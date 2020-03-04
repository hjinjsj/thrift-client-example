package com.didi.example.service.impl;

import com.didi.example.dto.UserDto;
import com.didi.example.lib.MyException;
import com.didi.example.lib.ThriftClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl {

    @Autowired
    private ThriftClient thriftClient;

    public void findById(long uid) {
        try {
            UserDto userDto = thriftClient.getUserServiceClient().findById(uid);
            log.info(userDto.toString());
        } catch (TException e) {
            log.error("Exception: {}", e.getStackTrace());
        }
    }

    public void findByIds(List<Long> uids){
        try {
            List<UserDto> userDtoList = thriftClient.getUserServiceClient().findByIds(uids);
            log.info(userDtoList.toString());
        } catch (TException e) {
            log.error("Exception: {}", e.getStackTrace());
        }
    }

    public void create(UserDto user) {
        try {
            long uid = thriftClient.getUserServiceClient().create(user);
            log.info("create uid: {}", uid);
        } catch (TException e) {
            log.error("Exception: {}", e.getStackTrace());
        }
    }

    public void update(UserDto user) {
        try {
            boolean res = thriftClient.getUserServiceClient().update(user);
            log.info("update result: {}", res);
        } catch (TException e) {
            log.error("Exception: {}", e.getStackTrace());
        }
    }

    public void remove(long uid) {
        try {
            boolean res = thriftClient.getUserServiceClient().remove(uid);
            log.info("remove result: {}", res);
        } catch (MyException e) {
            log.error("MyException: code-{} msg-{}", e.getCode(), e.getMsg());
        } catch (TException e) {
            log.error("Exception: {}", e.getStackTrace());
        }
    }
}
