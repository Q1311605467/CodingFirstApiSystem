/*
 Navicat Premium Data Transfer

 Source Server         : LocalMySQL
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : cf_test

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 15/12/2019 12:23:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_acb_record
-- ----------------------------
DROP TABLE IF EXISTS `t_acb_record`;
CREATE TABLE `t_acb_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `current_acb` int(11) NOT NULL COMMENT '记录时的ACB数量',
  `reason` int(11) NOT NULL DEFAULT 0 COMMENT 'ACB变更原因：0：默认；',
  `time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '记录时间',
  `mark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原因标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_border_honor_rank
-- ----------------------------
DROP TABLE IF EXISTS `t_border_honor_rank`;
CREATE TABLE `t_border_honor_rank`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username_one` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `real_name_one` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `username_two` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `real_name_two` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `username_three` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `real_name_three` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `reward_date` date NULL DEFAULT NULL,
  `register_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '登记该条记录的时间',
  `contest_level` int(11) NULL DEFAULT NULL COMMENT '比赛等级',
  `award_level` int(11) NULL DEFAULT NULL COMMENT '获奖等级',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '获奖描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 79 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_bug_report
-- ----------------------------
DROP TABLE IF EXISTS `t_bug_report`;
CREATE TABLE `t_bug_report`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` int(11) NOT NULL COMMENT '0：其他 1：系统漏洞 2：功能异常 3：逻辑错误 4：界面问题 ',
  `text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `report_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '反馈时间',
  `is_fixed` smallint(6) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE,
  INDEX `type`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_challenge_block
-- ----------------------------
DROP TABLE IF EXISTS `t_challenge_block`;
CREATE TABLE `t_challenge_block`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模块名称',
  `block_type` int(11) NOT NULL COMMENT '模块类型',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模块描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 613 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_challenge_block_condition
-- ----------------------------
DROP TABLE IF EXISTS `t_challenge_block_condition`;
CREATE TABLE `t_challenge_block_condition`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `block_id` int(11) NOT NULL COMMENT '挑战模式模块ID',
  `precondition_block_id` int(11) NOT NULL COMMENT '前置挑战模式模块ID',
  `precondition_unlock_score` int(11) NOT NULL COMMENT '前置模块需要解锁的积分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 159 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_challenge_block_problem
-- ----------------------------
DROP TABLE IF EXISTS `t_challenge_block_problem`;
CREATE TABLE `t_challenge_block_problem`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `block_id` int(11) NOT NULL COMMENT '挑战模式模块ID',
  `problem_order` int(11) NOT NULL COMMENT '模块内的题目顺序',
  `problem_id` int(11) NOT NULL COMMENT '题目ID',
  `score` int(11) NOT NULL DEFAULT 0 COMMENT '题目分值',
  `reward_acb` int(11) NOT NULL DEFAULT 0 COMMENT '解锁后奖励ACB',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 553 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_challenge_user_open_block
-- ----------------------------
DROP TABLE IF EXISTS `t_challenge_user_open_block`;
CREATE TABLE `t_challenge_user_open_block`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `block_id` int(11) NOT NULL,
  `unlock_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '模块解锁时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16402 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_contest_info
-- ----------------------------
DROP TABLE IF EXISTS `t_contest_info`;
CREATE TABLE `t_contest_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contest_id` int(11) NULL DEFAULT NULL COMMENT '比赛编号',
  `contest_kind` int(11) NULL DEFAULT NULL COMMENT '比赛类型： 0：练习；1：积分；2：趣味；3：正式；4：隐藏；5：DIY',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '比赛标题',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '比赛介绍',
  `create_user` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '比赛开设人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '比赛创建时间',
  `begin_time` datetime(0) NOT NULL COMMENT '比赛开始时间',
  `end_time` datetime(0) NOT NULL COMMENT '比赛结束时间',
  `permission_type` int(11) NOT NULL DEFAULT 0 COMMENT '权限类型： 0：公开；1：密码；2：私有；3：注册；4：正式；5：组队',
  `password` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '需要密码时的密码',
  `register_begin_time` datetime(0) NULL DEFAULT NULL COMMENT '需要注册参赛信息时的注册开始时间',
  `register_end_time` datetime(0) NULL DEFAULT NULL COMMENT '需要注册参赛信息时的注册结束时间',
  `computer_rating` tinyint(1) NULL DEFAULT 0 COMMENT '是否计算积分',
  `rank_type` int(11) NOT NULL COMMENT '计分方式',
  `problem_put_tag` tinyint(1) NULL DEFAULT 1 COMMENT '题目是否可以贴标签： 0：不可以，1：可以，定义同下',
  `status_read_out` tinyint(1) NULL DEFAULT 1 COMMENT '是否可以查看评测列表',
  `show_register_list` tinyint(1) NULL DEFAULT 1 COMMENT '展示注册名单',
  `show_border_list` tinyint(1) NULL DEFAULT 1 COMMENT '展示比赛版单',
  `show_other_status` tinyint(1) NULL DEFAULT 1 COMMENT '展示别人的代码',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `contest_id`(`contest_id`) USING BTREE,
  INDEX `create_user`(`create_user`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 401 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_contest_problem
-- ----------------------------
DROP TABLE IF EXISTS `t_contest_problem`;
CREATE TABLE `t_contest_problem`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contest_id` int(11) NULL DEFAULT NULL COMMENT '比赛编号',
  `problem_id` int(11) NULL DEFAULT NULL COMMENT '题目的题号',
  `problem_order` int(11) NULL DEFAULT NULL COMMENT '题目在比赛内的顺序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_contest_register_user
-- ----------------------------
DROP TABLE IF EXISTS `t_contest_register_user`;
CREATE TABLE `t_contest_register_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contest_id` int(11) NOT NULL,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `register_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '注册时间',
  `review_status` smallint(6) NULL DEFAULT 0 COMMENT '审核是否通过: 0:还未审核；1：审核通过；2：审核失败',
  `review_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核反馈信息',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `contest_id`(`contest_id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_discuss_post
-- ----------------------------
DROP TABLE IF EXISTS `t_discuss_post`;
CREATE TABLE `t_discuss_post`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '帖子建立时间',
  `author` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `priority` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_discuss_reply_post
-- ----------------------------
DROP TABLE IF EXISTS `t_discuss_reply_post`;
CREATE TABLE `t_discuss_reply_post`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `discuss_id` int(11) NOT NULL COMMENT '回帖帖子编号',
  `reply_order` int(11) NOT NULL COMMENT '回帖楼层顺序',
  `time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `author` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '帖子内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_judge_result
-- ----------------------------
DROP TABLE IF EXISTS `t_judge_result`;
CREATE TABLE `t_judge_result`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `judge_id` int(11) NULL DEFAULT NULL COMMENT '评测ID',
  `info` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评测返回的结果',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `judge_id`(`judge_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 168789 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_judge_status
-- ----------------------------
DROP TABLE IF EXISTS `t_judge_status`;
CREATE TABLE `t_judge_status`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `problem_id` int(11) NOT NULL,
  `contest_id` int(11) NOT NULL DEFAULT -1,
  `language` int(11) NOT NULL COMMENT '使用语言',
  `submit_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '提交时间',
  `result` int(11) NOT NULL COMMENT '评测结果',
  `score` int(11) NULL DEFAULT NULL COMMENT '如果是spj类型的题目，这里显示得分',
  `time_used` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评测耗时',
  `memory_used` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内存消耗',
  `code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评测代码',
  `code_length` int(11) NOT NULL COMMENT '代码长度',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `problem_id`(`problem_id`) USING BTREE,
  INDEX `contest_id`(`contest_id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE,
  INDEX `result`(`result`) USING BTREE,
  INDEX `language`(`language`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 304169 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'default' COMMENT '日志等级：[default, debug, info, warning, error, fatal]',
  `info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '产生日志的用户名',
  `ip_address` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '产生日志的地址',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `level`(`level`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_mall_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_mall_goods`;
CREATE TABLE `t_mall_goods`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `cost` int(11) NOT NULL DEFAULT 0 COMMENT '所需价格',
  `goods_type` int(11) NULL DEFAULT NULL COMMENT '商品大类类型：0：默认；1：实体物品；2：虚拟物品',
  `stock` int(11) NOT NULL DEFAULT -1 COMMENT '库存数量，-1为不限量',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商品介绍',
  `picture_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '图片url地址',
  `visible` tinyint(4) NULL DEFAULT 1 COMMENT '普通用户是否可见：0：不可见；1：可见',
  `shelf_user` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '上传用户',
  `shelf_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上架时间',
  `buy_limit` int(11) NULL DEFAULT NULL COMMENT '购买限制',
  `buy_verify_limit` int(11) NULL DEFAULT 0 COMMENT '购买权限限制：0：登录后不限制；',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_mall_order
-- ----------------------------
DROP TABLE IF EXISTS `t_mall_order`;
CREATE TABLE `t_mall_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL,
  `origin_cost` int(11) NOT NULL COMMENT '商品初始价格',
  `real_cost` int(11) NOT NULL COMMENT '实际付出价格',
  `order_user` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '购买用户',
  `order_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '下单时间',
  `review_user` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核订单用户',
  `order_status` smallint(6) NULL DEFAULT NULL COMMENT '订单状态: 0:无需审核；1：等待审核；2：订单失效；3：订单成交',
  `order_cancel` tinyint(4) NULL DEFAULT 0 COMMENT '用户是否主动取消：0：未取消订单；1：取消订单',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_permission_type
-- ----------------------------
DROP TABLE IF EXISTS `t_permission_type`;
CREATE TABLE `t_permission_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_problem_difficult
-- ----------------------------
DROP TABLE IF EXISTS `t_problem_difficult`;
CREATE TABLE `t_problem_difficult`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `problem_id` int(11) NULL DEFAULT NULL,
  `difficult_level` smallint(6) NULL DEFAULT NULL COMMENT '难度级别 1~3 ，越高越难',
  `problem_type` int(11) NULL DEFAULT 0 COMMENT '题目基本类型，0~6',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `problem_id`(`problem_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2615 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_problem_info
-- ----------------------------
DROP TABLE IF EXISTS `t_problem_info`;
CREATE TABLE `t_problem_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `problem_id` int(11) NULL DEFAULT NULL COMMENT '题目的题号',
  `title` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目标题',
  `belong_oj_id` int(11) NOT NULL DEFAULT 0 COMMENT '题目来源哪个OJ, 0为本地',
  `belong_problem_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题目在来源OJ里的ID',
  `author` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'spider' COMMENT '出题人',
  `total_submit` int(11) NOT NULL DEFAULT 0 COMMENT '总共提交次数',
  `total_ac` int(11) NOT NULL DEFAULT 0 COMMENT '提交次数内总共AC次数',
  `total_submit_user` int(11) NOT NULL DEFAULT 0 COMMENT '总共提交用户人数',
  `total_ac_user` int(11) NOT NULL DEFAULT 0 COMMENT '提交用户内总共AC人数',
  `visible` tinyint(1) NULL DEFAULT 1 COMMENT '是否普通用户可见，0： 不可见， 1：可见',
  `judge_option` smallint(6) NULL DEFAULT NULL COMMENT '评测机制选择： 0：默认，1：本地评测优先；2：第三方评测优先',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `problem_id`(`problem_id`) USING BTREE,
  INDEX `problem_id_2`(`problem_id`) USING BTREE,
  INDEX `belong_oj_id`(`belong_oj_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2615 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_problem_sample
-- ----------------------------
DROP TABLE IF EXISTS `t_problem_sample`;
CREATE TABLE `t_problem_sample`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `problem_id` int(11) NULL DEFAULT NULL,
  `case_order` int(11) NULL DEFAULT NULL COMMENT '样例顺序',
  `input_case` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '输入样例',
  `output_case` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '输出样例',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3237 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_problem_star
-- ----------------------------
DROP TABLE IF EXISTS `t_problem_star`;
CREATE TABLE `t_problem_star`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `problem_id` int(11) NOT NULL COMMENT '收藏的题目ID',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '收藏时间',
  `mark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_problem_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_problem_tag`;
CREATE TABLE `t_problem_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名称',
  `tag_type` int(11) NULL DEFAULT -1 COMMENT '标签隶属类型',
  `create_user` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'SYSTEM' COMMENT '标签创建者',
  `priority` int(11) NULL DEFAULT 0 COMMENT '优先级',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_problem_tag_record
-- ----------------------------
DROP TABLE IF EXISTS `t_problem_tag_record`;
CREATE TABLE `t_problem_tag_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `problem_id` int(11) NOT NULL COMMENT '题目ID',
  `tag_id` int(11) NOT NULL,
  `time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '贴标签时间',
  `confidence` double NULL DEFAULT 0 COMMENT '标签置信度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57406 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_problem_view
-- ----------------------------
DROP TABLE IF EXISTS `t_problem_view`;
CREATE TABLE `t_problem_view`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `problem_id` int(11) NULL DEFAULT NULL,
  `time_limit` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '时间限制',
  `memory_limit` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内存限制',
  `int_format` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '64位int使用的格式化字符串',
  `spj` tinyint(1) NOT NULL COMMENT '特判标记, 0：不是，1：是',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目描述',
  `input` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '输入',
  `output` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '输出',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `problem_id`(`problem_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2614 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_rating_record
-- ----------------------------
DROP TABLE IF EXISTS `t_rating_record`;
CREATE TABLE `t_rating_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `current_rating` int(11) NOT NULL COMMENT '记录时的积分值',
  `reason` int(11) NOT NULL DEFAULT 0 COMMENT '积分变更原因：0：默认；',
  `time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '记录时间',
  `mark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原因标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_system_info
-- ----------------------------
DROP TABLE IF EXISTS `t_system_info`;
CREATE TABLE `t_system_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性名',
  `value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `t_user_auth`;
CREATE TABLE `t_user_auth`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `salt` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '加密盐值',
  `password` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `attempt_login_fail_count` smallint(6) NULL DEFAULT 0 COMMENT '尝试登录失败次数',
  `locked` smallint(6) NULL DEFAULT 0 COMMENT '0为未锁定，1为锁定',
  `unlock_time` datetime(0) NULL DEFAULT NULL COMMENT '账号解锁时间',
  `last_login_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  CONSTRAINT `t_user_auth_ibfk_1` FOREIGN KEY (`username`) REFERENCES `t_user_base_info` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_base_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_base_info`;
CREATE TABLE `t_user_base_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '16个英文字母的登录名',
  `nick` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '八个汉字或者八个字符',
  `gender` smallint(6) NULL DEFAULT NULL COMMENT '性别，0为保密，1为男，2为女',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电话',
  `motto` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个性签名',
  `register_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '注册时间',
  `rating` int(11) NOT NULL DEFAULT 0 COMMENT '评分',
  `ranking` int(11) NOT NULL DEFAULT 0 COMMENT '排行',
  `ac_num` int(11) NOT NULL DEFAULT 0 COMMENT 'AC题数',
  `acb` int(11) NOT NULL DEFAULT 0 COMMENT 'ACB数量',
  `school` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学校',
  `faculty` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学院',
  `major` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业',
  `cla` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '班级',
  `student_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学号',
  `graduation_year` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '毕业年份，字符串类型',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_check_in
-- ----------------------------
DROP TABLE IF EXISTS `t_user_check_in`;
CREATE TABLE `t_user_check_in`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `check_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `info` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'normal' COMMENT '签到状态',
  `ip_address` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '签到ip地址',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_custom_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_custom_info`;
CREATE TABLE `t_user_custom_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `avatar_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '用户头像url地址',
  `adjective_id` int(11) NULL DEFAULT NULL COMMENT '形容词头衔ID',
  `article_id` int(11) NULL DEFAULT NULL COMMENT '名词头衔ID',
  `seal_id` int(11) NULL DEFAULT NULL COMMENT '印章ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  CONSTRAINT `t_user_custom_info_ibfk_1` FOREIGN KEY (`username`) REFERENCES `t_user_base_info` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_message
-- ----------------------------
DROP TABLE IF EXISTS `t_user_message`;
CREATE TABLE `t_user_message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` smallint(6) NULL DEFAULT 0,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 95574 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_user_permission`;
CREATE TABLE `t_user_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `permission_id` int(11) NOT NULL,
  `granter` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '授权人',
  `grant_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '授予时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE,
  CONSTRAINT `t_user_permission_ibfk_1` FOREIGN KEY (`username`) REFERENCES `t_user_base_info` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_problem_solved
-- ----------------------------
DROP TABLE IF EXISTS `t_user_problem_solved`;
CREATE TABLE `t_user_problem_solved`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `problem_id` int(11) NULL DEFAULT NULL,
  `try_count` int(11) NULL DEFAULT 0 COMMENT '尝试次数',
  `solved_count` int(11) NULL DEFAULT 0 COMMENT '解决次数',
  `last_try_time` datetime(0) NULL DEFAULT NULL COMMENT '最后尝试日期',
  `first_solved_time` datetime(0) NULL DEFAULT NULL COMMENT '第一次解锁日期',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE,
  INDEX `problem_id`(`problem_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 99814 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_seal
-- ----------------------------
DROP TABLE IF EXISTS `t_user_seal`;
CREATE TABLE `t_user_seal`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NULL DEFAULT 0 COMMENT '目前未定义属性',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '印章名称',
  `picture_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '印章图片url',
  `life_time` int(11) NULL DEFAULT -1 COMMENT '印章有效时间，单位：天',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_seal_record
-- ----------------------------
DROP TABLE IF EXISTS `t_user_seal_record`;
CREATE TABLE `t_user_seal_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `seal_id` int(11) NOT NULL COMMENT '印章ID',
  `obtain_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '获得时间',
  `expired_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `seal_id`(`seal_id`) USING BTREE,
  CONSTRAINT `t_user_seal_record_ibfk_1` FOREIGN KEY (`username`) REFERENCES `t_user_base_info` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_user_seal_record_ibfk_2` FOREIGN KEY (`seal_id`) REFERENCES `t_user_seal` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_title
-- ----------------------------
DROP TABLE IF EXISTS `t_user_title`;
CREATE TABLE `t_user_title`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NULL DEFAULT NULL COMMENT '头衔类型：0：未定义；1：形容词；2：名词头衔',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '头衔名称',
  `picture_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '头衔图片url，如果有',
  `life_time` int(11) NULL DEFAULT -1 COMMENT '头衔有效时间，单位：天',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_title_record
-- ----------------------------
DROP TABLE IF EXISTS `t_user_title_record`;
CREATE TABLE `t_user_title_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title_id` int(11) NOT NULL COMMENT '头衔ID',
  `obtain_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '获得时间',
  `expired_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `title_id`(`title_id`) USING BTREE,
  CONSTRAINT `t_user_title_record_ibfk_1` FOREIGN KEY (`username`) REFERENCES `t_user_base_info` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_user_title_record_ibfk_2` FOREIGN KEY (`title_id`) REFERENCES `t_user_title` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for v_judge_status
-- ----------------------------
DROP VIEW IF EXISTS `v_judge_status`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_judge_status` AS select `tjs`.`id` AS `id`,`tubi`.`nick` AS `nick`,`tjs`.`username` AS `username`,`tjs`.`problem_id` AS `problem_id`,`tjs`.`contest_id` AS `contest_id`,`tjs`.`language` AS `language`,`tjs`.`submit_time` AS `submit_time`,`tjs`.`result` AS `result`,`tjs`.`score` AS `score`,`tjs`.`time_used` AS `time_used`,`tjs`.`memory_used` AS `memory_used`,`tjs`.`code` AS `code`,`tjs`.`code_length` AS `code_length` from (`t_judge_status` `tjs` join `t_user_base_info` `tubi` on((`tjs`.`username` = `tubi`.`username`))) order by `tjs`.`id` desc;

SET FOREIGN_KEY_CHECKS = 1;
