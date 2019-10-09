package com.fjut.game.util;

/**
 * @author axiang [2019/10/9]
 */
public enum LogEnum {
    /**
     * 业务日志
     */
    BUSINESS("business"),

    /**
     * 平台日志
     */
    PLATFORM("platform"),

    /**
     * 数据库日志
     */
    DB("db"),

    /**
     * 错误日志
     */
    EXCEPTION("exception");


    private String category;


    LogEnum(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
