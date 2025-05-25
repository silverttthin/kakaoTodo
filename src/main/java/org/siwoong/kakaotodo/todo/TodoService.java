package org.siwoong.kakaotodo.todo;



import lombok.RequiredArgsConstructor;
import org.siwoong.kakaotodo.todo.dto.CreateTodoRequest;
import org.siwoong.kakaotodo.todo.dto.DeleteTodoRequest;
import org.siwoong.kakaotodo.todo.dto.GetTodoResponse;
import org.siwoong.kakaotodo.todo.dto.UpdateTodoRequest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;


@Service
@RequiredArgsConstructor
public class TodoService {

	private final TodoRepository todoRepository;

	// 유저 디비 존재여부 검증
	private void checkUser(Long userId) {
		if (!todoRepository.existsUser(userId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다");
		}
	}


	// 투두 생성
	public Integer create(CreateTodoRequest todo) {
		// user가 있는지 검증
		checkUser(todo.userId());
		return todoRepository.insertTodo(todo);
	}

	// 단건 조회
	public GetTodoResponse findById(Long todoId) {
		GetTodoResponse todo =  todoRepository.findById(todoId);
		if(todo == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 투두입니다");
		return todo;
	}

	// 전체 조회
	public List<GetTodoResponse> findAll(Long userId, int page, int size) {
		// 1미만 입력 시 페이지 반드시 1로 보정
		if (page < 1) page = 1;
		int offset = (page - 1) * size;
		return todoRepository.findAll(userId, offset, size);
	}

	// 삭제
	public void delete(Long todoId, DeleteTodoRequest req) {
		checkUser(req.userId());

		String dbPwd = todoRepository.findPasswordById(todoId);
		if (dbPwd == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 todo가 없습니다");

		if (!dbPwd.equals(req.password()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀립니다");


		todoRepository.deleteById(todoId);
	}


	// 수정
	public GetTodoResponse update(Long todoId, UpdateTodoRequest req) {
		checkUser(req.userId());

		String dbPwd = todoRepository.findPasswordById(todoId);
		if (dbPwd == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 todo가 없습니다");

		if (!dbPwd.equals(req.password()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀립니다");

		int updated = todoRepository.updateContent(todoId, req.content());

		if (updated != 1) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "수정에 실패했습니다");
		}

		return findById(todoId);
	}

}