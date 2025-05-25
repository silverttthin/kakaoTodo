package org.siwoong.kakaotodo.todo.dto;


public record DeleteTodoRequest(
	Integer todoId,
	String password
) {
}
