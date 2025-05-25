package org.siwoong.kakaotodo.todo;



import lombok.RequiredArgsConstructor;
import org.siwoong.kakaotodo.todo.dto.CreateTodoRequest;
import org.siwoong.kakaotodo.todo.dto.DeleteTodoRequest;
import org.siwoong.kakaotodo.todo.dto.GetTodoResponse;
import org.siwoong.kakaotodo.todo.dto.UpdateTodoRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TodoService {

	// jdbc 빈 가져오기
	private final JdbcTemplate jdbc;

	// 투두 생성
	public Integer create(CreateTodoRequest todo) {
		String sql = "INSERT INTO todos (creator, password, content, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
		LocalDateTime now = LocalDateTime.now();
		jdbc.update(sql,
			todo.creator(),
			todo.password(),
			todo.content(),
			Timestamp.valueOf(now),
			Timestamp.valueOf(now));

		return jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
	}

	// 단건 조회
	public GetTodoResponse findById(int id) {
		String sql = """
					    SELECT id, content, creator, created_at AS createdAt, updated_at AS updatedAt
					    FROM todos WHERE id = ?
					""";

		return jdbc.query(sql, new BeanPropertyRowMapper<>(GetTodoResponse.class), id)
			.stream().findFirst().orElseThrow(() -> {throw new RuntimeException("해당 id를 가진 todo 없음");});
	}

	// 전체 조회
	public List<GetTodoResponse> findAll() {
		String sql = "SELECT id, content, creator, created_at AS createdAt, updated_at AS updatedAt FROM todos"
			+ " ORDER BY updated_at DESC";
		return jdbc.query(sql, new BeanPropertyRowMapper<>(GetTodoResponse.class));
	}

	// 삭제
	public void delete(DeleteTodoRequest request) {
		String selectSql = "SELECT password FROM todos WHERE id = ?";
		String dbPassword = jdbc.query(selectSql, (rs) -> rs.next() ? rs.getString("password") : null, request.todoId());

		if (dbPassword == null) throw new RuntimeException("해당 id를 가진 todo 없음");

		if(!dbPassword.equals(request.password())) throw new RuntimeException("비밀번호가 틀림");

		String sql = "DELETE FROM todos WHERE id = ?";
		jdbc.update(sql, request.todoId());
	}


	// 수정
	public GetTodoResponse update(Integer postId, UpdateTodoRequest request) {
		String selectSql = "SELECT password FROM todos WHERE id = ?";
		String dbPassword = jdbc.query(selectSql, (rs) -> rs.next() ? rs.getString("password") : null, postId);
		if (dbPassword == null) throw new RuntimeException("해당 id를 가진 todo 없음");
		if (!dbPassword.equals(request.password())) throw new RuntimeException("비밀번호가 틀림");

		String updateSql  = "UPDATE todos SET creator = ?, content = ?, updated_at = NOW() WHERE id = ?";
		int rows = jdbc.update(updateSql, request.creator(), request.content(), postId);
		if(rows != 1) throw new RuntimeException("수정에 실패했습니다");

		return findById(postId);
	}


}