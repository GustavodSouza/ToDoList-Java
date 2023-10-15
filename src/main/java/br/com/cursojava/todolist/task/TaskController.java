package br.com.cursojava.todolist.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
    ITaskRepository iTaskRepository;

    @PostMapping("/")
    public TaskModel created(@RequestBody TaskModel taskModel) {
       var task = this.iTaskRepository.save(taskModel);
       return task;
    }
}
