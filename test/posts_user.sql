-- Query 5: Find all posts liked by a user
SELECT P.content
FROM LIKE_TABLE L, POST P
WHERE L.post_id = P.post_id AND L.user_id = 1;