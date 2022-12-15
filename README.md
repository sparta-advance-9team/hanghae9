# My_blog
나만의 블로그 백엔드 서버 만들기
프로젝트 정보 : [LINK](https://teamsparta.notion.site/9-e7f813a42304461ca494da6a84cde546)

## 개발기간
2022년 12월 9일 ~ 2022년 12월 15일

## 팀원
-유종열(팀장)
-김규민
-장영주(개발팀장)
-김현주
-방승섭

## Tech Stacks
-Spring Boot
-Spring Data JPA
-Spring Security + JWT

## API
API&테스트케이스 [LINK](https://docs.google.com/spreadsheets/d/16m9RGblVgYA5vwvS9IT523fcfl0eii1AEGjc0mcoAHM/edit?usp=sharing)



## Why: 과제 제출시에는 아래 질문을 고민해보고 답변을 함께 제출해주세요.

## 1.Spring Security를 적용했을 때 어떤 점이 도움이 되셨나요?
-보안과 관련하여 체계적으로 많은 옵션을 제공하여 편리하게 사용 가능
Filter 기반으로 동작하여 MVC와 분리하여 관리 및 동작
어노테이션을 통한 간단한 설정
인증관리자(Authentication Manager : 누구인지?)와 접근 결정 관리자(Access Decision Manager : 어떤 권한이 있는지?)를 통해 사용자의 리소스 접근을 관리
인증관리자는 UsernamePasswordAuthenticationFilter, 접근 결정 권리자는 FilterSecurityInterceptor가 수행




## 2.Spring Security를 사용하지 않는다면 어떻게 인증/인가를 효율적으로 처리할 수 있을까요?
- 다른 쿠키, 세션, 토큰 형식으로 처리하면 된다


## 3.AOP에 대해 설명해 주세요!
-핵심적인 여러 기능에 동일하게 반복적으로 추가되어야 하는 코드를 부가기능이라고 한다. 이때 반복적으로 쓰이는 부가기능 코드를 따로 분리해서 모듈화하여 부가기능 중심으로 설계, 구현이 가능하다. 이런식으로 각 기능의 관점(관심)에 따른 프로그래밍을 AOP라고 한다. AOP방식을 통해 핵심기능과 부가기능을 각각 집중하여 설계가 가능해진다.



## 4.RefreshToken 적용에 대한 장/단점을 작성해 주세요! 적용해 보지 않으셨다면 JWT를 사용하여 인증/인가를 구현 했을 때의 장/단점에 대해 숙련주차의 답변을 Upgrade 하여 작성해 주세요!
-장점
단순히 Access Token만 사용했을 때보다 JWT Token 탈취에 대한 안정성을 높일 수 있습니다.
Refresh Token 유효 기간을 길게 하여 사용자에게 기존과 같은 연결성을 제공하고 Access Token 유효 기간을 짧게하여 Token 탈취에 대한 안정성을 높일 수 있습니다.

단점
구현이 복잡합니다. 프론트엔드, 서버 모두 검증 프로세스가 길고 디버깅하기가 어렵기 때문에 구현이 어렵습니다.
Access Token이 만료될 때마다 새롭게 발급하는 과정에서 생기는 HTTP 요청 횟수가 많습니다. 이는 서버의 자원 낭비로 귀결됩니다.


## 5.즉시로딩 / 지연로딩에 대해 설명해 주세요!
즉시로딩
- fetch 타입 : EAGER
- 데이터를 조회할 때, 연관된 모듣 객체의 데이터까지 한번에 불러오는 것
- 클래스를 조회할 경우, 연관된 객체를 조회하는 쿼리문까지 생성
- 연관된 엔티티를 즉시 조회 (프록시 객체를 만들지 않고 실제 객체를 바로 사용)

지연로딩
- fetch 타입 : LAZY
- 필요한 시점에 연관된 객체의 데이터를 불러오는 것
- 클래스 조회 시, 연관된 객체는 지금 당장 필요하지 않기 때문에 클래스만 조회하는 쿼리로 생성
- 연관된 엔티티를 프록시로 조회

