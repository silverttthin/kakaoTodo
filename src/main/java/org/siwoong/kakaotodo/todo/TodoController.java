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
@RequestMapping("/todos")
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
	public ResponseEntity<GetTodoResponse> getTodo(@PathVariable Integer todoId) {
		return ResponseEntity.ok(todoService.findById(todoId));
	}

	// 목록 조회
	@GetMapping
	public ResponseEntity<List<GetTodoResponse>> getTodos() {
		return ResponseEntity.ok(todoService.findAll());
	}

	// 삭제
	@DeleteMapping
	public ResponseEntity<Void> deleteTodo(@RequestBody @Valid DeleteTodoRequest req) {
		todoService.delete(req);
		return ResponseEntity.noContent().build();
	}

	// 수정
	@PutMapping("{postId}")
	public ResponseEntity<GetTodoResponse> updateTodo(@PathVariable Integer postId, @RequestBody UpdateTodoRequest req){
		return ResponseEntity.ok(todoService.update(postId, req));
	}



}
