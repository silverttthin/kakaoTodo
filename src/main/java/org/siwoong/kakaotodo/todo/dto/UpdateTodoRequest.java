package org.siwoong.kakaotodo.todo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record UpdateTodoRequest(

	@NotNull(message = "userId는 필수입력값입니다")
	Long userId,

	@NotBlank(message = "암호는 필수입력값입니다")
	String password,

	@NotBlank(message = "내용은 필수입력값입니다")
	String content
) {
}
