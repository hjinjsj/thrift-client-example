package com.didi.example.lib;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @author huangjin
 */
public class Err {
    public static final int ERROR_PARAM = 400_000_000;
    public static final int ERROR_NOT_FOUND_CODE = 400_000_001;
    public static final int ERROR_NOT_FOUND_UID = 500_000_000;

    public static final String ERROR_PARAM_MSG = "参数异常";
    public static final String ERROR_NOT_FOUND_CODE_MSG = "未找到错误码";
    public static final String ERROR_NOT_FOUND_MSG = "未找到此用户";

    public static final Map<Integer, String> ERROR_MAP = ImmutableMap.<Integer, String>builder()
        .put(ERROR_PARAM, ERROR_PARAM_MSG)
        .put(ERROR_NOT_FOUND_CODE, ERROR_NOT_FOUND_CODE_MSG)
        .put(ERROR_NOT_FOUND_UID, ERROR_NOT_FOUND_MSG)
        .build();

    public static final String getErrorMsg(int code) throws MyException {
        String msg = ERROR_MAP.get(code);
        if (msg == null) {
            throw new MyException(ERROR_NOT_FOUND_CODE, getErrorMsg(ERROR_NOT_FOUND_CODE));
        }
        return msg;
    }
}
