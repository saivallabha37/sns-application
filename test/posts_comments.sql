-- Query 4: Show posts with number of comments
SELECT P.post_id, COUNT(C.comment_id) AS total_comments
FROM POST P, COMMENTS C
WHERE P.post_id = C.post_id(+)
GROUP BY P.post_id;