USE 'new_affairs';
DROP TABLE stu_student;
CREATE TABLE stu_student(
    stu_id INT NOT NULL   COMMENT '' ,
    stu_name VARCHAR(32)    COMMENT '' ,
    stu_sex VARCHAR(32)    COMMENT '' ,
    stu_password VARCHAR(32)    COMMENT '' ,
    PRIMARY KEY (stu_id)
) COMMENT = ' ';

ALTER TABLE stu_student COMMENT 'undefined';
DROP TABLE tea_teacher;
CREATE TABLE tea_teacher(
    tea_id INT NOT NULL   COMMENT '' ,
    tea_name VARCHAR(32)    COMMENT '' ,
    tea_sex VARCHAR(32)    COMMENT '' ,
    tea_password VARCHAR(32)    COMMENT '' ,
    PRIMARY KEY (tea_id)
) COMMENT = ' ';

ALTER TABLE tea_teacher COMMENT 'undefined';
DROP TABLE cou_course;
CREATE TABLE cou_course(
    cou_id INT NOT NULL AUTO_INCREMENT  COMMENT '' ,
    cou_name VARCHAR(32)    COMMENT '' ,
    cou_builder VARCHAR(32)    COMMENT '' ,
    cou_count VARCHAR(32)    COMMENT '' ,
    cou_time DATETIME    COMMENT '' ,
    PRIMARY KEY (cou_id)
) COMMENT = ' ';

ALTER TABLE cou_course COMMENT 'undefined';
DROP TABLE cou_elective;
CREATE TABLE cou_elective(
    elective_id INT NOT NULL AUTO_INCREMENT  COMMENT '' ,
    cou_id INT    COMMENT '' ,
    stu_id INT    COMMENT '' ,
    tea_id INT    COMMENT '' ,
    elective_time DATETIME    COMMENT '' ,
    PRIMARY KEY (elective_id)
) COMMENT = ' ';

ALTER TABLE cou_elective COMMENT 'undefined';
