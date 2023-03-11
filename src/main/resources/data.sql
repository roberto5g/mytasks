INSERT INTO users (name,email,password)
    VALUES
    ('Roberto','roberto@d.com','$2a$12$YjaiLd8csdBRVBuaX2T2vOxNezQAv3HXLzfybSnbgutC1S5pclApW'),
    ('Santos','santos@d.com','$2a$12$YjaiLd8csdBRVBuaX2T2vOxNezQAv3HXLzfybSnbgutC1S5pclApW');
INSERT INTO boards (description, title, owner_id, created_at) VALUES ('First Board','Board 1', 1, now()),('Second Board','Board 2', 2, now());
INSERT INTO tasks (created_at, description, status, due_date, title, updated_at, assignee_id, board_id, creator_id)
    VALUES
    (now(), 'task 1', 0, '2023-10-10','Title task 1',now(),2,1,1),
    (now(), 'task 2', 1,'2023-10-10','Title task 2',now(),1,1,2),
    (now(), 'task 3', 2,'2023-10-10','Title task 3',now(),1,2,2),
    (now(), 'task 4', 3,'2023-10-10','Title task 4',now(),1,2,2);

INSERT INTO comments (description, user_id, task_id, created_at)
    VALUES
    ('comment 1',1,1,now()),
    ('comment 2',2,1,now()),
    ('comment 3',1,2,now()),
    ('comment 4',2,3,now()),
    ('comment 5',1,1,now());