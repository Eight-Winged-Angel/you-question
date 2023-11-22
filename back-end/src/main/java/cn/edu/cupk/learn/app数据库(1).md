## user

用户基本信息

| 属性名       | 类型              | 主键  | 外键 | 非空  | 备注         |
| ------------ | --------------- | ---- | ---- | ---- | ------------ |
| user_id      | bigint          | √    |      | √    | 主键         |
| create_time  | datetime        |      |      | √    | 创建时间     |
| update_time  | datetime        |      |      | √    | 修改时间     |
| user_name    | varchar(16)     |      |      |      | 用户名       |
| user_phone   | varchar(11)     |      |      | √    | 电话号码     |
| user_password| char(32)        |      |      |      | 密码（加密）  |
| school_id    | bigint          |      | √    |      | 关联的学校id  |
| user_qq      | varchar(11)     |      |      |      | qq号         |
| user_email   | varchar(20)     |      |      |      | 邮箱         |
| user_role    | tinyint         |      |      | √    | 身份         |
| user_profile | blob            |      |      |      | 头像         |

## question_answer

选择选项

| 属性名       | 类型             | 主键 | 外键  | 非空  | 备注          |
| ----------- | --------------- | ---- | ---- | ---- | ------------ |
| question_id | bigint          | √    | √    | √    |题目id         |
| answer_id   | tinyint         | √    | √    | √    | 选项/空的序号   |
| create_time | datetime        |      |      | √    | 创建时间       |
| update_time | datetime        |      |      | √    | 修改时间       |
|answer_content| varchar(255)   |      |      | √    | 选项内容       |
| is_correct  | tinyint         |      |      |      | 答案（是否正确）|

## practice

练习表

| 属性名      | 类型              | 主键 | 外键  | 非空  | 备注       |
| ----------- | --------------- | ---- | ---- | ---- | ---------- |
| practice_id | bigint          | √    |      | √    | 主键       |
|practice_name| varchar(255)    |      |      | √    | 练习名     |
| create_time | datetime        |      |      | √    | 创建时间   |
| update_time | datetime        |      |      | √    | 修改时间   |
| start_time  | datetime        |      |      |      | 开始时间   |
| end_time    | datetime        |      |      |      | 结束时间   |
| time_limit  | smallint        |      |      |      | 限时（秒）  |
| course_id   | bigint          |      | √    | √    | 关联课程id  |
| invite_code | char(6)         |      |      |      | 习题集邀请码 |

## question

题目

| 属性名      | 类型            | 主键 | 外键 | 非空 | 备注                                        |
| ----------- | --------------- | ---- | ---- | ---- | ------------------------------------------- |
| question_id | bigint          | √    |      | √    | 主键                                        |
| create_time | datetime        |      |      | √    | 创建时间                                    |
| update_time | datetime        |      |      | √    | 修改时间                                    |
|question_content| varchar(255) |      |      | √    | 题干                                        |
|question_type| tinyint         |      |      | √    | 0：单选 1：多选 2：判断 3：填空 4：主观大题 |
| question_image| blob          |      |      |      | 图片                                        |

## practice_question

练习-题目关联表

| 属性名         | 类型             | 主键 | 外键  | 非空  | 备注     |
| ------------- | --------------- | ---- | ---- | ---- | -------- |
| practice_id   | bigint          | √    | √    | √    | 练习id   |
| question_order| smallint        | √    |      | √    | 题号     |
| question_score| tinyint         |      |      |      | 小题分数  |
| question_id   | bigint          |      | √    | √    | 题目id   |


## question_submit

学生提交的答案

| 属性名       | 类型             | 主键 | 外键  | 非空  | 备注          |
| ----------- | --------------- | ---- | ---- | ---- | ------------- |
| question_id | bigint          | √    | √    | √    | 关联的题目id   |
|question_order| smallint       | √    | √    | √    | 题号          |
| user_id     | bigint          | √    | √    | √    | 关联的学生id   |
| create_time | datetime        |      |      | √    | 创建时间       |
| update_time | datetime        |      |      | √    | 修改时间       |
|answer_content| varchar(255)   |      |      | √    | 题目答案       |
题目答案使用特殊答案分割