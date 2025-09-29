package br.com.alura.AluraFake.task;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class TaskController {

    private final TaskService taskService;
    private final NewTaskDTOMapper newTaskDTOMapper;

    public TaskController(
        TaskService taskService,
        NewTaskDTOMapper newTaskDTOMapper
    ) {
        this.taskService = taskService;
        this.newTaskDTOMapper = newTaskDTOMapper;
    }

    @PostMapping("/task/new/opentext")
    public ResponseEntity newOpenTextExercise(@Valid @RequestBody NewTaskDTO newTask) {
        Task task = newTaskDTOMapper.toEntity(newTask);
        taskService.createOpenTextTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/task/new/singlechoice")
    public ResponseEntity newSingleChoice(@Valid @RequestBody NewTaskDTO newTask) {
        Task task = newTaskDTOMapper.toEntity(newTask);
        taskService.createSingleChoiceTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/task/new/multiplechoice")
    public ResponseEntity newMultipleChoice(@Valid @RequestBody NewTaskDTO newTask) {
        Task task = newTaskDTOMapper.toEntity(newTask);
        taskService.createMultipleChoiceTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}