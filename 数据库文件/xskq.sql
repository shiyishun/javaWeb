/*
Navicat MySQL Data Transfer

Source Server         : data
Source Server Version : 50703
Source Host           : localhost:3306
Source Database       : xskq

Target Server Type    : MYSQL
Target Server Version : 50703
File Encoding         : 65001

Date: 2018-06-21 16:35:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_call_theroll`
-- ----------------------------
DROP TABLE IF EXISTS `tb_call_theroll`;
CREATE TABLE `tb_call_theroll` (
  `call_theroll_id` varchar(32) NOT NULL COMMENT '记录标识',
  `course_id` varchar(32) DEFAULT NULL COMMENT '课程ID',
  `course_time_id` varchar(32) DEFAULT NULL COMMENT '课程时间ID',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `call_state` int(1) DEFAULT '0' COMMENT '签到状态（0：缺勤；1：签到：2：请假）',
  `call_date` datetime DEFAULT NULL COMMENT '签到日期',
  `call_position` varchar(10) DEFAULT NULL COMMENT '签到位置',
  `call_order` int(2) DEFAULT NULL COMMENT '签到周序',
  PRIMARY KEY (`call_theroll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点名表';

-- ----------------------------
-- Records of tb_call_theroll
-- ----------------------------
INSERT INTO `tb_call_theroll` VALUES ('4028470463e7a2530163e869d46c0002', '10001', '1', '170327155', '0', '2018-06-10 14:37:20', '0*0', '1');
INSERT INTO `tb_call_theroll` VALUES ('4028470463e7a2530163e869d4760003', '10001', '1', '170327154', '1', '2018-06-10 14:37:20', '6*6', '1');
INSERT INTO `tb_call_theroll` VALUES ('4028470463e7a2530163e869d47e0004', '10001', '1', '170327153', '0', '2018-06-10 14:37:20', '0*0', '1');
INSERT INTO `tb_call_theroll` VALUES ('402847046418b85a016418d157640000', '10001', '1', '170327155', '0', '2018-06-20 00:12:10', '0*0', '2');
INSERT INTO `tb_call_theroll` VALUES ('402847046418b85a016418d1578d0001', '10001', '1', '170327154', '0', '2018-06-20 00:12:10', '0*0', '2');
INSERT INTO `tb_call_theroll` VALUES ('402847046418b85a016418d157930002', '10001', '1', '170327153', '0', '2018-06-20 00:12:10', '0*0', '2');
INSERT INTO `tb_call_theroll` VALUES ('402847046418b85a016418d3b27c0003', '4028b88163c111570163c11e7ead0008', '4028b88163c111570163c11f12d1000a', '170327153', '0', '2018-06-20 00:14:44', '0*0', '1');
INSERT INTO `tb_call_theroll` VALUES ('402847046418b85a016418d3b2860004', '4028b88163c111570163c11e7ead0008', '4028b88163c111570163c11f12d1000a', '170327154', '0', '2018-06-20 00:14:44', '0*0', '1');

-- ----------------------------
-- Table structure for `tb_course`
-- ----------------------------
DROP TABLE IF EXISTS `tb_course`;
CREATE TABLE `tb_course` (
  `course_id` varchar(32) NOT NULL COMMENT '记录标识',
  `course_no` varchar(10) DEFAULT NULL COMMENT '课程编号',
  `course_name` varchar(255) DEFAULT NULL COMMENT '课程名称',
  `description` varchar(255) DEFAULT NULL COMMENT '课程描述',
  `term` varchar(10) DEFAULT NULL COMMENT '学期',
  `class_date` varchar(10) DEFAULT NULL COMMENT '学周',
  `class_order` varchar(10) DEFAULT NULL COMMENT '学分',
  `daily_weight` double DEFAULT NULL COMMENT '日常比重',
  `final_weight` double DEFAULT NULL COMMENT '期末比例',
  `picket_line` int(11) DEFAULT NULL COMMENT '警戒线',
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程表';

-- ----------------------------
-- Records of tb_course
-- ----------------------------
INSERT INTO `tb_course` VALUES ('10001', '10001', '程序设计', null, null, '1-12', '4', '0.2', '0.8', '5');
INSERT INTO `tb_course` VALUES ('4028b88163c111570163c11e7ead0008', '10005', '面向对象', '', null, '1-12', '4', '0.4', '0.6', '3');

-- ----------------------------
-- Table structure for `tb_course_time`
-- ----------------------------
DROP TABLE IF EXISTS `tb_course_time`;
CREATE TABLE `tb_course_time` (
  `course_time_id` varchar(32) NOT NULL COMMENT '课程时间标识',
  `week` int(1) DEFAULT NULL COMMENT '星期（1-6）',
  `start_period` int(2) DEFAULT NULL COMMENT '开始节次',
  `end_period` int(2) DEFAULT NULL COMMENT '结束节次',
  `class_location` varchar(100) DEFAULT NULL COMMENT '上课地点',
  `class_shape` varchar(30) DEFAULT NULL COMMENT '教室布局',
  PRIMARY KEY (`course_time_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程时间表';

-- ----------------------------
-- Records of tb_course_time
-- ----------------------------
INSERT INTO `tb_course_time` VALUES ('1', '1', '1', '2', '计算机学院3号楼106', '8*8');
INSERT INTO `tb_course_time` VALUES ('2', '3', '5', '6', '计算机学院3号楼106', '8*8');
INSERT INTO `tb_course_time` VALUES ('4028b88163c111570163c11f12d1000a', '5', '1', '4', '东3-309', '8*8');

-- ----------------------------
-- Table structure for `tb_course_time_rel`
-- ----------------------------
DROP TABLE IF EXISTS `tb_course_time_rel`;
CREATE TABLE `tb_course_time_rel` (
  `course_time_rel_id` varchar(32) NOT NULL COMMENT '记录标识',
  `course_id` varchar(32) DEFAULT NULL COMMENT '课程标识',
  `course_time_id` varchar(32) DEFAULT NULL COMMENT '课程时间标识',
  PRIMARY KEY (`course_time_rel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程时间关联表';

-- ----------------------------
-- Records of tb_course_time_rel
-- ----------------------------
INSERT INTO `tb_course_time_rel` VALUES ('2', '10001', '1');
INSERT INTO `tb_course_time_rel` VALUES ('3', '10001', '2');
INSERT INTO `tb_course_time_rel` VALUES ('4028b88163c111570163c11f12d1000b', '4028b88163c111570163c11e7ead0008', '4028b88163c111570163c11f12d1000a');

-- ----------------------------
-- Table structure for `tb_dict`
-- ----------------------------
DROP TABLE IF EXISTS `tb_dict`;
CREATE TABLE `tb_dict` (
  `dict_id` varchar(32) NOT NULL,
  `dict_no` int(11) DEFAULT NULL COMMENT '字典编号',
  `dict_name` varchar(60) NOT NULL COMMENT '字典名称',
  `dict_value` varchar(10) DEFAULT NULL COMMENT '字典',
  `dict_category` varchar(30) DEFAULT NULL COMMENT '字典类别',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父类id',
  `description` varchar(255) DEFAULT NULL COMMENT '字典描述',
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of tb_dict
-- ----------------------------
INSERT INTO `tb_dict` VALUES ('100', '100', '性别', null, null, null, null);
INSERT INTO `tb_dict` VALUES ('101', '101', '男', '0', '性别', '100', null);
INSERT INTO `tb_dict` VALUES ('102', '102', '女', '1', '性别', '100', null);
INSERT INTO `tb_dict` VALUES ('200', '200', '是否有效', null, null, null, null);
INSERT INTO `tb_dict` VALUES ('201', '201', '是', '0', '是否有效', '200', null);
INSERT INTO `tb_dict` VALUES ('202', '202', '否', '1', '是否有效', '200', null);
INSERT INTO `tb_dict` VALUES ('300', '300', '班级', null, null, null, null);
INSERT INTO `tb_dict` VALUES ('301', '301', '1班', '1', '班级', '300', null);
INSERT INTO `tb_dict` VALUES ('302', '302', '2班', '2', '班级', '300', null);
INSERT INTO `tb_dict` VALUES ('303', '303', '3班', '3', '班级', '300', null);
INSERT INTO `tb_dict` VALUES ('304', '304', '4班', '4', '班级', '300', null);
INSERT INTO `tb_dict` VALUES ('305', '305', '5班', '5', '班级', '300', null);
INSERT INTO `tb_dict` VALUES ('400', '400', '用户状态', null, null, null, null);
INSERT INTO `tb_dict` VALUES ('401', '401', '有效', '0', '用户状态', '400', null);
INSERT INTO `tb_dict` VALUES ('402', '402', '挂起', '1', '用户状态', '400', null);
INSERT INTO `tb_dict` VALUES ('403', '403', '注销', '2', '用户状态', '400', null);
INSERT INTO `tb_dict` VALUES ('500', '500', '星期', null, null, null, null);
INSERT INTO `tb_dict` VALUES ('501', '501', '星期一', '1', '星期', '500', null);
INSERT INTO `tb_dict` VALUES ('502', '502', '星期二', '2', '星期', '500', null);
INSERT INTO `tb_dict` VALUES ('503', '503', '星期三', '3', '星期', '500', null);
INSERT INTO `tb_dict` VALUES ('504', '504', '星期四', '4', '星期', '500', null);
INSERT INTO `tb_dict` VALUES ('505', '505', '星期五', '5', '星期', '500', null);
INSERT INTO `tb_dict` VALUES ('506', '506', '星期六', '6', '星期', '500', null);
INSERT INTO `tb_dict` VALUES ('600', '600', '节次', null, null, null, null);
INSERT INTO `tb_dict` VALUES ('601', '601', '节', '0', '节次', '600', null);
INSERT INTO `tb_dict` VALUES ('700', '700', '签到状态', null, null, null, null);
INSERT INTO `tb_dict` VALUES ('701', '701', '缺勤', '0', '签到状态', '700', null);
INSERT INTO `tb_dict` VALUES ('702', '702', '签到', '1', '签到状态', '700', null);
INSERT INTO `tb_dict` VALUES ('703', '703', '请假', '2', '签到状态', '700', null);

-- ----------------------------
-- Table structure for `tb_mark`
-- ----------------------------
DROP TABLE IF EXISTS `tb_mark`;
CREATE TABLE `tb_mark` (
  `mark_id` varchar(36) NOT NULL COMMENT '记录标识',
  `course_id` varchar(32) DEFAULT NULL COMMENT '课程ID',
  `user_id` varchar(32) NOT NULL COMMENT '学生ID',
  `daily_score` float DEFAULT '0' COMMENT '平时成绩',
  `final_score` float DEFAULT '0' COMMENT '考试成绩',
  `exam_score` float DEFAULT '0' COMMENT '期末成绩',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='成绩表';

-- ----------------------------
-- Records of tb_mark
-- ----------------------------
INSERT INTO `tb_mark` VALUES ('362e4d9273d811e88c9254e1adb796bb', '10001', '170327155', '0', '0', '0');
INSERT INTO `tb_mark` VALUES ('362e4ed773d811e88c9254e1adb796bb', '10001', '170327154', '0', '0', '0');
INSERT INTO `tb_mark` VALUES ('362e4f1573d811e88c9254e1adb796bb', '10001', '170327153', '0', '0', '0');
INSERT INTO `tb_mark` VALUES ('fdc8040e-73dd-11e8-8c92-54e1adb796bb', '4028b88163c111570163c11e7ead0008', '170327153', '0', '0', '0');
INSERT INTO `tb_mark` VALUES ('fdc80875-73dd-11e8-8c92-54e1adb796bb', '4028b88163c111570163c11e7ead0008', '170327154', '0', '0', '0');

-- ----------------------------
-- Table structure for `tb_menu`
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu` (
  `menu_id` varchar(32) NOT NULL COMMENT '记录标识',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父类菜单id',
  `menu_name` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `menu_no` int(255) DEFAULT NULL COMMENT '菜单编号',
  `level` int(11) DEFAULT NULL COMMENT '菜单级别',
  `menu_url` varchar(255) DEFAULT NULL COMMENT '菜单地址',
  `is_leaf` int(1) DEFAULT NULL COMMENT '是否叶节点（0：是；1：否）',
  `menu_icon_path` varchar(255) DEFAULT NULL COMMENT '菜单图标路径',
  `is_visable` int(1) DEFAULT '0' COMMENT '是否有效（0：是；1否）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
INSERT INTO `tb_menu` VALUES ('1', null, '用户管理', '1', '1', 'user_mng.html', '0', null, '0', '2018-04-26 16:53:12', 'fa fa-dashboard');
INSERT INTO `tb_menu` VALUES ('2', null, '角色权限', '2', '1', 'role_permi.html', '1', null, '0', '2018-04-26 16:54:26', 'fa fa-book');
INSERT INTO `tb_menu` VALUES ('20', null, '系统管理', '20', '1', 'sys_mng.html', '1', null, '0', '2018-04-26 17:01:25', 'fa fa-cogs');
INSERT INTO `tb_menu` VALUES ('2001', '20', '字典设置', '2001', '2', 'dict_setting.html', '0', null, '0', '2018-04-26 17:03:55', null);
INSERT INTO `tb_menu` VALUES ('2002', '20', '密码修改', '2002', '2', 'password_modify.html', '0', null, '0', '2018-04-26 17:05:23', null);
INSERT INTO `tb_menu` VALUES ('201', '2', '角色查询', '201', '2', 'role_search.html', '0', null, '0', '2018-06-11 19:17:21', null);
INSERT INTO `tb_menu` VALUES ('202', '2', '权限查询', '202', '2', 'permi_search.html', '0', null, '0', '2018-06-11 19:18:52', null);
INSERT INTO `tb_menu` VALUES ('3', null, '学生管理', '3', '1', 'student_mng.html', '0', null, '0', '2018-04-26 16:58:40', 'fa fa-tasks');
INSERT INTO `tb_menu` VALUES ('4', null, '查看汇总', '4', '1', 'view_sum.html', '0', null, '0', '2018-04-26 16:58:37', 'fa fa-th');
INSERT INTO `tb_menu` VALUES ('5', null, '校园管理', '5', '1', 'school_mng.html', '0', null, '0', '2018-05-14 18:40:00', 'fa fa-tasks');
INSERT INTO `tb_menu` VALUES ('6', null, '课程管理', '6', '1', 'course_mng.html', '1', null, '0', '2018-05-06 23:08:11', 'fa fa-book');
INSERT INTO `tb_menu` VALUES ('601', '6', '课程查询', '601', '2', 'course_search.html', '0', null, '0', '2018-06-11 00:10:51', null);
INSERT INTO `tb_menu` VALUES ('602', '6', '课程时间查询', '602', '2', 'course_time_search.html', '0', null, '0', '2018-06-11 00:12:22', null);
INSERT INTO `tb_menu` VALUES ('7', null, '成绩管理', '7', '1', 'mark_mng.html', '0', null, '0', '2018-05-06 22:48:14', 'fa fa-book');
INSERT INTO `tb_menu` VALUES ('8', null, '签到管理', '8', '1', 'call_theroll_mng.html', '1', null, '0', '2018-06-10 12:26:23', 'fa fa-tasks');
INSERT INTO `tb_menu` VALUES ('801', '8', '签到统计', '801', '2', 'call_theroll_count.html', '0', null, '0', '2018-06-10 12:45:45', null);
INSERT INTO `tb_menu` VALUES ('802', '8', '签到查询', '802', '2', 'call_theroll_search.html', '0', null, '0', '2018-06-10 20:16:23', null);

-- ----------------------------
-- Table structure for `tb_permi`
-- ----------------------------
DROP TABLE IF EXISTS `tb_permi`;
CREATE TABLE `tb_permi` (
  `permi_id` varchar(32) NOT NULL COMMENT '记录标识',
  `permi_no` varchar(10) NOT NULL COMMENT '权限编号',
  `permi_name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
  `req_url` varchar(255) DEFAULT NULL COMMENT '请求地址',
  `is_page` int(1) DEFAULT NULL COMMENT '是否页面（0：是；1：否）',
  `is_menu` int(1) DEFAULT NULL COMMENT '是否菜单（是：0；否：1）',
  `is_action` int(1) DEFAULT NULL COMMENT '是否动作',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `user_id` varchar(32) DEFAULT NULL COMMENT '创建者用户id',
  `is_access` int(1) DEFAULT '0' COMMENT '是否可访问（0：可访问；1：拒绝访问）',
  PRIMARY KEY (`permi_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of tb_permi
-- ----------------------------
INSERT INTO `tb_permi` VALUES ('11', '1', '用户管理', null, 'user_mng.html', '0', '0', '1', '2018-04-24 19:11:27', null, '0');
INSERT INTO `tb_permi` VALUES ('12', '2', '角色权限', null, 'role_permi.html', '1', '0', '1', '2018-04-26 20:11:04', null, '0');
INSERT INTO `tb_permi` VALUES ('120', '20', '系统管理', null, 'sys_mng.html', '1', '0', '1', '2018-04-26 20:16:27', null, '0');
INSERT INTO `tb_permi` VALUES ('12001', '2001', '字典设置', null, 'dict_setting.html', '0', '0', '1', '2018-04-26 20:18:44', null, '0');
INSERT INTO `tb_permi` VALUES ('12002', '2002', '密码修改', null, 'password_modify.html', '0', '0', '1', '2018-04-26 20:18:41', null, '0');
INSERT INTO `tb_permi` VALUES ('1201', '201', '角色查询', null, 'role_search.html', '0', '0', '1', '2018-06-11 19:22:12', null, '0');
INSERT INTO `tb_permi` VALUES ('1202', '202', '权限查询', null, 'permi_search.html', '0', '0', '1', '2018-06-11 19:26:05', null, '0');
INSERT INTO `tb_permi` VALUES ('13', '3', '学生管理', null, 'student_mng.html', '0', '0', '1', '2018-04-26 20:13:04', null, '0');
INSERT INTO `tb_permi` VALUES ('14', '4', '查看汇总', null, 'view_sum.html', '0', '0', '1', '2018-04-26 20:14:07', null, '0');
INSERT INTO `tb_permi` VALUES ('15', '5', '校园管理', null, 'school_mng.html', '0', '0', '1', '2018-04-26 20:15:21', null, '0');
INSERT INTO `tb_permi` VALUES ('16', '6', '课程管理', null, 'course_mng.html', '0', '0', '1', '2018-05-17 21:00:18', null, '0');
INSERT INTO `tb_permi` VALUES ('1601', '601', '课程查询', null, 'course_search.html', '0', '0', '1', '2018-06-11 00:13:20', null, '0');
INSERT INTO `tb_permi` VALUES ('1602', '602', '课程时间查询', null, 'course_time_search.html', '0', '0', '1', '2018-06-11 00:14:01', null, '0');
INSERT INTO `tb_permi` VALUES ('17', '7', '成绩管理', null, 'mark_mng.html', '0', '0', '1', '2018-05-06 22:47:12', null, '0');
INSERT INTO `tb_permi` VALUES ('18', '8', '签到管理', null, 'call_theroll_mng.html', '1', '0', '1', '2018-06-10 12:27:20', null, '0');
INSERT INTO `tb_permi` VALUES ('1801', '801', '签到统计', null, 'call_theroll_count.html', '0', '0', '1', '2018-06-10 12:47:57', null, '0');
INSERT INTO `tb_permi` VALUES ('1802', '802', '签到查询', null, 'call_theroll_search.html', '0', '0', '1', '2018-06-10 20:18:16', null, '0');

-- ----------------------------
-- Table structure for `tb_question`
-- ----------------------------
DROP TABLE IF EXISTS `tb_question`;
CREATE TABLE `tb_question` (
  `question_id` varchar(32) NOT NULL COMMENT '记录标识',
  `course_id` varchar(32) DEFAULT NULL COMMENT '课程ID',
  `course_name` varchar(32) DEFAULT NULL COMMENT '课程名称',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `question_date` datetime DEFAULT NULL COMMENT '提问日期',
  `score` double DEFAULT NULL COMMENT '分数',
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提问表';

-- ----------------------------
-- Records of tb_question
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_role`
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `role_id` varchar(32) NOT NULL COMMENT '记录标识',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父级角色id',
  `role_name` varchar(32) NOT NULL COMMENT '角色名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `description` varchar(255) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES ('0', null, '超级管理员', '2018-04-21 19:48:30', null);
INSERT INTO `tb_role` VALUES ('1', null, '教师', '2018-05-06 22:42:59', null);
INSERT INTO `tb_role` VALUES ('2', null, '学生', '2018-05-14 19:58:59', null);

-- ----------------------------
-- Table structure for `tb_role_permi_rel`
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_permi_rel`;
CREATE TABLE `tb_role_permi_rel` (
  `role_permi_rel_id` varchar(32) NOT NULL,
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色id',
  `permi_id` varchar(32) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`role_permi_rel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of tb_role_permi_rel
-- ----------------------------
INSERT INTO `tb_role_permi_rel` VALUES ('01', '0', '11');
INSERT INTO `tb_role_permi_rel` VALUES ('02', '0', '12');
INSERT INTO `tb_role_permi_rel` VALUES ('020', '0', '120');
INSERT INTO `tb_role_permi_rel` VALUES ('02001', '0', '12001');
INSERT INTO `tb_role_permi_rel` VALUES ('02002', '0', '12002');
INSERT INTO `tb_role_permi_rel` VALUES ('0201', '0', '1201');
INSERT INTO `tb_role_permi_rel` VALUES ('0202', '0', '1202');
INSERT INTO `tb_role_permi_rel` VALUES ('05', '0', '15');
INSERT INTO `tb_role_permi_rel` VALUES ('1120', '1', '120');
INSERT INTO `tb_role_permi_rel` VALUES ('1122', '1', '12002');
INSERT INTO `tb_role_permi_rel` VALUES ('13', '1', '13');
INSERT INTO `tb_role_permi_rel` VALUES ('16', '1', '16');
INSERT INTO `tb_role_permi_rel` VALUES ('1601', '1', '1601');
INSERT INTO `tb_role_permi_rel` VALUES ('1602', '1', '1602');
INSERT INTO `tb_role_permi_rel` VALUES ('17', '1', '17');
INSERT INTO `tb_role_permi_rel` VALUES ('18', '1', '18');
INSERT INTO `tb_role_permi_rel` VALUES ('1801', '1', '1801');
INSERT INTO `tb_role_permi_rel` VALUES ('1802', '1', '1802');
INSERT INTO `tb_role_permi_rel` VALUES ('2120', '2', '120');
INSERT INTO `tb_role_permi_rel` VALUES ('21202', '2', '12002');

-- ----------------------------
-- Table structure for `tb_school_info`
-- ----------------------------
DROP TABLE IF EXISTS `tb_school_info`;
CREATE TABLE `tb_school_info` (
  `school_info_id` varchar(32) NOT NULL,
  `school_no` int(11) NOT NULL COMMENT '学校编号',
  `school` varchar(100) NOT NULL COMMENT '学校',
  `college` varchar(100) NOT NULL COMMENT '学院',
  `department` varchar(100) DEFAULT NULL COMMENT '系别',
  PRIMARY KEY (`school_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='校园信息表';

-- ----------------------------
-- Records of tb_school_info
-- ----------------------------
INSERT INTO `tb_school_info` VALUES ('1', '1', '福州大学', '数计学院', null);

-- ----------------------------
-- Table structure for `tb_teach_stu`
-- ----------------------------
DROP TABLE IF EXISTS `tb_teach_stu`;
CREATE TABLE `tb_teach_stu` (
  `teach_stu_id` varchar(32) NOT NULL COMMENT '记录标识',
  `no` varchar(20) DEFAULT NULL COMMENT '学号',
  `name` varchar(200) DEFAULT NULL COMMENT '姓名',
  `gender` int(1) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `grade` varchar(10) DEFAULT NULL COMMENT '年级',
  `major` varchar(255) DEFAULT NULL COMMENT '专业',
  `class_no` int(2) DEFAULT NULL COMMENT '班级',
  `is_teacher` int(1) DEFAULT NULL COMMENT '是否教师（0：是；1：否）',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `school_info_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`teach_stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='师生表';

-- ----------------------------
-- Records of tb_teach_stu
-- ----------------------------
INSERT INTO `tb_teach_stu` VALUES ('170327153', '170327153', '王韦', '0', '1990-06-06', '2017级', '计算机技术', '1', '1', '170327153', '1');
INSERT INTO `tb_teach_stu` VALUES ('170327154', '170327154', '周雅', '1', '1992-06-01', '2017级', '计算机技术', '1', '1', '170327154', '1');
INSERT INTO `tb_teach_stu` VALUES ('170327155', '170327155', '陈思盈', '1', '1992-01-31', '2017级', '计算机技术', '1', '1', '170327155', '1');
INSERT INTO `tb_teach_stu` VALUES ('4028b8816366fce7016367343cf50333', '233', '超级管理员', '0', '2018-05-17', '', '', '1', '1', '4028b8816349623801634a433b510005', '1');
INSERT INTO `tb_teach_stu` VALUES ('4028b881636e250a01636e446eee0002', '1234567', '李四', '0', '1982-05-17', '2017级', '', '1', '0', '4028b881636e250a01636e446eee0001', '1');

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` varchar(32) NOT NULL COMMENT '记录标识',
  `user_no` bigint(20) NOT NULL COMMENT '用户编号',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `pwd` varchar(50) NOT NULL COMMENT '密码',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) DEFAULT NULL COMMENT '电子邮箱',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `login_time` datetime DEFAULT NULL COMMENT '登陆时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登陆时间',
  `count` bigint(20) DEFAULT NULL COMMENT '登陆次数',
  `status` int(1) DEFAULT '0' COMMENT '用户状态（0：有效；1：挂起：2：注销）',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('170327153', '201805172122482102', '王韦', 'E10ADC3949BA59ABBE56E057F20F883E', '18060901552', '', '2018-05-25 20:48:15', null, null, null, '0');
INSERT INTO `tb_user` VALUES ('170327154', '201805172122482003', '周雅', 'E10ADC3949BA59ABBE56E057F20F883E', '18060901555', null, '2018-05-25 20:48:15', null, null, null, '0');
INSERT INTO `tb_user` VALUES ('170327155', '201805172122482002', '陈思盈', 'E10ADC3949BA59ABBE56E057F20F883E', '18060901155', null, '2018-05-25 20:54:00', null, null, null, '0');
INSERT INTO `tb_user` VALUES ('4028b8816349623801634a433b510005', '0', 'admin', 'E10ADC3949BA59ABBE56E057F20F883E', '18060905555', '', '2018-05-06 22:41:05', null, null, null, '0');
INSERT INTO `tb_user` VALUES ('4028b881636e250a01636e446eee0001', '201805172122482000', 'teach01', 'E10ADC3949BA59ABBE56E057F20F883E', '18060905101', '', '2018-05-17 21:22:49', null, null, null, '0');

-- ----------------------------
-- Table structure for `tb_user_course_rel`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_course_rel`;
CREATE TABLE `tb_user_course_rel` (
  `user_course_rel_id` varchar(32) NOT NULL COMMENT '用户课程关联标识',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户标识',
  `course_time_id` varchar(32) DEFAULT NULL COMMENT '课程时间标识',
  `course_id` varchar(32) DEFAULT NULL COMMENT '课程标识',
  PRIMARY KEY (`user_course_rel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户课程关联表';

-- ----------------------------
-- Records of tb_user_course_rel
-- ----------------------------
INSERT INTO `tb_user_course_rel` VALUES ('1', '4028b881636e250a01636e446eee0001', null, '10001');
INSERT INTO `tb_user_course_rel` VALUES ('2', '170327155', '1', '10001');
INSERT INTO `tb_user_course_rel` VALUES ('3', '170327154', '1', '10001');
INSERT INTO `tb_user_course_rel` VALUES ('4', '170327153', '1', '10001');
INSERT INTO `tb_user_course_rel` VALUES ('4028b88163c111570163c11e7ec90009', '4028b881636e250a01636e446eee0001', null, '4028b88163c111570163c11e7ead0008');
INSERT INTO `tb_user_course_rel` VALUES ('5', '170327155', '2', '10001');
INSERT INTO `tb_user_course_rel` VALUES ('6', '170327154', '2', '10001');
INSERT INTO `tb_user_course_rel` VALUES ('7', '170327153', '2', '10001');
INSERT INTO `tb_user_course_rel` VALUES ('8', '170327153', '4028b88163c111570163c11f12d1000a', '4028b88163c111570163c11e7ead0008');
INSERT INTO `tb_user_course_rel` VALUES ('9', '170327154', '4028b88163c111570163c11f12d1000a', '4028b88163c111570163c11e7ead0008');

-- ----------------------------
-- Table structure for `tb_user_role_rel`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role_rel`;
CREATE TABLE `tb_user_role_rel` (
  `user_role_rel_id` varchar(32) NOT NULL COMMENT '记录标识',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户',
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`user_role_rel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of tb_user_role_rel
-- ----------------------------
INSERT INTO `tb_user_role_rel` VALUES ('4028470463ec60330163ee87b7bb0000', '170327153', '2');
INSERT INTO `tb_user_role_rel` VALUES ('4028b88162e8814e0162e883191d0002', '170327155', '2');
INSERT INTO `tb_user_role_rel` VALUES ('4028b88162e8814e0162e883191d0003', '4028b8816349623801634a433b510005', '0');
INSERT INTO `tb_user_role_rel` VALUES ('4028b88162e8814e0162e883191d0004', '170327154', '2');
INSERT INTO `tb_user_role_rel` VALUES ('4028b881636e250a01636e470b9e0005', '4028b881636e250a01636e446eee0001', '1');
