-- Query 1: Get all posts with usernames
SELECT P.post_id, P.content, U.username
FROM POST P, USERS U
WHERE P.user_id = U.user_id;