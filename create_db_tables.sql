CREATE DATABASE schema1;

\c schema1

CREATE TABLE department(dep_name VARCHAR(20) PRIMARY KEY, building NUMERIC, budget NUMERIC);

CREATE TABLE instructor(ID INT PRIMARY KEY, name VARCHAR(20), salary NUMERIC, dep_name VARCHAR(20) REFERENCES department);

CREATE TABLE classroom(building INT, room_number INT, capacity NUMERIC, PRIMARY KEY(building, room_number), UNIQUE(building), UNIQUE(room_number));

CREATE TABLE time_slot(id INT PRIMARY KEY, day VARCHAR(10), start_time TIME, end_time TIME);

CREATE TABLE student(id INT PRIMARY KEY, name VARCHAR(20), tot_credit INT, department VARCHAR(20) REFERENCES department, advisor_id INT REFERENCES instructor);

CREATE TABLE course(course_id INT PRIMARY KEY, title VARCHAR(20), credits INT, department VARCHAR(20) REFERENCES department);

CREATE TABLE prerequisite(course_id INT, prereq_id INT, PRIMARY KEY(course_id, prereq_id));

CREATE TABLE section(section_id INT PRIMARY KEY, semester INT, year INT, instructor_id INT REFERENCES instructor, course_id INT REFERENCES course, classroom_building INT REFERENCES classroom(building), classroom_room_no INT REFERENCES classroom(room_number));

CREATE TABLE takes(student_id INT REFERENCES student, section_id INT REFERENCES section, grade REAL, PRIMARY KEY(student_id, section_id));

CREATE TABLE section_time(time_slot INT REFERENCES time_slot, section_id INT REFERENCES section, PRIMARY KEY(time_slot, section_id));


CREATE DATABASE schema2;

\c schema2

CREATE TABLE employee(Fname CHAR(20), Minit CHAR(10), Lname CHAR(20), ssn INT PRIMARY KEY, Bdate DATE, address CHAR(20), sex CHARACTER(1), salary INT, super_ssn INT REFERENCES employee(ssn), dno INT);

CREATE TABLE department(Dname CHAR(20), Dnumber INT PRIMARY KEY, mgr_ssn INT REFERENCES employee, mgr_start_date DATE);

CREATE TABLE dept_locations(Dnumber INT REFERENCES department, Dlocation CHAR(20), PRIMARY KEY(Dnumber, Dlocation));

CREATE TABLE project(Pname CHAR(20), Pnumber INT PRIMARY KEY, Plocation CHAR(50), Dnumber INT REFERENCES department);

CREATE TABLE works_on(Essn INT REFERENCES employee, Pno INT REFERENCES project, Hours INT, PRIMARY KEY(Essn, Pno));

CREATE TABLE dependent(Essn INT REFERENCES employee, Dependent_name CHAR(20), sex CHARACTER(1), Bdate DATE, Relationship CHAR(20), PRIMARY KEY(Essn, Dependent_name));


CREATE DATABASE schema3;

\c schema3

CREATE TABLE sailors(sid INT PRIMARY KEY, sname CHAR(20), rating INT, age REAL);

CREATE TABLE boat(bid INT PRIMARY KEY, bname CHAR(20), color CHAR(10));

CREATE TABLE reserves(sid INT REFERENCES sailors, bid INT REFERENCES boat, day DATE, PRIMARY KEY(sid, bid));


CREATE DATABASE schema4;

\c schema4

CREATE TABLE movie(mov_id INT PRIMARY KEY, mov_title CHAR(50), mov_year INT, mov_time INT, mov_lang CHAR(50), mov_dt_rel DATE, mov_rel_country CHAR(5));

CREATE TABLE reviewer(rev_id INT PRIMARY KEY, rev_name CHAR(30));

CREATE TABLE genres(gen_id INT PRIMARY KEY, gen_title CHAR(20));

CREATE TABLE actor(act_id INT PRIMARY KEY, act_fname CHAR(20), act_lname CHAR(20), act_gender CHAR(1));

CREATE TABLE director(dir_id INT PRIMARY KEY, dir_fname CHAR(20), dir_lname CHAR(20));

CREATE TABLE movie_direction(dir_id INT REFERENCES director, mov_id INT REFERENCES movie, PRIMARY KEY(dir_id, mov_id));

CREATE TABLE movie_cast(act_id INT REFERENCES actor, mov_id INT REFERENCES movie, role CHAR(30), PRIMARY KEY(act_id, mov_id));

CREATE TABLE movie_genres(mov_id INT REFERENCES movie, gen_id INT REFERENCES genres, PRIMARY KEY(mov_id, gen_id));

CREATE TABLE rating(mov_id INT REFERENCES movie, rev_id INT REFERENCES reviewer, rev_stars INT, num_o_ratings INT, PRIMARY KEY(mov_id, rev_id));
