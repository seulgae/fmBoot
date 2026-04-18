# FM Manager

풋살 팀 관리, 구장 예약, 결제, 공지, 커뮤니티 기능을 제공하는 Spring Boot 기반 웹 애플리케이션입니다.

기존 JSP/Model 1 형태의 프로젝트를 Spring Boot + Thymeleaf 구조로 이전한 저장소이며, 현재 로컬 기본 실행 환경은 `H2` 메모리 DB 기준으로 맞춰져 있습니다. 운영/이관용 Oracle 관련 자료는 별도 문서로 분리되어 있습니다.

## 개요

- 프로젝트명: `FM Manager`
- Group / Artifact: `com.ucamp:fm`
- 버전: `0.0.1-SNAPSHOT`
- Java: `17`
- Spring Boot: `2.7.18` (2.7.x 최종 안정 릴리즈, Java 17 호환)
- 빌드 도구: `Maven Wrapper`
- 렌더링 방식: `Thymeleaf` + `Thymeleaf Layout Dialect`
- 데이터 접근: `MyBatis`
- 보안/인증: `Spring Security` (BCrypt + 레거시 평문 호환 인코더)
- 웹/앱 대응: `PWA` (installable, offline shell)
- 부가 기능: 메일 발송, 아임포트 결제 연동

## 주요 기능

- 회원가입, 로그인, 아이디/비밀번호 찾기
- 마이페이지 및 회원 정보 수정
- 풋살팀 생성, 조회, 멤버 관리, 경기 기록 관리
- 구장 등록, 수정, 조회, 예약
- 결제 연동
- 공지사항 관리
- 블로그/댓글 기반 커뮤니티
- 관리자 페이지

## 기술 스택

- Java 17
- Spring Boot 2.7.18
- Spring Web
- Spring Security
- Spring Mail
- Thymeleaf
- Thymeleaf Layout Dialect
- MyBatis Spring Boot Starter 2.3.2
- H2 Database (로컬 기본)
- Oracle JDBC (운영 전환용, 현재 pom.xml 에는 주석 처리됨)
- Iamport REST Client
- Pretendard Variable 폰트 (CDN)

## 현재 기본 실행 기준

기준 설정 파일: [application.properties](/C:/Dev/WorkSpace/fmBoot/src/main/resources/application.properties)

현재 저장소 기본값은 로컬 개발 편의를 위한 `H2 메모리 DB` 기준입니다.

- 애플리케이션 포트: `8080`
- 로컬 접속 주소: `http://localhost:8080`
- 기본 DB: `jdbc:h2:mem:fmdb;MODE=Oracle;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
- H2 콘솔: `http://localhost:8080/h2-console`
- H2 콘솔 경로: `/h2-console`
- MyBatis alias 패키지: `com.ucamp.fm`
- MyBatis mapper 위치: `classpath*:mapper/*.xml`
- SQL 초기화: `spring.sql.init.mode=always`
- 템플릿 캐시: `false`
- 업로드 최대 크기: `500MB`
- 메일 서버: `Gmail SMTP`

초기화 스크립트:

- 스키마: [schema.sql](/C:/Dev/WorkSpace/fmBoot/src/main/resources/schema.sql)
- 데이터: [data.sql](/C:/Dev/WorkSpace/fmBoot/src/main/resources/data.sql)

## 실행 방법

### 준비 사항

- JDK 17 설치
- `JAVA_HOME` 설정

### 로컬 실행

Windows:

```bash
mvnw.cmd spring-boot:run
```

macOS / Linux:

```bash
./mvnw spring-boot:run
```

### 패키징

Windows:

```bash
mvnw.cmd -DskipTests package
java -jar target/fm-0.0.1-SNAPSHOT.jar
```

macOS / Linux:

```bash
./mvnw -DskipTests package
java -jar target/fm-0.0.1-SNAPSHOT.jar
```

## 테스트 및 빌드

테스트 코드는 많지 않지만 Maven 기본 테스트 태스크는 아래처럼 실행할 수 있습니다.

Windows:

```bash
mvnw.cmd test
```

macOS / Linux:

```bash
./mvnw test
```

참고:

- 현재 CI와 배포 워크플로는 모두 `-DskipTests package` 기준으로 동작합니다.

## 패키지 구조

기준 경로: [src/main/java/com/ucamp/fm](/C:/Dev/WorkSpace/fmBoot/src/main/java/com/ucamp/fm)

- `admin`
- `blog`
- `cmt`
- `common`
  - `config` — Web MVC 설정 (인터셉터 등록)
  - `controller` — 전역 `@ControllerAdvice` (세션 아이디 일괄 주입)
  - `dto`
  - `interceptor` — 로그인 필수 경로 체크 (`LoginMemberInterceptor`)
- `login`
- `mypage`
- `notice`
- `pay`
- `security` — `LegacyAwarePasswordEncoder` (BCrypt + 레거시 평문 호환)
- `team`

대부분의 도메인은 아래 구조를 따릅니다.

- `controller`
- `dto`
- `mapper`
- `service`
- `service/impl`

공통 설정 클래스:

- [FmApplication.java](/C:/Dev/WorkSpace/fmBoot/src/main/java/com/ucamp/fm/FmApplication.java)
- [WebSecurityConfig.java](/C:/Dev/WorkSpace/fmBoot/src/main/java/com/ucamp/fm/WebSecurityConfig.java)
- [AsyncConfig.java](/C:/Dev/WorkSpace/fmBoot/src/main/java/com/ucamp/fm/AsyncConfig.java)

## 화면/리소스 구조

### 템플릿

기준 경로: [src/main/resources/templates](/C:/Dev/WorkSpace/fmBoot/src/main/resources/templates)

- `layout` — **공통 레이아웃** (`default_layout`, `popup_layout`)
  모든 페이지는 `layout:decorate="~{layout/default_layout}"` 로 상속되며, 본문은 `layout:fragment="content"` 로 주입됩니다. 팝업 화면(window.open 대상)은 `popup_layout` 을 사용합니다.
- `fragments` — `header`, `footer`, `config` (meta/manifest/script 주입)
- `common` — `styles.html`, `scripts.html` (공통 CSS/JS 로드 포인트)
- `admin` / `blog` / `cmt` / `login` / `mypage` / `notice` / `pay` / `team` — 도메인별 페이지 템플릿

### 정적 리소스

기준 경로: [src/main/resources/static](/C:/Dev/WorkSpace/fmBoot/src/main/resources/static)

- `css/app.css` — 디자인 토큰(`:root`), 컴포넌트, 반응형 미디어쿼리(lg/md/sm/xs), `prefers-color-scheme: dark` 다크 모드 포함
- `js/common.js` — 공통 인터랙션(팝업/토글/폼 보조), 서비스 워커 등록
- `js/pages/*.js` — 페이지별 스크립트
- `icons/icon.svg` · `icons/icon-maskable.svg` — PWA 아이콘 (SVG 기반)
- `images/` — 히어로 배너 등 기본 이미지
- `manifest.webmanifest` — PWA 매니페스트
- `service-worker.js` — 오프라인 지원 및 정적 자원 캐시 전략
- `offline.html` — 네트워크 단절 시 폴백 페이지

업로드 경로: [src/main/webapp/uploadImg](/C:/Dev/WorkSpace/fmBoot/src/main/webapp/uploadImg)

## UI / UX 구성

- **폰트**: Pretendard Variable (CDN) → Noto Sans KR → 시스템 폰트 폴백. 한글 가독성을 우선해 제목 weight/line-height/letter-spacing 을 조정.
- **레이아웃 일원화**: 모든 페이지가 `default_layout` / `popup_layout` 에서 head/header/footer/script 슬롯을 주입받아 중복 제거.
- **반응형**: 브레이크포인트 `lg 1080 / md 820 / sm 640 / xs 480`, 820px 이하에서 헤더 햄버거 드로어, 테이블 가로 스크롤, 버튼 세로 스택 등 모바일 최적화.
- **다크 모드**: OS 설정(`prefers-color-scheme`) 을 따라 자동 전환. 브랜드 컬러는 유지하고 배경/표면/텍스트 토큰만 반전.
- **PWA**: Chrome/Edge 등에서 "앱 설치" 가능, 홈 화면에 추가 시 스탠드얼론 실행. 오프라인 시 `offline.html` 표시.
- **언어**: UI 라벨은 한국어를 기본으로 하며, 식별용 브랜드 마크(`FM`)와 숫자 표기는 그대로 유지.

## DB 관련 메모

현재 저장소에는 두 가지 DB 관점의 자료가 공존합니다.

1. 로컬 기본 실행
   `application.properties` 기준으로 H2 메모리 DB를 사용합니다.
2. 운영/이관 참고
   Oracle XE 기반 배포 문서와 스키마 스크립트가 `docs/`, `scripts/db/` 아래에 남아 있습니다.

관련 문서:

- [db-schema.md](/C:/Dev/WorkSpace/fmBoot/docs/db-schema.md)
- [db-ide-connection.md](/C:/Dev/WorkSpace/fmBoot/docs/db-ide-connection.md)
- [create_schema.sql](/C:/Dev/WorkSpace/fmBoot/scripts/db/create_schema.sql)
- [create_schema_ide.sql](/C:/Dev/WorkSpace/fmBoot/scripts/db/create_schema_ide.sql)

## CI/CD

GitHub Actions 워크플로:

- [ci.yml](/C:/Dev/WorkSpace/fmBoot/.github/workflows/ci.yml)
- [deploy.yml](/C:/Dev/WorkSpace/fmBoot/.github/workflows/deploy.yml)

현재 구성:

- CI: `pull_request`, `workflow_dispatch`
- Deploy: `main` 브랜치 push, `workflow_dispatch`
- Build runner: `ubuntu-latest`
- Deploy runner: `self-hosted`, `linux`
- 배포 산출물: `target/fm-0.0.1-SNAPSHOT.jar`

배포 구조 참고 문서:

- [deployment.md](/C:/Dev/WorkSpace/fmBoot/docs/deployment.md)
- [deploy-self-hosted.sh](/C:/Dev/WorkSpace/fmBoot/scripts/deploy-self-hosted.sh)

## 보안 / 정적 분석

- 비밀번호는 `LegacyAwarePasswordEncoder` 를 통해 **BCrypt** 로 저장되며, 마이그레이션 중인 레거시 평문 비밀번호에 대한 호환 비교도 동시에 지원합니다.
- OWASP Dependency-Check 용 suppression: [dependency-check-suppressions.xml](/C:/Dev/WorkSpace/fmBoot/dependency-check-suppressions.xml)
  - `CVE-2025-22235` 는 Spring Boot `EndpointRequest.to()` 관련 이슈로 보고되지만, 본 프로젝트는 `spring-boot-starter-actuator` 미포함 + `EndpointRequest` 미사용으로 실제 경로가 로드되지 않아 Not Exploitable 로 판정되어 suppression 처리되어 있습니다. Spring Boot 3.x 이관 시 제거 예정.

## 주의 사항

- [application.properties](/C:/Dev/WorkSpace/fmBoot/src/main/resources/application.properties) 에 메일 계정 정보가 직접 들어 있습니다.
- Oracle 운영 정보도 문서에 남아 있으므로, 실제 운영 전에는 환경 변수 또는 외부 설정 파일로 분리하는 것이 좋습니다.
- 업로드 경로는 일부 기능에서 서블릿 컨텍스트 기준 실제 경로를 사용하므로 로컬/배포 환경 차이에 주의가 필요합니다.
- Service Worker 는 **HTTPS 또는 `localhost`** 에서만 등록됩니다. HTTP 로 배포된 환경에서는 PWA 기능이 동작하지 않습니다.

## 참고 자료

- 프로젝트 소개 자료: [PPT.pdf](/C:/Dev/WorkSpace/fmBoot/PPT.pdf)
- 프로젝트 영상: `https://youtu.be/Cb8BTgsmOXY`
