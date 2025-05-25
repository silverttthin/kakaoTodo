package org.siwoong.kakaotodo.todo;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.siwoong.kakaotodo.todo.dto.CreateTodoRequest;
import org.siwoong.kakaotodo.todo.dto.DeleteTodoRequest;
import org.siwoong.kakaotodo.todo.dto.GetTodoResponse;
import org.siwoong.kakaotodo.todo.dto.UpdateTodoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("todos")
public class TodoController {

	private final TodoService todoService;

	// 생성
	@PostMapping
	public ResponseEntity<Integer> createTodo(@RequestBody @Valid CreateTodoRequest req) {
		Integer createdId = todoService.create(req);
		return ResponseEntity.ok(createdId);
	}

	// 단건 조회
	@GetMapping("/{todoId}")
	public ResponseEntity<GetTodoResponse> getTodo(@PathVariable Long todoId) {
		return ResponseEntity.ok(todoService.findById(todoId));
	}

	// 목록 조회
	@GetMapping
	public ResponseEntity<List<GetTodoResponse>> getTodos(@RequestParam(required = false) Long userId,
		            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "2") int size) {
		return ResponseEntity.ok(todoService.findAll(userId, page, size));
	}


	// 삭제
	@DeleteMapping("/{todoId}")
	public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId, @RequestBody @Valid DeleteTodoRequest req) {
		todoService.delete(todoId, req);
		return ResponseEntity.noContent().build();
	}

	// 수정
	@PutMapping("{todoId}")
	public ResponseEntity<GetTodoResponse> updateTodo(@PathVariable Long todoId, @RequestBody UpdateTodoRequest req){
		return ResponseEntity.ok(todoService.update(todoId, req));
	}



}
