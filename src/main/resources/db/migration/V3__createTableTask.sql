-- Tabela de tarefas
CREATE TABLE Task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    createdAt datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    course_id BIGINT NOT NULL,
    statement VARCHAR(255) NOT NULL,
    task_order INT NOT NULL,
    type ENUM('OPEN_TEXT', 'MULTIPLE_CHOICE', 'SINGLE_CHOICE') NOT NULL,

    CONSTRAINT fk_tasks_course FOREIGN KEY (course_id)
        REFERENCES Course(id)
        ON DELETE CASCADE
);

-- Tabela de opções de tarefa
CREATE TABLE TaskOption (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    createdAt datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    task_id BIGINT NOT NULL,
    option_text VARCHAR(80) NOT NULL,
    is_correct BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_options_task FOREIGN KEY (task_id)
        REFERENCES Task(id)
        ON DELETE CASCADE
);