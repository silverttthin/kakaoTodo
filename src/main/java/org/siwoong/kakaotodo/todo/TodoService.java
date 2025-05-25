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

	public void validateUserExist(Long userId) {
		String checkUserSql = "SELECT COUNT(*) FROM users WHERE id = ?";
		Integer cnt = jdbc.queryForObject(checkUserSql,Integer.class, userId);
		if(cnt == 0) throw new RuntimeException("[USER ERROR]존재하지 않는 유저입니다");
	}

	// 투두 생성
	public Integer create(CreateTodoRequest todo) {
		// user가 있는지 검증
		validateUserExist(todo.userId());

		// 입력값 기반으로 생성 쿼리 생성 후 저장
		String sql = "INSERT INTO todos (user_id, password, content, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
		LocalDateTime now = LocalDateTime.now();
		jdbc.update(sql,
			todo.userId(),
			todo.password(),
			todo.content(),
			Timestamp.valueOf(now),
			Timestamp.valueOf(now));

		return jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
	}

	// 단건 조회
	public GetTodoResponse findById(Long todoId) {
		String sql = """
            SELECT
              t.id,
              t.user_id    AS userId,
              u.name       AS userName,
              u.email      AS userEmail,
              t.content,
              t.created_at AS createdAt,
              t.updated_at AS updatedAt
            FROM todos t
            JOIN users u ON t.user_id = u.id
            WHERE t.id = ?
            """;

		return jdbc.query(sql, new BeanPropertyRowMapper<>(GetTodoResponse.class), todoId)
			.stream().findFirst().orElseThrow(() -> {throw new RuntimeException("해당 id를 가진 todo 없음");});
	}

	// 전체 조회
	public List<GetTodoResponse> findAll(Long userId, int page, int size) {

		if(page<1) page = 1;

		int offset = (page - 1) * size;

		if (userId == null) {
			// 쿼리변수없으면 걍 수정일 기반 전체 목록
			String sql = """
				SELECT
				    t.id,
				    t.user_id	AS userId,
				    u.name       AS userName,
				    u.email      AS userEmail,
				    t.content,
				    t.created_at AS createdAt,
				    t.updated_at AS updatedAt
				FROM todos t
				JOIN users u ON t.user_id = u.id
				ORDER BY t.updated_at DESC
				LIMIT ? OFFSET ?
				""";

			return jdbc.query(sql, new BeanPropertyRowMapper<>(GetTodoResponse.class), size, offset);
		} else {
			// userId가 있다면 해당 유저의 투두 가져오기
			String sql = """
				SELECT
				    t.id,
				    t.user_id	AS userId,
				    u.name       AS userName,
				    u.email      AS userEmail,
				    t.content,
				    t.created_at AS createdAt,
				    t.updated_at AS updatedAt
				FROM todos t
				JOIN users u ON t.user_id = u.id
				WHERE t.user_id = ?
				ORDER BY t.updated_at DESC
				LIMIT ? OFFSET ?
				""";


			return jdbc.query(sql, new BeanPropertyRowMapper<>(GetTodoResponse.class), userId, size, offset);
		}
	}

	// 삭제
	public void delete(Long todoId, DeleteTodoRequest request) {
		// 유저 검증
		validateUserExist(request.userId());

		// 게시글 존재 검증 및 비번검증
		String selectSql = "SELECT password FROM todos WHERE id = ?";
		String dbPassword = jdbc.query(selectSql, (rs) -> rs.next() ? rs.getString("password") : null, todoId);
		if (dbPassword == null) throw new RuntimeException("해당 id를 가진 todo 없음");
		if(!dbPassword.equals(request.password())) throw new RuntimeException("비밀번호가 틀림");

		// 삭제
		String sql = "DELETE FROM todos WHERE id = ?";
		jdbc.update(sql, todoId);
	}


	// 수정
	public GetTodoResponse update(Long todoId, UpdateTodoRequest request) {

		//유저 검증
		validateUserExist(request.userId());

		// 게시글 존재 검증 및 비번검증
		String selectSql = "SELECT password FROM todos WHERE id = ?";
		String dbPassword = jdbc.query(selectSql, (rs) -> rs.next() ? rs.getString("password") : null, todoId);
		if (dbPassword == null) throw new RuntimeException("해당 id를 가진 todo 없음");
		if (!dbPassword.equals(request.password())) throw new RuntimeException("비밀번호가 틀림");

		// 수정
		String updateSql  = "UPDATE todos SET content = ?, updated_at = NOW() WHERE id = ?";
		int rows = jdbc.update(updateSql, request.content(), todoId);
		if(rows != 1) throw new RuntimeException("수정에 실패했습니다");

		return findById(todoId);
	}



}