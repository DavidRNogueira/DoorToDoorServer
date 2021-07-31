package com.example.api.resources;

import com.example.api.dto.LineDto;
import com.example.api.dto.TaskDto;
import com.example.api.service.TaskService;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {
	private final TaskService taskService;

	public TaskController(final TaskService taskService) {
		this.taskService = taskService;
	}

	@PostMapping
	public String createTask (@RequestBody final TaskDto taskDto) {
		return taskService.createTask(taskDto);
	}

	@DeleteMapping("/{id}")
	public void deleteTask (@PathVariable final String id) throws NotFoundException {
		taskService.deleteTask(id);
	}

	@GetMapping("/{id}")
	public TaskDto getTask (@PathVariable final String id) {return taskService.getTaskById(id);}

	@PutMapping
	public void updateTask (@RequestBody final TaskDto taskDto) throws NotFoundException {
		taskService.updateTask(taskDto);
	}

	@PostMapping("/{id}/line")
	public void createNewLine (@PathVariable final String id,@RequestBody final LineDto lineDto) {
		taskService.createNewLine(lineDto, id);
	}
}
