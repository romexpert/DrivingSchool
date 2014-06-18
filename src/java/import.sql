INSERT INTO GROUPS (IID, NAME) VALUES (1, 'Group 1');
INSERT INTO GROUPS (IID, NAME) VALUES (2, 'Group 2');
INSERT INTO GROUPS (IID, NAME) VALUES (3, 'Group 3');

INSERT INTO PEOPLE (IID, AccountRole, email, name, password_hash, phone, group_id, instructor_id) VALUES (1, 'Admin', 'admin@driftman.ru', 'Admin Andry', 'admin123', '1 2345678', NULL, NULL);

INSERT INTO PEOPLE (IID, AccountRole, email, name, password_hash, phone, group_id, instructor_id) VALUES (2, 'Instructor', 'instructor@driftman.ru', 'Instructor Andry', 'instructor123', '2 2345678', NULL, NULL);
INSERT INTO PEOPLE (IID, AccountRole, email, name, password_hash, phone, group_id, instructor_id) VALUES (3, 'Instructor', 'instructor2@driftman.ru', 'Instructor 2', 'instructor2123', '3 2345678', NULL, NULL);
INSERT INTO PEOPLE (IID, AccountRole, email, name, password_hash, phone, group_id, instructor_id) VALUES (4, 'Instructor', 'instructor3@driftman.ru', 'Instructor 3', 'instructor3123', '4 2345678', NULL, NULL);

INSERT INTO PEOPLE (IID, AccountRole, email, name, password_hash, phone, group_id, instructor_id) VALUES (5, 'Student', 'student@driftman.ru', 'Andry', 'student123', '5 2345678', 1, 2);
INSERT INTO PEOPLE (IID, AccountRole, email, name, password_hash, phone, group_id, instructor_id) VALUES (6, 'Student', 'student2@driftman.ru', 'Student 2', 'student2123', '6 2345678', 1, 2);
INSERT INTO PEOPLE (IID, AccountRole, email, name, password_hash, phone, group_id, instructor_id) VALUES (7, 'Student', 'student3@driftman.ru', 'Student 3', 'student3123', '7 2345678', 2, 2);