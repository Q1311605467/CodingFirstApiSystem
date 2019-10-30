package com.fjut.cf.pojo.vo;

/**
 * @author axiang [2019/10/30]
 */
public class UserAcbBorderVO {
    private String username;
    private String nick;
    private Integer acb;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getAcb() {
        return acb;
    }

    public void setAcb(Integer acb) {
        this.acb = acb;
    }

    @Override
    public String toString() {
        return "UserAcbBorderVO{" +
                "username='" + username + '\'' +
                ", nick='" + nick + '\'' +
                ", acb=" + acb +
                '}';
    }
}
