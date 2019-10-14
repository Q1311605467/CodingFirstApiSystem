CREATE DATABASE coding_first;

DROP TABLE IF EXISTS t_user_base_info;
CREATE TABLE t_user_base_info
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    username        VARCHAR(16) UNIQUE COMMENT '16个英文字母的登录名',
    nick            VARCHAR(8)  NOT NULL COMMENT '八个汉字或者八个字符',
    gender          SMALLINT COMMENT '性别，0为保密，1为男，2为女',
    email           VARCHAR(50) NOT NULL COMMENT '邮箱',
    phone           VARCHAR(20) NOT NULL COMMENT '电话',
    motto           VARCHAR(50) COMMENT '个性签名',
    register_time   DATETIME    NOT NULL COMMENT '注册时间',
    rating          INT         NOT NULL DEFAULT 0 COMMENT '评分',
    ranking         INT                  DEFAULT 0 COMMENT '排行',
    ac_num          INT                  DEFAULT 0 COMMENT 'AC题数',
    school          VARCHAR(20) COMMENT '学校',
    faculty         VARCHAR(20) COMMENT '学院',
    major           VARCHAR(20) COMMENT '专业',
    cla             VARCHAR(20) COMMENT '班级',
    student_id      VARCHAR(20)          DEFAULT NULL COMMENT '学号',
    graduation_time DATETIME             DEFAULT NULL COMMENT '毕业时间'
);

DROP TABLE IF EXISTS `t_user_auth`;
CREATE TABLE `t_user_auth`
(
    id                       INT PRIMARY KEY AUTO_INCREMENT,
    username                 varchar(30) NOT NULL UNIQUE,
    salt                     char(32)    NOT NULL COMMENT '加密盐值',
    password                 char(40)    NOT NULL,
    attempt_login_fail_count smallint(6) DEFAULT '0' COMMENT '尝试登录失败次数',
    locked                   smallint(6) DEFAULT '0' COMMENT '0为未锁定，1为锁定',
    unlock_time              datetime    DEFAULT NULL COMMENT '账号解锁时间',
    last_login_time          datetime    DEFAULT NULL,
    FOREIGN KEY (username) REFERENCES t_user_base_info (username)
);

DROP TABLE IF EXISTS `t_user_custom_info`;
CREATE TABLE `t_user_custom_info`
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    username   VARCHAR(30) NOT NULL UNIQUE,
    avatar_url VARCHAR(255) COMMENT '用户头像url地址',
    adjective_title VARCHAR(10) COMMENT '形容词头衔',
    article_title VARCHAR(10) COMMENT '名词头衔',
    FOREIGN KEY (username) REFERENCES t_user_base_info (username)
);


