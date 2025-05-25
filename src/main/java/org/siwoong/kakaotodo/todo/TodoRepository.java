package org.siwoong.kakaotodo.todo;


import lombok.RequiredArgsConstructor;
import org.siwoong.kakaotodo.todo.dto.CreateTodoRequest;
import org.siwoong.kakaotodo.todo.dto.GetTodoResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class TodoRepository {

	private final JdbcTemplate jdbc;

	// 유저 존재 검증
	public boolean existsUser(Long userId) {
		String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
		Integer cnt = jdbc.queryForObject(sql, Integer.class, userId);
		return cnt != null && cnt > 0;
	}

	// 투두 생성
	public Integer insertTodo(CreateTodoRequest req) {
		String sql = """
            INSERT INTO todos (user_id, password, content, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?)
            """;
		LocalDateTime now = LocalDateTime.now();
		jdbc.update(sql,
			req.userId(),
			req.password(),
			req.content(),
			Timestamp.valueOf(now),
			Timestamp.valueOf(now));
		return jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
	}

	// ===== 단건 조회 =====
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
			.stream()
			.findFirst()
			.orElse(null);
	}

	// ===== 전체/페이징 조회 =====
	public List<GetTodoResponse> findAll(Long userId, int offset, int size) {
		if (userId == null) {
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
                ORDER BY t.updated_at DESC
                LIMIT ? OFFSET ?
                """;
			return jdbc.query(sql, new BeanPropertyRowMapper<>(GetTodoResponse.class), size, offset);
		} else {
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
                WHERE t.user_id = ?
                ORDER BY t.updated_at DESC
                LIMIT ? OFFSET ?
                """;
			return jdbc.query(sql, new BeanPropertyRowMapper<>(GetTodoResponse.class), userId, size, offset);
		}
	}

	// ===== 삭제 =====
	public String findPasswordById(Long todoId) {
		String sql = "SELECT password FROM todos WHERE id = ?";
		return jdbc.query(sql, rs -> rs.next() ? rs.getString("password") : null, todoId);
	}

	public int deleteById(Long todoId) {
		String sql = "DELETE FROM todos WHERE id = ?";
		return jdbc.update(sql, todoId);
	}

	// ===== 수정 =====
	public int updateContent(Long todoId, String content) {
		String sql = "UPDATE todos SET content = ?, updated_at = NOW() WHERE id = ?";
		return jdbc.update(sql, content, todoId);
	}
}
