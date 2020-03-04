package com.didi.example;

import com.didi.example.dto.UserDto;
import com.didi.example.lib.ThriftClient;
import com.didi.example.service.impl.UserServiceImpl;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author huangjin
 */
@SpringBootApplication
@Slf4j
public class ExampleClientApplication implements ApplicationRunner {
    @Autowired
    private ThriftClient thriftClient;

    @Autowired
    private UserServiceImpl userService;

    public static void main(String[] args) {
        new SpringApplicationBuilder(ExampleClientApplication.class)
            .web(false)
            .bannerMode(Banner.Mode.OFF)
            .run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("start thrift client example app.");
        thriftClient.connServer();
        userService.findById((long)1);
        userService.findByIds(Lists.newArrayList((long)1));
        userService.create(new UserDto("teset"));
        userService.update(new UserDto().setId((long)1).setName("hjin").setMobile("86-15201330138"));
        userService.remove((long)1);
    }

}
