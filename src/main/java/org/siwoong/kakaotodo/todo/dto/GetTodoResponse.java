package org.siwoong.kakaotodo.todo.dto;


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
	String content;
	LocalDateTime createdAt;
	LocalDateTime updatedAt;

}