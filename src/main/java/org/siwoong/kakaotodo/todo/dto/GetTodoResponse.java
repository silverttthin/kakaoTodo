package org.siwoong.kakaotodo.todo.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class GetTodoResponse {
	Integer id;
	Integer userId;
	@NotBlank
	String userName;
	@NotBlank @Email
	String userEmail;
	String content;
	LocalDateTime createdAt;
	LocalDateTime updatedAt;

}