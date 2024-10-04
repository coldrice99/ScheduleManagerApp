### 📝 **일정 관리 프로젝트**

**프로젝트 개요**  
이 프로젝트는 **Spring Boot**와 **MySQL**을 활용하여 구현된 간단한 **일정 관리 시스템**이다. 사용자는 일정 생성, 조회, 수정, 삭제 기능을 이용할 수 있으며, 일정 수정 및 삭제 시 비밀번호 검증 기능이 포함되어 있다. 각 일정에는 작성자 이름, 내용, 비밀번호, 수정일 정보가 포함된다.

---

### **1. 프로젝트 구조**

프로젝트는 크게 세 가지 주요 레이어로 구성되어 있다:

- **Controller**: 클라이언트의 요청을 받아 처리하고 응답을 보낸다.
- **Entity**: 데이터베이스 테이블을 Java 객체로 매핑한다.
- **DTO (Data Transfer Object)**: 데이터 전송을 위한 객체로, 데이터베이스와 직접 상호작용하지 않고 데이터 전송에 사용된다.

---

### **2. 주요 기능**

1. **일정 생성**:  
   - 사용자는 이름, 비밀번호, 내용 등의 정보를 입력하여 새로운 일정을 생성할 수 있으며, 생성 시 자동으로 현재 시간이 기록된다.
   
2. **전체 일정 조회**:  
   - 사용자는 생성된 모든 일정을 조회할 수 있으며, 최신 수정일 기준으로 정렬된다.
   
3. **특정 일정 조회**:  
   - 사용자는 일정 ID를 통해 특정 일정을 조회할 수 있다.
   
4. **일정 수정**:  
   - 사용자는 올바른 비밀번호를 입력하면 일정의 내용을 수정할 수 있다.
   
5. **일정 삭제**:  
   - 사용자는 올바른 비밀번호를 입력하면 일정을 삭제할 수 있다.

---

### **3. 프로젝트 구조 (폴더 구성)**

```bash
src
├── main
│   ├── java
│   │   └── com
│   │       └── sparta
│   │           └── schedulemanagment
│   │               ├── controller
│   │               │   └── ScheduleController.java
│   │               ├── dto
│   │               │   ├── ScheduleRequestDto.java
│   │               │   └── ScheduleResponseDto.java
│   │               └── entity
│   │                   └── Schedule.java
│   └── resources
│       ├── application.properties
└── test
    └── java
        └── com
            └── sparta
                └── schedulemanagment
                    └── ScheduleManagmentApplicationTests.java
```

---

### **4. API 명세서**

| Method | Endpoint              | 설명                  | Request Body                            | Response                        |
|--------|------------------------|------------------------|-----------------------------------------|----------------------------------|
| POST   | `/api/schedules`       | 새로운 일정 생성       | `{userName, password, contents}`        | 생성된 일정의 세부 정보           |
| GET    | `/api/schedules`       | 모든 일정 조회         | 없음                                     | 모든 일정 목록                   |
| GET    | `/api/schedules/{id}`  | 특정 일정 조회         | 없음                                     | 해당 일정의 세부 정보             |
| PUT    | `/api/schedules/{id}`  | 일정 수정              | `{userName, contents, password}`        | 수정된 일정의 ID                  |
| DELETE | `/api/schedules/{id}`  | 일정 삭제              | `{password}`                            | 삭제된 일정의 ID                  |

---

### **5. 데이터베이스 스키마**

| 컬럼명        | 데이터 타입      | 설명                                   |
|---------------|-----------------|---------------------------------------|
| `id`          | BIGINT          | Primary key, 자동 증가                |
| `userName`    | VARCHAR(100)    | 일정 작성자의 이름                    |
| `password`    | VARCHAR(100)    | 일정 수정 및 삭제 시 필요한 비밀번호   |
| `contents`    | TEXT            | 일정의 내용                           |
| `updateDate`  | TIMESTAMP       | 일정이 생성 또는 수정된 날짜와 시간    |

---

### **6. 프로젝트를 통해 배운 것**

- **Spring Boot**를 이용한 RESTful API 설계 방법
- **JDBC**를 이용한 데이터베이스 연동
- **Postman**을 활용한 API 테스트
- 비밀번호 검증을 통한 일정 삭제 및 수정 기능 구현
- 날짜 및 시간을 다루기 위한 **LocalDateTime** 처리 방법

프로젝트를 통해 RESTful API를 구현하고, 데이터베이스와의 연동 및 클라이언트와 서버 간의 상호작용에 대한 이해도를 높일 수 있었다.
