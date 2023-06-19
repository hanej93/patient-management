# 환자 관리 프로젝트
## 요구사항

### 환자 등록
환자 정보를 등록합니다.
환자등록번호는 병원별로 중복되지 않도록 서버에서 생성

### 환자 수정
환자 정보를 수정

### 환자 삭제
환자 정보를 삭제

### 환자 조회
환자id를 이용해 한 환자의 정보를 조회합니다. 환자 Entity 의 모든 속성과 내원 정보를 목록으로 함께 조회

### 환자 목록 조회
- 전체 환자 목록을 조회
- 환자이름, 환자등록번호, 생년월일 로 환자를 검색
- ageSize (한 번에 조회하는 최대 항목 수), pageNo (1부터 시작, 페이지 번호)를 요청 인자로 전달받아서 페이징

## 의존성
 ※ 최신 라이브러리를 사용하여 개발하였습니다. 주요 의존성은 다음과 같습니다:
 - jdk 17, springboot 3.1.0
 - spring web
 - spring data jpa
 - spring rest docs
 - spring dev tools
 - validation
 - querydsl
 - h2 databases
 - lombok
 - javafaker
 - thymeleaf
 - bootstrap/jquery

## 프로젝트 설정
프로젝트는 시작 시 MockController와 MockService를 통해 더미 데이터를 생성하도록 설정되어 있습니다. 
현재 'prod' 프로파일 외에서만 이 기능이 활성화되어 있습니다. 
협업 환경에서는 'local' 프로파일로 수정하거나 사용하지 않도록 수정해야 합니다.

## API 문서

API 문서는 Spring REST Docs를 사용하여 생성됩니다. 모든 ControllerTests가 성공적으로 통과하면 이 문서가 생성됩니다.
`src/main/resources/static/docs/index.html`

## 화면
### 환자 조회 검색
![image](https://github.com/hanej93/patient-management/assets/56663420/12fedeef-9369-467d-8bf7-fce35965011b)
### 신규 환자 등록
![image](https://github.com/hanej93/patient-management/assets/56663420/5365fd8c-b693-40e6-b740-a41d3f6fb7df)

※ 추후 수정 삭제에 대한 기능도 추가 예정입니다.


