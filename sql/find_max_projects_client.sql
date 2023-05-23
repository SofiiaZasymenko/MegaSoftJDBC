SELECT client.name, COUNT(project.client_id) AS project_count
FROM client
JOIN project ON client.id = project.client_id
GROUP BY project.client_id
HAVING project_count = (
    SELECT COUNT(client_id) AS project_count
    FROM project
    GROUP BY client_id
    ORDER BY project_count DESC
    LIMIT 1
);