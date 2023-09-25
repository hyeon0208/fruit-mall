# 프룻 프룻 쇼핑몰 - 회원 서버 🍎
프룻 프룻 쇼핑몰 회원 서버는 판매자가 등록한 계절별 과일들 중 원하는 과일을 구매할 수 있는 웹 서비스 입니다.
<br>

---
## 📚 목차
1. [사용 기술 스택](#1-사용-기술-스택)

2. [프로젝트 주요 기능](#2-프로젝트-주요-기능)

3. [ERD 설계도](#3-erd)

4. [기능 별 화면 및 소개](#-4-기능-별-화면-및-소개)

---
## 1️⃣ 사용 기술 스택


<p align="center">
📌 BackEnd
<br>
<br>
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Security-00AB77?style=flat-square&logo=springsecurity&logoColor=white"/>
<img src="https://img.shields.io/badge/java 11-007396?style=flat-square&logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=Gradle&logoColor=white"/>
<img src="https://img.shields.io/badge/log4j2-BEFCFF?style=flat-square&logo=&logoColor=white"/>

<br>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>
<img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white"/>
<img src="https://img.shields.io/badge/MyBatis-9EB0A2?style=flat-square&logo=&logoColor=white"/>
<img src="https://img.shields.io/badge/Kafka-231F20?style=flat-square&logo=apachekafka&logoColor=white"/>
<br>
<br>
</p>

<p align="center">
📌FrontEnd
<br>
<br>
<img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white"/>
<img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white"/>
<img src="https://img.shields.io/badge/jQuery-0769AD?style=flat-square&logo=jQuery&logoColor=white"/>
<br>
<img src="https://img.shields.io/badge/Axios-5A29E4?style=flat-square&logo=axios&logoColor=white"/>
<img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=flat-square&logo=thymeleaf&logoColor=white"/>
<br>
</p>

---
## 2️⃣ 프로젝트 주요 기능

|      기능       |                          설명                           |                                  비고                                   |
|:-------------:|:-----------------------------------------------------:|:---------------------------------------------------------------------:|
| 자체 회원가입 & 로그인 |                  자체 서비스 회원 가입 및 로그인                   |                            이메일, 닉네임 중복 검사.                            |
| 소셜 회원가입 & 로그인 |              카카오, 네이버, 구글을 이용한 소셜 로그인 지원              | 첫 소셜 로그인 시<br/>자체 서비스 회원과의 구별을 위한<br/> 로그인한 해당 소셜 서비스 이름을 추가해 회원가입 처리 |
|    최근 본 상품    |               클릭했던 상품의 이미지를 3개까지 보여준다.                |              Cookie로 구현<br/>해당 상품의 이미지를 클릭하면 상세 페이지로 이동.              |
|     장바구니      |      구매하고자하는 상품을 장바구니에 담아 <br/>해당 상품의 구매할 수량을 조정      |  비회원은 LocalStorage에 저장<br/>회원은 DB에 저장<br/>비회원 로그인시 로그인한 회원 장바구니로 합침   |
|   구매 상품 내역    |                  구매한 상품들의 내역을 보여준다.                   |               원하는 조건별(날짜, 상품이름, 주문번호) 검색, 리뷰 작성, 재구매 가능               |
|    배송지 관리     |                유저의 배송지를 등록, 수정, 삭제 가능.                |                           배송지는 3개 까지 저장 가능.                           |
|   회원 정보 수정    |                 닉네임과 비밀번호를 변경할 수 있다.                  |                              닉네임 중복 검사.                               |
|     상품 상세     |     판매자가 등록한 상품의 정보 및 <br/>상품 상세 설명 이미지와 글을 보여준다.     |             판매 상품의 상세 정보를 확인하고<br/>상품에 대한 리뷰를 확인 및 작성 가능.             |
|     리뷰작성      |                구매한 상품에 대한 리뷰 작성 가능  .                 |                         해당 상품을 구매한 수량만큼 리뷰작성                          |
|     주문 결제     | 구매자가 선택한 상품들을 iamport API를 이용해 신용카드, 카카오페이, 휴대폰 결제 가능 |                    상품 재고 부족 시 자동 결제 취소 (동시성 문제 고려)                    |
|    실시간 알림     |         구매자가 작성한 리뷰에 판매자의 댓글이 달릴 시 실시간 알림 수신          |           Kafka에서 해당 토픽의 메세지를 수신해 SSE 연결된 클라이언트에게 실시간 알림 전송           |
<br>

---
## 3️⃣ ERD
👉🏻 **ERD 링크 바로가기 :** https://www.erdcloud.com/d/N33PiySjCTmbMZxTq
![erd.png](img/erd.png)

---
## ️ ️4️⃣ 기능 별 화면 및 소개

### - 로그인
- 소셜 로그인 (카카오, 네이버, 구글 소셜 로그인 지원)

![ezgif com-video-to-gif444](https://github.com/Hyeon0208/fruit-mall/assets/99153215/bd81e45c-030b-4201-ab56-7d9f252431ea)

<br>

- 일반 로그인

![ezgif com-video-to-gif (1)](https://github.com/Hyeon0208/fruit-mall/assets/99153215/1525c517-c83f-4d83-b035-f65e75c4a88a)
  
<br>

- 소셜 회원가입 이메일과 프룹샷 회원가입 이메일 중복을 방지하기 위해 "로그인방법" 컬럼으로 구별하여 회원 저장.

![user테이블.png](img/user테이블.png)

<br>

### - 회원가입

![ezgif com-video-to-gif](https://github.com/Hyeon0208/fruit-mall/assets/99153215/dbc96a02-4742-43ee-a5b9-9d61d40faacf)

- 이메일, 닉네임, 비밀번호, 약관동의 정보 입력
- 이메일주소와 닉네임은 중복 불가로, 중복 입력 값을 즉시 확인할 수 있도록 해당 필드 비동기로 처리.

<br>

### - 메인화면
- 과일 카테고리 별, 검색 조건 별 페이징

![ezgif com-video-to-gif](https://github.com/Hyeon0208/fruit-mall/assets/99153215/7094a903-2664-437b-8fef-c914b99f3441)

<br>

### - 장바구니
- 비회원
  - LocalStorage에 해당 상품을 저장
  - 주문하기 클릭 시 login 페이지로 redirect
  - 로그인 시 로그인 사용자의 장바구니에 LocalStorage에 담긴 상품 저장 및 LocalStorage clear.

<br>

- 로그인 회원
  - 장바구니 상품 테이블에 해당 상품 저장
  - 주문하기 클릭 시 결제 페이지로 redirect

![ezgif com-video-to-gif (1)](https://github.com/Hyeon0208/fruit-mall/assets/99153215/df15c4e6-0184-4dcd-8edd-49d25ae381b5)

<br>

### - 좋아요
- 비회원
  - 좋아요 기능 비활성화

<br>

- 로그인 회원
  - 좋아요 상품 likes 테이블에 저장하여 저장된 수 만큼 사이드 바에 표시.

![ezgif com-video-to-gif](https://github.com/Hyeon0208/fruit-mall/assets/99153215/155b867b-eb46-417c-8f46-cdc85d3050ff)

<br>

### - 최근 본 상품
- 상품 클릭 순서대로 3개까지 cookie에 상품이미지와 상품ID 저장하여 사이드바에 표시.
- 쿠키 만료일 1일로 설정.
- 최근 본 상품의 이미지 클릭 시 해당 상품의 상세페이지로 이동.

![ezgif com-video-to-gif (1)](https://github.com/Hyeon0208/fruit-mall/assets/99153215/547fa51d-74d0-4dd1-9c96-c984c39bdc9f)

<br>

### - 상품 상세
관리자가 등록한 해당 상품의 정보 표시 (상품명, 할인율, 가격, 재고, 상세정보)

- 비회원
  - 장바구니 추가 클릭 시 선택한 수량만큼 LocalStorage에 해당 상품을 저장
  - 구매하기 클릭 시 로그인 페이지로 redirect
  - 리뷰 열람만 가능

- 로그인 회원
  - 장바구니 추가 클릭 시 선택한 수량만큼 장바구니 상품 테이블에 해당 상품 저장
  - 구매하기 클릭 시 해당 상품 결제 페이지로 redirect
  - 해당 상품의 구매 이력이 있을 경우 리뷰 작성 가능

![ezgif com-video-to-gif (2)](https://github.com/Hyeon0208/fruit-mall/assets/99153215/28403b6e-ec20-428d-8140-9fb2f260d39b)

<br>

### - 상품 주문
- 배송지 입력 및 결제 수단, 약관동의 선택 후 iamport API를 사용한 테스트 결제
- 상품 구매 완료시 구매한 장바구니 상품 제거

![ezgif com-video-to-gif (9)](https://github.com/Hyeon0208/fruit-mall/assets/99153215/f8785ce8-329c-468e-9a03-b3990a5ed264)

<br>

### - 마이페이지

- 구매내역
  - 기간별, 검색 조건 별 구매 상품 내역 확인
  - 재구매 클릭 시 해당 상품 구매수량만큼 장바구니에 저장
  - 리뷰보기 클릭 시 해당 상품의 리뷰 페이지로 이동

![ezgif com-video-to-gif (6)](https://github.com/Hyeon0208/fruit-mall/assets/99153215/0a3116db-61eb-4cc9-a00a-c227ffbb855d)

<br>

- 배송지 설정
  - 배송지는 3개까지 저장
  - 배송지 등록, 수정, 삭제 가능

![ezgif com-video-to-gif (5)](https://github.com/Hyeon0208/fruit-mall/assets/99153215/86d0fd1a-59ee-4dd7-a28b-80197fdce16c)

<br>

- 회원정보 수정
  - 비밀번호 입력으로 현재 계정 인증 
  - 닉네임 및 비밀번호 변경 가능

![ezgif com-video-to-gif (4)](https://github.com/Hyeon0208/fruit-mall/assets/99153215/19f8c8d0-a493-4a59-893a-98e8be775049)

<br>

### - 리뷰 작성

- 상품 상세 페이지
  - 구매 이력이 있을 경우 "리뷰 작성하기" 활성화
  - 구매 이력 중 주문 일이 가장 최신인 주문으로 리뷰 저장
  - 리뷰 수정 가능
  - 해당 리뷰에 댓글이 달릴 시 답글 보기로 답글 표시

<br>

- 마이 페이지
  - 리뷰 작성 시 리뷰보기 버튼 활성화.
  - 리뷰 보기 클릭 시 해당 상품의 리뷰 페이지로 이동

![ezgif com-video-to-gif (3)](https://github.com/Hyeon0208/fruit-mall/assets/99153215/83976c7a-5ac2-4a9e-a89d-7a58256ba04c)

<br>

### - 리뷰 답글 알림
- 판매자가 구매자의 리뷰에 답글 작성 시 실시간 알림 표시
- 해당 알림 아이콘 클릭 시 알림 내역 조회
- 알림 내용 클릭 시 리뷰 작성한 상품의 리뷰 페이지로 redirect

![실시간알림표시.png](img/실시간알림표시.png)

<img src="img/리뷰답글알림.png"  width="600" height="400"/>
