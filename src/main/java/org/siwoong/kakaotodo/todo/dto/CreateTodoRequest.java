package org.siwoong.kakaotodo.todo.dto;


import jakarta.validation.constraints.NotBlank;


public record CreateTodoRequest(
	@NotBlank
	String creator,

	@NotBlank
	String password,

	@NotBlank
	String content
) {}
