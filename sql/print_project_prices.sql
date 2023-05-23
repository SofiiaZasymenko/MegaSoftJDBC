SELECT project_worker.project_id,
SUM(worker.salary) * DATEDIFF(month, project.start_date, project.finish_date) AS price
FROM worker
JOIN project_worker ON worker.id = project_worker.worker_id
JOIN project ON project_worker.project_id = project.id
GROUP BY project_worker.project_id
ORDER BY price DESC;