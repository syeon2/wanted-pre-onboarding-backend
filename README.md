## 🧑🏻‍💻 wanated_pre_onboarding_backend 사전 과제

---

#### 🟤 지원자 이름 및 사용 기술

- 이름 : 김수연
- Application: Java 11, Spring Boot 2.7.14
- Database: MySQL 8.0

----

#### 🟤 애플리케이션 실행 방법

1. root 디렉토리에 접근합니다.
2. `./gradlew bootjar` 명령어 입력하여 Java 파일을 빌드합니다. <br />
3. `docker-compose up -d` 명령어를 입력하여 docker 컨테이너를 생성합니다.
4. `http://localhost:8080/**` 로 로컬환경에서 각 API를 호출할 수 있습니다.
5. docker-compose 환경에서 test 코드도 실행 가능합니다.

---

#### 🟤 데이터베이스 테이블 구조

<img src="https://i.ibb.co/q7HNvtr/Screenshot-2023-08-07-at-4-08-17-PM.png" width="800" />

----

#### 🟤 구현한 API의 동작을 촬영한 데모 영상 링크

<a href="https://fascinated-beechnut-581.notion.site/Wanted-Pre-Onboarding-Backend-887d28cf072d41399f4c8345ebc2f127?pvs=4" target="_blank">
구현한 API의 동작을 촬영한 데모 영상 링크입니다.
</a>

----

#### 🟤 구현 방법 및 이유에 대한 간략한 설명

1. Layer별로 사용하는 DTO를 다르게 만들어 사용합니다.

- 각 Layer별로 DTO가 가지는 역할을 분담하여 하나의 DTO 클래스가 가지는 역할을 분명하게 합니다.
    - Presentation Layer DTO : 클라이언트 RequestBody에 대한 Validation 역할 담당
    - Service Layer DTO : Presentation DTO를 가공하여 비즈니스 로직을 수행 및 여러 Repository에 전달할 수 있도록 데이터를 가공합니다.
    - Persistence Layer DTO : 각 테이블에 Mapping되어 데이터를 저장하는 Entity

2. Member domain

- JWT에 대한 토큰은 클라이언트에게만 Header로 함께 보내고 서버에는 별도로 저장하지 않습니다. (JWT의 기술 의도)
- JWT 토큰이 Header에 있는지, 유효한 토큰 값인지 확인하는 Argument Resolver를 구현하였습니다.
- JWT 토큰에는 회원 ID, 회원 EMAIL, EXPIRED Time이 hashing되어 있습니다. 이 회원 ID를 사용하여 수정하거나 삭제할 포스팅이 자신의 포스팅인지 확인합니다.
    - (Request Body로 회원 ID를 체크하게 되면 조작될 가능성이 높기 때문입니다.)

3. Post domain

- 초기 모델링은 제목, 조회 수 등 게시글 리스트에서 볼 수 있는 필드 값들을 포함한 Post 테이블과 게시글 세부 내용에서 볼 수 있는 게시글 내용을 별도로 모델링하였습니다.
    - Post 테이블의 필드가 적어 테이블을 나누면 오히려 오버 엔지니어링이라고 판단했습니다.
    - 불필요한 트랜잭션, 조인 등으로 인한 데이터베이스 오버헤드를 최소화시키고자 했습니다.

4. Test code

- 각 Layer별로 테스트 코드를 작성하였습니다.
    - 추후 코드를 수정할 경우에 수정한 코드를 의존하고 있는 다른 코드 또한 안전하게 점검할 수 있는 장점이 있습니다.
    - 초기에는 테스트 코드를 작성하는 시간이 걸리는 단점이 있지만, 유지보수 및 서비스를 확장할 때 빠르게 작업할 수 있는 장점 또한 가지고 있습니다.

----

#### 🟤 API 명세 (request/response) 포함

<a href="https://documenter.getpostman.com/view/19045900/2s9XxwutWN" target="_blank">
API 명세 링크입니다.
</a>

----

#### 🟤 AWS 배포

- AWS EC2를 사용하여 배포하였습니다.

`http://ec2-13-209-26-239.ap-northeast-2.compute.amazonaws.com:8080/**` 로 과제로 구현된 API를 호출할 수 있습니다.

- AWS 구조 설계 <br />
  <img src="https://i.ibb.co/FVXD7QF/wanted-architecture.png" width="800" />

---

#### 🟤 API 요구사항

- [X] 과제 1. 사용자 회원가입 엔드포인트
    - [X] 이메일과 비밀번호로 회원가입할 수 있는 엔드포인트를 구현
    -[X] 이메일과 비밀번호에 대한 유효성 검사를 구현


- [X] 과제 2. 사용자 로그인 엔드포인트
    - [X] 사용자가 올바른 이메일과 비밀번호를 제공하면, 사용자 인증을 거친 후에 JWT(JSON Web Token)를 생성하여 사용자에게 반환
    - [X] 회원가입 엔드포인트에 이메일과 비밀번호의 유효성 검사기능을 구현


- [X] 과제 3. 새로운 게시글을 생성하는 엔드포인트


- [X] 과제 4. 게시글 목록을 조회하는 엔드포인트
    - [X] Pagination 기능을 구현


- [X] 과제 5. 특정 게시글을 조회하는 엔드포인트
    - [X] 게시글의 ID를 받아 해당 게시글을 조회하는 엔드포인트를 구현


- [X] 과제 6. 특정 게시글을 수정하는 엔드포인트
    - [X] 게시글의 ID와 수정 내용을 받아 해당 게시글을 수정하는 엔드포인트를 구현
    - [X] 게시글을 수정할 수 있는 사용자는 게시글 작성자만 가능


- [X] 과제 7. 특정 게시글을 삭제하는 엔드포인트
    - [X] 게시글의 ID를 받아 해당 게시글을 삭제하는 엔드포인트를 구현
    - [X] 게시글을 삭제할 수 있는 사용자는 게시글 작성자만 가능


- [X] 추가 기능1 : 단위 또는 통합테스트 구현
- [X] 추가 기능2 : docker compose를 이용하여 애플리케이션 환경을 구성
- [X] 추가 기능3 : 클라우드 환경(AWS)에 배포 환경을 설계하고 애플리케이션을 배포
