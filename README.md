# BE 2차 과제 : 일정 관리 앱 만들기

본 프로젝트는 카카오테크캠퍼스 BE 코스의 1단계 과정 2차 과제입니다.  
SpringBoot + Mysql(docker로 띄움) + **JDBC**를 사용해 일정(todo)을 관리하는 앱입니다.  
 
### Why JDBC why???
<img width="643" alt="image" src="https://github.com/user-attachments/assets/21323108-6e88-4422-a7c9-53f9d529f31d" />

실제로 jdbc를 통해 sql 지역변수들을 많이 작성하며 연습해볼 수 있었으며 덕분에 db값들을 가져오는 것을 직접 구현할 수 있었습니다.  
또한 JPA의 다양한 기능들에 대해 내심 감사함을 느끼는 기회를 가졌습니다.  


### 필수기능과 도전기능
본 프로젝트는 도전기능 포함 모든 기능을 구현하였습니다. 
- 필수기능은 [3번째 커밋](https://github.com/silverttthin/kakaoTodo/commit/62e011b226aad2a73275079479cba99ed078a37e)에 기록을 남겼습니다.
- 나머지 12개의 커밋은 모두 도전기능 사항들을 구현하는 과정의 커밋들입니다.


### ERD

<img width="346" alt="image" src="https://github.com/user-attachments/assets/f7df58fd-82f5-4350-8808-3ea09fc3f80e" />

### API Documentation
---

## 1. todo 생성

**POST** `/todos`  

### Request Body

| 필드       | 타입     | 필수 여부 | 설명                      |
| ---------- | -------- | --------- | ------------------------- |
| `userId`   | Long     | 예        | 작성자 유저 pk        |
| `password` | String   | 예        | 수정·삭제 시 기입할 비밀번호     |
| `content`  | String   | 예        | todo 내용                 |

### Responses

| HTTP 코드      | 설명                     | 응답 바디                   |
| -------------- | ------------------------ | --------------------------- |
| `200 OK`       | 생성 성공                | 생성된 todo의 ID     |
| `400 BAD REQUEST` | 요청 바디 검증 실패    | 오류 메시지 JSON            |



## 2. 단건 조회

**GET** `/todos/{todoId}`

### Path Variables

| 이름     | 타입   | 설명              |
| -------- | ------ | ----------------- |
| `todoId` | Long   | 조회할 todo의 pk  |

### Responses

| HTTP 코드        | 설명                        | 응답 바디             |
| ---------------- | --------------------------- | ---------------------- |
| `200 OK`         | 조회 성공                   | todo 객체 (JSON)       |
| `404 NOT FOUND`  | 해당 ID의 todo가 없을 때    | 오류 메시지 JSON       |

⸻  

## 3. 목록 조회

**GET** `/todos`

### Query Parameters

| 이름     | 타입   | 기본값 | 설명                           |
| -------- | ------ | ------ | ------------------------------ |
| `userId` | Long   | —      | (선택) 특정 유저의 todo만 필터링, null 시 기본 목록 조회 |
| `page`   | int    | 1      | 페이지 번호 (1부터 시작)         |
| `size`   | int    | 2      | 한 페이지당 항목 수(개발상 편의를 위해 디폴트 2개로 지정했음)              |

### Responses

| HTTP 코드  | 설명        | 응답 바디                  |
| ---------- | ----------- | -------------------------- |
| `200 OK`   | 조회 성공   | todo 단건조회를 담은 배열 (JSON)      |

---

## 4. todo 수정

**PUT** `/todos/{todoId}`  

### Path Variables

| 이름      | 타입   | 설명              |
| --------- | ------ | ----------------- |
| `todoId`  | Long   | 수정할 todo의 pk  |

### Request Body

| 필드       | 타입     | 필수 여부 | 설명                  |
| ---------- | -------- | --------- | --------------------- |
| `userId`   | Long     | 예        | 작성자 유저 pk        |
| `password` | String   | 예        |  todo 작성시 기입한 비밀번호        |
| `content`  | String   | 예        | 업데이트할 todo 내용   |

### Responses

| HTTP 코드        | 설명                          | 응답 바디                |
| ---------------- | ----------------------------- | ------------------------- |
| `200 OK`         | 수정 성공                     | 수정된 todo의 단건조회 dto (JSON)   |
| `400 BAD REQUEST`| 비밀번호 불일치 등 잘못된 요청 | 오류 메시지 JSON          |
| `404 NOT FOUND`  | 해당 ID의 todo가 없을 때       | 오류 메시지 JSON          |

---

## 5. todo 삭제

**DELETE** `/todos/{todoId}`  

### Path Variables

| 이름      | 타입   | 설명              |
| --------- | ------ | ----------------- |
| `todoId`  | Long   | 삭제할 todo의 pk  |

### Request Body

| 필드       | 타입     | 필수 여부 | 설명               |
| ---------- | -------- | --------- | ------------------ |
| `userId`   | Long     | 예        | 작성자 유저 pk      |
| `password` | String   | 예        | todo 작성시 기입한 비밀번호     |

### Responses

| HTTP 코드        | 설명                          | 응답 바디          |
| ---------------- | ----------------------------- | ------------------- |
| `200 OK`         | 삭제 성공                     | (빈 바디)           |
| `400 BAD REQUEST`| 비밀번호 불일치 등 잘못된 요청 | 오류 메시지 JSON    |
| `404 NOT FOUND`  | 해당 ID의 todo가 없을 때       | 오류 메시지 JSON    |





