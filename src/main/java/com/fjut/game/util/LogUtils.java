package com.fjut.game.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author axiang [2019/10/9]
 */
public class LogUtils {
    /**
     * 获取业务日志logger工厂
     *
     * @return
     */
    public static Logger getBussinessLogger() {
        return LoggerFactory.getLogger(LogEnum.BUSINESS.getCategory());
    }

    /**
     * 获取平台日志logger
     *
     * @return
     */
    public static Logger getPlatformLogger() {
        return LoggerFactory.getLogger(LogEnum.PLATFORM.getCategory());
    }

    /**
     * 获取数据库日志logger
     *
     * @return
     */
    public static Logger getDBLogger() {
        return LoggerFactory.getLogger(LogEnum.DB.getCategory());
    }

    /**
     * 获取异常日志logger
     *
     * @return
     */
    public static Logger getExceptionLogger() {
        return LoggerFactory.getLogger(LogEnum.EXCEPTION.getCategory());
    }

}
