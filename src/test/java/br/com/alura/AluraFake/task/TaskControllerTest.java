package br.com.alura.AluraFake.task;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.course.CourseService;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.user.UserRepository;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TaskService taskService;
    @MockBean
    private TaskRepository taskRepository;
    @MockBean
    private CourseService courseService;
    @MockBean
    private CourseRepository courseRepository;
    @MockBean
    private UserRepository userRepository;
    @SpyBean
    private NewTaskDTOMapper newTaskDTOMapper;

    @Test
    void newOpenText__should_create_open_text_task() throws Exception {
        Course course = mockCourse();
        NewTaskDTO newTaskDTO = new NewTaskDTO(
            course.getId(), 
            "Explique o ciclo de vida do Spring", 
            1, 
            null);

        doReturn(course).when(courseService).getById(newTaskDTO.courseId());
        doReturn(false).when(taskRepository).existsByCourseAndStatement(course, newTaskDTO.statement());

        mockMvc.perform(post("/task/new/opentext")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTaskDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void newSingleChoice__should_create_single_choice_task() throws Exception {
        Course course = mockCourse();
        NewTaskDTO newTaskDTO = new NewTaskDTO(
            1L, 
            "Escolha as tecnologias utilizadas", 
            1, 
            List.of(
                new NewTaskOptionDTO("Java", false), 
                new NewTaskOptionDTO("JavaScript", false), 
                new NewTaskOptionDTO("Spring", true), 
                new NewTaskOptionDTO("HTML", false)
            ));

        doReturn(course).when(courseService).getById(newTaskDTO.courseId());
        doReturn(false).when(taskRepository).existsByCourseAndStatement(course, newTaskDTO.statement());

        mockMvc.perform(post("/task/new/singlechoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTaskDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void newMultipleChoice__should_create_multiple_choice_task() throws Exception {
        Course course = mockCourse();
        NewTaskDTO newTaskDTO = new NewTaskDTO(
            1L, 
            "Quais linguagens são tipadas estaticamente?", 
            1, 
            List.of(
                new NewTaskOptionDTO("Java", true), 
                new NewTaskOptionDTO("JavaScript", false), 
                new NewTaskOptionDTO("TypeScript", true), 
                new NewTaskOptionDTO("Python", false)
            ));

        doReturn(course).when(courseService).getById(newTaskDTO.courseId());
        doReturn(false).when(taskRepository).existsByCourseAndStatement(course, newTaskDTO.statement());

        mockMvc.perform(post("/task/new/multiplechoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTaskDTO)))
                .andExpect(status().isCreated());
    }

    private Course mockCourse() {
        Course course = mock(Course.class);
        doReturn(1L).when(course).getId();
        doReturn(Status.BUILDING).when(course).getStatus();
        return course;
    }
}
