package org.siwoong.kakaotodo.todo.dto;


import jakarta.validation.constraints.NotBlank;


public record UpdateTodoRequest(

	@NotBlank(message = "암호는 필수입력값입니다")
	String password,

	@NotBlank(message = "내용은 필수입력값입니다")
	String content
) {
}
