package com.fjut.cf.pojo.po;

import java.util.Date;

/**
 * 评测视图
 *
 * @author axiang [2019/10/31]
 */
public class ViewJudgeStatusPO {
    private Integer id;
    private String nick;
    private String username;
    private Integer problemId;
    private Integer contestId;
    private Integer language;
    private Date submitTime;
    private Integer result;
    private Integer score;
    private String timeUsed;
    private String memoryUsed;
    private String code;
    private Integer codeLength;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(String timeUsed) {
        this.timeUsed = timeUsed;
    }

    public String getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(String memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(Integer codeLength) {
        this.codeLength = codeLength;
    }

    @Override
    public String toString() {
        return "ViewJudgeStatusPO{" +
                "id=" + id +
                ", nick='" + nick + '\'' +
                ", username='" + username + '\'' +
                ", problemId=" + problemId +
                ", contestId=" + contestId +
                ", language=" + language +
                ", submitTime=" + submitTime +
                ", result=" + result +
                ", score=" + score +
                ", timeUsed='" + timeUsed + '\'' +
                ", memoryUsed='" + memoryUsed + '\'' +
                ", code='" + code + '\'' +
                ", codeLength=" + codeLength +
                '}';
    }
}
