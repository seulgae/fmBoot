# FM Manager

풋살 팀 관리, 구장 예약, 결제, 공지사항, 커뮤니티 기능을 제공하는 Spring Boot 기반 웹 애플리케이션입니다.

## 1. 프로젝트 개요

- 프로젝트명: `FM Manager`
- 그룹/아티팩트: `com.ucamp:fm`
- 버전: `0.0.1-SNAPSHOT`
- 실행 방식: Spring Boot 내장 Tomcat
- 렌더링 방식: Thymeleaf 서버사이드 렌더링
- 주요 기능:
  - 회원가입 / 로그인
  - 마이페이지
  - 팀 생성 및 팀 관리
  - 경기 기록 관리
  - 구장 목록 조회 및 예약
  - 결제
  - 공지사항
  - 블로그 / 댓글 커뮤니티

## 2. 기술 스택

- Java 17
- Spring Boot 2.7.5
- Spring Web
- Thymeleaf
- Thymeleaf Layout Dialect
- MyBatis Spring Boot Starter 2.2.2
- Oracle JDBC (`ojdbc11`)
- Spring Security
- Spring Mail
- Iamport REST Client
- Maven Wrapper

## 3. 현재 실행 설정

기준 파일: [application.properties](C:/Dev/WorkSpace/fmBoot/src/main/resources/application.properties)

- 애플리케이션 포트: `8085`
- 로컬 접속 주소: `http://localhost:8085`
- DB URL: `jdbc:oracle:thin:@localhost:1521/XEPDB1`
- DB 사용자: `fm`
- MyBatis alias 패키지: `com.ucamp.fm`
- MyBatis mapper 위치: `classpath*:mapper/*.xml`
- Thymeleaf cache: `false`
- 파일 업로드 최대 크기: `500MB`
- 메일 서버: Gmail SMTP

주의:
- 현재 `application.properties`에 DB 비밀번호, 메일 비밀번호 등 민감 정보가 직접 들어 있습니다.
- 추후에는 환경 변수 또는 별도 외부 설정 파일로 분리하는 것이 좋습니다.

## 4. 실행 방법

### 로컬 실행 전 준비

- JDK 17 설치
- `JAVA_HOME` 설정
- Oracle XE 실행
- `XEPDB1` 및 `fm` 계정 준비

### 실행 명령

```bash
./mvnw spring-boot:run
```

Windows에서는 다음 명령을 사용합니다.

```bash
mvnw.cmd spring-boot:run
```

패키징 후 실행:

```bash
./mvnw -DskipTests package
java -jar target/fm-0.0.1-SNAPSHOT.jar
```

## 5. 현재 패키지 구조

기준 경로: [src/main/java/com/ucamp/fm](C:/Dev/WorkSpace/fmBoot/src/main/java/com/ucamp/fm)

- `admin`
- `blog`
- `cmt`
- `login`
- `mypage`
- `notice`
- `pay`
- `team`

각 도메인 패키지는 대체로 아래 구조를 따릅니다.

- `controller`
- `dto`
- `mapper`
- `service`
- `service/impl`

## 6. 현재 템플릿 구조

기준 경로: [src/main/resources/templates](C:/Dev/WorkSpace/fmBoot/src/main/resources/templates)

- `admin`
- `blog`
- `cmt`
- `common`
- `fragments`
- `layout`
- `login`
- `mypage`
- `notice`
- `pay`
- `team`

정리 원칙:
- 화면 템플릿도 비즈니스 로직 패키지와 같은 도메인 기준으로 맞췄습니다.
- 기존 `member` 폴더에 섞여 있던 화면은 아래처럼 분리했습니다.
  - 로그인/회원 관련 화면 -> `login`
  - 마이페이지 관련 화면 -> `mypage`
- 기존 `adm`, `blogbbs`, `cmtbbs`, `noticebbs`, `placebbs` 구조는 각각 `admin`, `blog`, `cmt`, `notice`, `pay`로 정리했습니다.

## 7. 이번에 반영된 내용

이번 작업 기준으로 반영된 핵심 내용은 다음과 같습니다.

1. 템플릿 디렉터리 구조를 도메인 기준으로 재정리했습니다.
2. 컨트롤러의 뷰 반환 경로를 새 템플릿 위치에 맞게 수정했습니다.
3. `member` 중심의 혼합된 화면 구조를 `login` / `mypage` 도메인으로 분리했습니다.
4. 화면 구조와 Java 패키지 구조를 최대한 동일한 기준으로 맞췄습니다.

수정된 대표 컨트롤러:

- [AdminController.java](C:/Dev/WorkSpace/fmBoot/src/main/java/com/ucamp/fm/admin/controller/AdminController.java)
- [BlogController.java](C:/Dev/WorkSpace/fmBoot/src/main/java/com/ucamp/fm/blog/controller/BlogController.java)
- [CmtController.java](C:/Dev/WorkSpace/fmBoot/src/main/java/com/ucamp/fm/cmt/controller/CmtController.java)
- [LoginController.java](C:/Dev/WorkSpace/fmBoot/src/main/java/com/ucamp/fm/login/controller/LoginController.java)
- [MypageController.java](C:/Dev/WorkSpace/fmBoot/src/main/java/com/ucamp/fm/mypage/controller/MypageController.java)
- [NoticeController.java](C:/Dev/WorkSpace/fmBoot/src/main/java/com/ucamp/fm/notice/controller/NoticeController.java)
- [PayController.java](C:/Dev/WorkSpace/fmBoot/src/main/java/com/ucamp/fm/pay/controller/PayController.java)

## 8. 지금까지의 작업 히스토리

최근 작업 흐름을 기준으로 정리했습니다.

1. 프로젝트 기본 구조를 정리하고 `pom.xml`과 불필요한 배포 파일을 정리했습니다.
2. Java 패키지 구조를 도메인별로 재구성했고 `service/impl` 구조를 정리했습니다.
3. 서버 배포 구조를 `podman + oracle-xe + fm-app + nginx` 기준으로 맞췄습니다.
4. GitHub Actions 기반 CI/배포 흐름을 추가했고, self-hosted runner 중심의 배포 방식으로 정리했습니다.
5. 운영 환경 기준으로 README를 한 차례 갱신했습니다.
6. 화면 템플릿 구조를 비즈니스 패키지 기준으로 다시 정리했습니다.
7. 이번 README에서 현재 설정, 구조, 반영 내용, 다음 작업 항목까지 문서화했습니다.

참고용 최근 커밋 흐름:

- `[길태형]templates 구조를 비즈니스 패키지 기준으로 정리`
- `[길태형]CI, self-hosted runner 기반 배포 구조로 전환하고 AWS 확장 가능하게 정리`
- `[길태형]도메인별 패키지 구조로 재구성하고 service 하위에 impl 정리`
- `[길태형]pom 설정 정리 및 불필요한 배포 파일 삭제`
- `[길태형]서버 배포 구조를 podman + oracle-xe + fm-app + nginx 기준으로 정리`

## 9. 현재까지 내가 한 일

현재 기준으로 이미 되어 있는 일:

- Spring Boot 프로젝트 기본 세팅 완료
- Oracle 연동 설정 완료
- 로그인 / 회원 / 마이페이지 기능 구현
- 팀 관리 기능 구현
- 공지사항 기능 구현
- 블로그 및 댓글 기능 구현
- 구장 목록 / 예약 / 결제 기능 구현
- 배포 구조 정리
- CI / Deploy 워크플로우 추가
- 템플릿 구조를 도메인 기준으로 재정리
- README를 현재 프로젝트 기준으로 정리

## 10. 지금부터 해야 할 일

우선순위 기준으로 정리하면 아래 순서가 적절합니다.

1. 로컬 개발 환경에서 `JAVA_HOME`을 설정하고 실제 빌드가 되는지 확인해야 합니다.
2. 템플릿 이동 이후 전체 메뉴와 화면 진입 경로를 직접 눌러 보면서 뷰 깨짐이 없는지 확인해야 합니다.
3. `application.properties`에 들어 있는 민감 정보를 외부 설정으로 분리해야 합니다.
4. 테스트 코드가 거의 없는 상태라 핵심 기능 단위 테스트 또는 통합 테스트를 추가해야 합니다.
5. 로그인, 권한, 관리자 화면 접근 제어가 실제로 의도대로 동작하는지 다시 점검해야 합니다.
6. 배포 문서와 실제 서버 설정이 완전히 일치하는지 검증해야 합니다.

## 11. 바로 확인해야 하는 체크리스트

- `http://localhost:8085` 접속 확인
- 로그인 페이지 동작 확인
- 마이페이지 화면 이동 확인
- 팀 관리 페이지 이동 확인
- 공지사항 목록/상세 확인
- 블로그 목록/상세/댓글 확인
- 구장 목록/상세/예약/결제 흐름 확인
- 관리자 화면 접근 확인
- 업로드 기능 확인

## 12. 현재 알려진 주의사항

- 현재 환경에서는 `JAVA_HOME`이 없어 Maven 컴파일 검증을 바로 수행하지 못했습니다.
- 템플릿 구조는 정리되었지만 실제 브라우저 기준 화면 점검은 별도로 진행하는 것이 안전합니다.
- 설정 파일에 민감 정보가 포함되어 있으므로 외부화가 필요합니다.
- README의 운영/배포 관련 내용은 현재 커밋 히스토리와 설정 파일 기준으로 정리한 것이며, 실제 서버 상태는 별도 확인이 필요할 수 있습니다.

## 13. 참고 파일

- [pom.xml](C:/Dev/WorkSpace/fmBoot/pom.xml)
- [application.properties](C:/Dev/WorkSpace/fmBoot/src/main/resources/application.properties)
- [PPT.pdf](C:/Dev/WorkSpace/fmBoot/PPT.pdf)
- 프로젝트 영상: `https://youtu.be/Cb8BTgsmOXY`
