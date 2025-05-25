package org.siwoong.kakaotodo.todo.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record CreateTodoRequest(
	@NotNull
	Long userId,

	@NotBlank(message = "비밀번호는 필수값입니다")
	String password,

	@Size(max = 200, message = "내용은 필수값이며 최대 200자이내입니다.")
	@NotBlank
	String content
) {}
