package com.fjut.cf.pojo;

/**
 * @author axiang [2019/10/14]
 */
public class UserInfoVO {
    private UserCustomInfoPO customInfo;
    private UserBaseInfoPO baseInfo;

    public UserCustomInfoPO getUserCustomInfoPO() {
        return customInfo;
    }

    public void setUserCustomInfoPO(UserCustomInfoPO userCustomInfoPO) {
        this.customInfo = userCustomInfoPO;
    }

    public UserBaseInfoPO getUserBaseInfoPO() {
        return baseInfo;
    }

    public void setUserBaseInfoPO(UserBaseInfoPO userBaseInfoPO) {
        this.baseInfo = userBaseInfoPO;
    }

    @Override
    public String toString() {
        return baseInfo.toString() + customInfo.toString();
    }
}
