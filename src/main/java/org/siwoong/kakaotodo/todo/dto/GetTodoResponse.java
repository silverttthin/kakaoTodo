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
	String content;
	String creator;
	LocalDateTime createdAt;
	LocalDateTime updatedAt;

}