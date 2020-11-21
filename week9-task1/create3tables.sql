drop table if exists ExamResults;
drop table if exists Students;
drop table if exists Subjects;

CREATE SEQUENCE Students_seq;
CREATE TABLE Students (
  id int NOT NULL DEFAULT NEXTVAL ('Students_seq'),
  name varchar(64) DEFAULT NULL CHECK (name not SIMILAR TO '%(@|#|$)%'),
  surname varchar(64) DEFAULT NULL CHECK (name not SIMILAR TO '%(@|#|$)%'),
  dob date,
  phone bigint NOT NULL,
  skill varchar(64) DEFAULT NULL,
  created TIMESTAMP,
  updated TIMESTAMP,
  PRIMARY KEY (id)
)  ;
ALTER SEQUENCE Students_seq RESTART WITH 1;

CREATE SEQUENCE Subjects_seq;
CREATE TABLE Subjects (
  id int NOT NULL DEFAULT NEXTVAL ('Subjects_seq'),
  subject_name varchar(64) DEFAULT NULL,
  tutor varchar(64) DEFAULT NULL,
  PRIMARY KEY (id)
)  ;
ALTER SEQUENCE Subjects_seq RESTART WITH 1;

CREATE SEQUENCE ExamResults_seq;
CREATE TABLE ExamResults (
  id int NOT NULL DEFAULT NEXTVAL ('ExamResults_seq'),
  Student_id int NOT NULL,
  Subject_id int NOT NULL,
  mark int NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (Subject_id) REFERENCES  Subjects(id),
  FOREIGN KEY (Student_id) REFERENCES  Students(id)
)  ;
ALTER SEQUENCE ExamResults_seq RESTART WITH 1;