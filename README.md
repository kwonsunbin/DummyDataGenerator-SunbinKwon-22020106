# DummyDataGenerator

Todo List 기반의 테스트용 더미 데이터를 생성하고 JSON 파일로 저장하는 도구입니다.

## 기술 스택

- Java 21
- Gradle 9.3 (Kotlin DSL)
- Gson 2.11.0
- JUnit Jupiter 6.0

## 프로젝트 구조

```
src/
├── main/java/org/example/
│   ├── Main.java                          # 진입점
│   ├── model/
│   │   ├── TodoItem.java                  # Builder 패턴 모델
│   │   ├── TodoStatus.java                # PENDING / IN_PROGRESS / DONE / CANCELLED
│   │   └── TodoPriority.java              # LOW / MEDIUM / HIGH / URGENT
│   ├── generator/
│   │   └── TodoDummyDataGenerator.java    # 랜덤 더미 데이터 생성 (seed 지원)
│   └── repository/
│       ├── JsonRepository.java            # 저장소 인터페이스
│       └── TodoJsonRepository.java        # JSON 파일 기반 구현체
└── test/java/org/example/
    ├── generator/TodoDummyDataGeneratorTest.java
    └── repository/TodoJsonRepositoryTest.java
```

## 빌드 및 실행

```bash
# 빌드
./gradlew build

# 실행 (기본값: 10개, output/todos.json)
./gradlew run

# 개수와 출력 경로 지정
./gradlew run --args="<count> <output-path>"

# 예시: 20개 생성, data/todos.json 저장
./gradlew run --args="20 data/todos.json"
```

## 생성 데이터 예시

```json
[
  {
    "id": "todo-8402c2ab",
    "title": "Migrate to PostgreSQL",
    "description": "Performance impact must be measured before merging.",
    "status": "CANCELLED",
    "priority": "HIGH",
    "createdAt": "2026-04-28",
    "dueDate": "2026-05-16",
    "tags": ["backend", "database"],
    "assignee": "alice"
  }
]
```

## TodoItem 필드

| 필드 | 타입 | 설명 |
|------|------|------|
| `id` | String | `todo-{hex8}` 형식의 고유 ID |
| `title` | String | 할 일 제목 |
| `description` | String | 상세 설명 |
| `status` | TodoStatus | `PENDING` / `IN_PROGRESS` / `DONE` / `CANCELLED` |
| `priority` | TodoPriority | `LOW` / `MEDIUM` / `HIGH` / `URGENT` |
| `createdAt` | String | 생성일 (`yyyy-MM-dd`) |
| `dueDate` | String | 마감일 (`yyyy-MM-dd`) |
| `tags` | List\<String\> | 태그 목록 (1~3개) |
| `assignee` | String | 담당자 |

## JSON Repository

`TodoJsonRepository`는 `JsonRepository<T>` 인터페이스를 구현하며 다음 메서드를 제공합니다.

| 메서드 | 설명 |
|--------|------|
| `save(item)` | 단일 항목 추가 저장 |
| `saveAll(items)` | 다수 항목 추가 저장 |
| `findAll()` | 전체 조회 |
| `clear()` | 전체 삭제 |

## 테스트

```bash
./gradlew test
```

`TodoDummyDataGeneratorTest` — 생성 개수, ID 유일성, 날짜 순서, seed 재현성 검증  
`TodoJsonRepositoryTest` — 저장/조회/추가/삭제, 전체 필드 직렬화 검증
