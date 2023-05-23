SELECT id, DATEDIFF(month, start_date, finish_date) as month_count
FROM project
GROUP BY id
HAVING month_count = (
    SELECT DATEDIFF(month, start_date, finish_date) as month_count
    FROM project
    GROUP BY id
    ORDER BY month_count DESC
    LIMIT 1
);