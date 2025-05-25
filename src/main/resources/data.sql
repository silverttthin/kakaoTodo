-- src/main/resources/data.sql

-- 1) users 테이블이 비어 있을 때만 기본 유저 2명 삽입
INSERT INTO users (name, email, created_at, updated_at)
SELECT '이시웅', 'silverttthin@naver.com', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM users LIMIT 1)
UNION ALL
SELECT '카카오', 'kakao@kakao.com', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM users LIMIT 1)
;

-- 2) todos 테이블이 비어 있을 때만 기본 할 일 10건 삽입
INSERT INTO todos (user_id, content, password, created_at, updated_at)
SELECT t.user_id, t.content, t.password, t.created_at, t.updated_at
FROM (
         SELECT 1 AS user_id, '국경이랑 저녁먹기'                 AS content, '1' AS password, NOW() AS created_at, NOW() AS updated_at
         UNION ALL
         SELECT 1,               'glue 개발',                     '1', NOW(), NOW()
         UNION ALL
         SELECT 1,               '스프링부트 공부하기',            '1', NOW(), NOW()
         UNION ALL
         SELECT 1,               'MySQL 데이터베이스 설계',       '1', NOW(), NOW()
         UNION ALL
         SELECT 1,               'API 문서 작성하기',            '1', NOW(), NOW()
         UNION ALL
         SELECT 2,               'kakao의 첫 번째 할 일',         '1', NOW(), NOW()
         UNION ALL
         SELECT 2,               'kakao의 두 번째 할 일',         '1', NOW(), NOW()
         UNION ALL
         SELECT 2,               '회의 자료 준비하기',            '1', NOW(), NOW()
         UNION ALL
         SELECT 2,               '프로젝트 일정 점검',            '1', NOW(), NOW()
         UNION ALL
         SELECT 2,               '팀원들과 코드 리뷰',            '1', NOW(), NOW()
     ) AS t
WHERE NOT EXISTS (SELECT 1 FROM todos LIMIT 1)
;