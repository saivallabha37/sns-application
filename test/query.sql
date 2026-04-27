-- ============================================
-- SNS DATABASE - SOCIAL NETWORKING SERVICE
-- DBMS Project Base Implementation
-- ============================================

-- ============================================
-- CREATING TABLES (DDL)
-- ============================================

CREATE TABLE USERS (
    user_id NUMBER PRIMARY KEY,
    username VARCHAR2(50),
    email VARCHAR2(100)
);

CREATE TABLE POST (
    post_id NUMBER PRIMARY KEY,
    content VARCHAR2(500),
    created_at DATE,
    user_id NUMBER,
    FOREIGN KEY (user_id) REFERENCES USERS(user_id)
);

CREATE TABLE COMMENTS (
    comment_id NUMBER PRIMARY KEY,
    content VARCHAR2(500),
    created_at DATE,
    user_id NUMBER,
    post_id NUMBER,
    FOREIGN KEY (user_id) REFERENCES USERS(user_id),
    FOREIGN KEY (post_id) REFERENCES POST(post_id)
);

CREATE TABLE LIKE_TABLE (
    like_id NUMBER PRIMARY KEY,
    timestamp DATE,
    user_id NUMBER,
    post_id NUMBER,
    FOREIGN KEY (user_id) REFERENCES USERS(user_id),
    FOREIGN KEY (post_id) REFERENCES POST(post_id)
);

-- ============================================
-- INSERTING SAMPLE DATA (DML)
-- ============================================

INSERT INTO USERS VALUES (1, 'Sai', 'sai@gmail.com');
INSERT INTO USERS VALUES (2, 'Rahul', 'rahul@gmail.com');

INSERT INTO POST VALUES (101, 'Hello World!', SYSDATE, 1);
INSERT INTO POST VALUES (102, 'My first post', SYSDATE, 2);

INSERT INTO COMMENTS VALUES (201, 'Nice post!', SYSDATE, 2, 101);
INSERT INTO COMMENTS VALUES (202, 'Thanks!', SYSDATE, 1, 101);

INSERT INTO LIKE_TABLE VALUES (301, SYSDATE, 2, 101);
INSERT INTO LIKE_TABLE VALUES (302, SYSDATE, 1, 102);

-- ============================================
-- QUERIES
-- ============================================

-- Query 1: Get all posts with usernames
SELECT P.post_id, P.content, U.username
FROM POST P, USERS U
WHERE P.user_id = U.user_id;

-- Query 2: Get comments on a specific post
SELECT C.content, U.username
FROM COMMENTS C, USERS U
WHERE C.user_id = U.user_id AND C.post_id = 101;

-- Query 3: Count likes on each post
SELECT post_id, COUNT(*) AS total_likes
FROM LIKE_TABLE
GROUP BY post_id;

-- Query 4: Show posts with number of comments
SELECT P.post_id, COUNT(C.comment_id) AS total_comments
FROM POST P, COMMENTS C
WHERE P.post_id = C.post_id(+)
GROUP BY P.post_id;

-- Query 5: Find all posts liked by a user
SELECT P.content
FROM LIKE_TABLE L, POST P
WHERE L.post_id = P.post_id AND L.user_id = 2;
