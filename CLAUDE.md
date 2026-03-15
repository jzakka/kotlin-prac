# Kotlin Practice - Go 개발자를 위한 코틀린 학습

## 학습 방식

- **학습자**: Go 개발자 → Kotlin 전환/확장 학습
- **코드 작성**: 학습자가 직접 작성
- **Claude 역할**: 예제 코드 제공 + Go와의 비교 설명으로 개념 이해 지원
- **코드 위치**: `src/` 디렉토리 하위에 챕터별 패키지로 구성

## 커리큘럼

### Phase 1: 기초 문법 (Go와 1:1 대응되는 개념)

#### CH01. 변수와 타입
- `val` / `var` vs Go의 `:=` / `var`
- 기본 타입: Int, Long, Double, String, Boolean (Go의 int, int64, float64, string, bool)
- 타입 추론 (Go와 동일하게 지원)
- Nullable 타입 `?` (Go의 포인터/zero value와 비교)
- String template `"$name"` vs Go의 `fmt.Sprintf`

#### CH02. 함수
- `fun` 키워드 vs Go의 `func`
- 단일 표현식 함수 `fun add(a: Int, b: Int) = a + b`
- Named arguments & Default parameters (Go에는 없는 기능)
- 다중 반환: `Pair`, `Triple` vs Go의 다중 반환값

#### CH03. 제어 흐름
- `if`는 표현식 (값을 반환) vs Go의 if문
- `when` 표현식 vs Go의 `switch`
- `for` 루프와 ranges (`1..10`, `until`, `step`) vs Go의 `for`
- `while`, `do-while` (Go는 `for`로 통합)

#### CH04. 컬렉션
- `List`, `Set`, `Map` vs Go의 slice, map
- Mutable vs Immutable 컬렉션 (Go에는 없는 구분)
- 컬렉션 함수: `filter`, `map`, `reduce`, `groupBy` 등
- Sequence (lazy evaluation) vs Go의 채널 기반 파이프라인

### Phase 2: 객체지향 (Go에서 확장되는 개념)

#### CH05. 클래스와 객체
- `class`, `data class` vs Go의 `struct`
- 생성자 (primary / secondary) vs Go의 팩토리 함수
- Properties (getter/setter 자동 생성) vs Go의 필드 직접 접근
- `companion object` vs Go의 패키지 레벨 함수

#### CH06. 상속과 인터페이스
- `open class`, `abstract class` vs Go의 struct embedding
- `interface` vs Go의 interface (암시적 구현 vs 명시적 구현)
- `override` 키워드
- 다중 인터페이스 구현

#### CH07. 특수 클래스
- `enum class` vs Go의 iota 패턴
- `sealed class` / `sealed interface` (Go에 대응 없음 - 패턴매칭과 연결)
- `object` (싱글턴) vs Go의 패키지 레벨 변수 + sync.Once
- `value class` (inline class)

### Phase 3: 코틀린 고유 기능 (Go에 없는 개념)

#### CH08. Null Safety 심화
- Safe call `?.` / Elvis 연산자 `?:`
- Non-null assertion `!!`
- `let`, `run`, `also`, `apply`, `with` 스코프 함수
- Go의 `if err != nil` 패턴과 비교

#### CH09. 확장 함수와 프로퍼티
- 기존 클래스에 함수 추가 (Go에서는 불가능한 기능)
- 확장 프로퍼티
- 수신 객체 지정 람다

#### CH10. 람다와 고차 함수
- 람다 표현식 `{ x: Int -> x * 2 }` vs Go의 `func(x int) int { return x * 2 }`
- 고차 함수 vs Go의 함수를 인자로 전달
- `inline` 함수 (성능 최적화)
- 클로저 동작 비교

#### CH11. 제네릭
- 기본 제네릭 `<T>` vs Go의 제네릭 `[T any]`
- `in` / `out` 변성 (Go에는 없음)
- 타입 제약 `where` vs Go의 타입 제약
- `reified` 타입 파라미터

### Phase 4: 비동기와 동시성 (Go의 강점 영역과 비교)

#### CH12. 코루틴 기초
- `suspend fun` vs Go의 goroutine
- `launch`, `async/await` vs Go의 `go` 키워드
- `CoroutineScope` vs Go의 `context.Context`
- 구조화된 동시성 vs Go의 `sync.WaitGroup`

#### CH13. 코루틴 심화
- `Channel` vs Go의 `chan`
- `Flow` vs Go의 채널 파이프라인
- `select` vs Go의 `select`
- 에러 처리: `SupervisorJob` vs Go의 `errgroup`

### Phase 5: 실전 패턴

#### CH14. 에러 처리
- `try-catch-finally` vs Go의 `error` 반환
- `Result` 타입 vs Go의 `(value, error)` 패턴
- `runCatching` 패턴
- 커스텀 예외 vs Go의 커스텀 error 타입

#### CH15. DSL과 빌더 패턴
- Type-safe builder 패턴
- 수신 객체 지정 람다 활용
- Go에서는 Functional Options 패턴으로 유사하게 구현

#### CH16. 테스트
- JUnit5 + Kotlin
- `kotest` 프레임워크
- Go의 `testing` 패키지와 비교
- 테스트 픽스처와 모킹

## 프로젝트 구조

```
src/
├── ch01_variables/
├── ch02_functions/
├── ch03_control_flow/
├── ch04_collections/
├── ch05_classes/
├── ch06_inheritance/
├── ch07_special_classes/
├── ch08_null_safety/
├── ch09_extensions/
├── ch10_lambdas/
├── ch11_generics/
├── ch12_coroutines_basic/
├── ch13_coroutines_advanced/
├── ch14_error_handling/
├── ch15_dsl/
└── ch16_testing/
```

## 참고

- Kotlin 공식 문서: https://kotlinlang.org/docs/home.html
- Kotlin Koans (연습문제): https://kotlinlang.org/docs/koans.html
- Kotlin Playground: https://play.kotlinlang.org/
