package org.siwoong.kakaotodo.todo.dto;


public record UpdateTodoRequest(
	String password,

	String content,
	String creator
) {
}
