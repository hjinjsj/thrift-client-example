package com.didi.example;

import com.didi.example.dto.UserDto;
import com.didi.example.lib.ThriftClient;
import com.didi.example.service.UserService;
import com.didi.example.service.impl.UserServiceImpl;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.concurrent.CountDownLatch;

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
//        thriftClient.connServer();
//        thriftClient.nbConnServer();
//        thriftClient.nbCompactConnServer();
        thriftClient.nbJsonConnServer();

        userService.findById((long)1);
        userService.findByIds(Lists.newArrayList((long)1));
        userService.create(new UserDto("test"));
        userService.update(new UserDto().setUserId((long)1).setName("hjin").setMobile("86-15201330138"));
        userService.remove((long)1);

//        testAsync9();
    }

    public void testAsync9() throws TException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        thriftClient.asyncConnServer();
        thriftClient.getUserServiceAsyncClient().findById((long)1, new AsyncMethodCallback<UserService.AsyncClient.findById_call>(){

            @Override
            public void onComplete(UserService.AsyncClient.findById_call findById_call) {
                try {
                    UserDto userDto = findById_call.getResult();
                    log.info(userDto.toString());
                } catch (TException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }

            @Override
            public void onError(Exception e) {
                log.error("async error: {}", e.getStackTrace());
                e.printStackTrace();
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * libthrift-0.13.0
     * 因为异步调用依赖nio方法在jdk9才有，所以jdk8报错，需要把libthrift代码调整到0.9.3版本
     * 一直报Exception in thread "TAsyncClientManager#SelectorThread 26" java.lang.NoSuchMethodError: java.nio.ByteBuffer.rewind()Ljava/nio/ByteBuffer;
     */
    public void testAsync13() throws TException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        thriftClient.asyncConnServer();
        thriftClient.getUserServiceAsyncClient().findById((long)1, new AsyncMethodCallback<UserDto>(){
            @Override
            public void onComplete(UserDto response) {
                log.info(response.toString());
                countDownLatch.countDown();
            }

            @Override
            public void onError(Exception exception) {
                log.error("async error: {}", exception.getStackTrace());
                exception.printStackTrace();
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
