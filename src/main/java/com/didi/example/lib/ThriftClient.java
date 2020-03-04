package com.didi.example.lib;

import com.didi.example.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.*;
import org.apache.thrift.transport.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author huangjin
 */
@Component
@Data
@Slf4j
@ConfigurationProperties(prefix = "thrift")
public class ThriftClient {
    private int port;

    private UserService.Client userServiceClient;

    private UserService.AsyncClient userServiceAsyncClient;

    public void connServer(){
        log.info("conn thrift server at port: {}" , port);
        TSocket transport = null;
        try {
            transport = new TSocket("localhost", port);
            TBinaryProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
            userServiceClient = new UserService.Client(mp);
            transport.open();
        } catch(TException e) {
            transport.close();
            log.error("conn thrift server fail , err msg: {}", e.getStackTrace());
        }
    }

    public void nbConnServer() {
        log.info("conn thrift server at port: {}", port);
        TTransport transport = null;
        try {
            transport = new TFramedTransport(new TSocket("localhost", port));
            TBinaryProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
            userServiceClient = new UserService.Client(mp);
            transport.open();
        } catch(TException e) {
            transport.close();
            log.error("conn thrift server fail , err msg: {}", e.getStackTrace());
        }
    }

    public void nbCompactConnServer() {
        log.info("conn compact thrift server at port: {}", port);
        TTransport transport = null;
        try {
            transport = new TFramedTransport(new TSocket("localhost", port));
            TCompactProtocol protocol = new TCompactProtocol(transport);
            TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
            userServiceClient = new UserService.Client(mp);
            transport.open();
        } catch(TException e) {
            transport.close();
            log.error("conn thrift server fail , err msg: {}", e.getStackTrace());
        }
    }

    public void nbJsonConnServer() {
        log.info("conn json thrift server at port: {}", port);
        TTransport transport = null;
        try {
            transport = new TFramedTransport(new TSocket("localhost", port));
            TJSONProtocol protocol = new TJSONProtocol(transport);
            TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserService");
            userServiceClient = new UserService.Client(mp);
            transport.open();
        } catch(TException e) {
            transport.close();
            log.error("conn thrift server fail , err msg: {}", e.getStackTrace());
        }
    }

    public void  asyncConnServer() {
        log.info("async conn thrift server at port: {}", port);
        TNonblockingTransport transport = null;
        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();
            transport = new TNonblockingSocket(
                "localhost", port);
            TProtocolFactory protocol = new TBinaryProtocol.Factory();
            userServiceAsyncClient = new UserService.AsyncClient(protocol,
                clientManager, transport);
        } catch (IOException e) {
            log.error("conn thrift server fail , err msg: {}", e.getStackTrace());
        }
    }
}
