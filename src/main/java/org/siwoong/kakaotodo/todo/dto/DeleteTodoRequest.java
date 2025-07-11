package org.siwoong.kakaotodo.todo.dto;


import jakarta.validation.constraints.NotNull;


public record DeleteTodoRequest(

	@NotNull(message = "userId는 필수입력값입니다")
	Long userId,

	@NotNull(message = "암호는 필수입력값입니다.")
	String password
) {
}
