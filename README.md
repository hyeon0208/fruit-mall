# 프룻 프룻 쇼핑몰 🍎

<br>

---
## 🛠 사용 기술 스택


<p align="center">
📌 BackEnd
<br>
<br>
<img src="https://img.shields.io/badge/Spring Boot 2.7.6-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Security-00AB77?style=flat-square&logo=springsecurity&logoColor=white"/>
<img src="https://img.shields.io/badge/java 11-007396?style=flat-square&logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=Gradle&logoColor=white"/>
<img src="https://img.shields.io/badge/log4j2-BEFCFF?style=flat-square&logo=&logoColor=white"/>

<br>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>
<img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white"/>
<img src="https://img.shields.io/badge/Firebase-FFCA28?style=flat-square&logo=firebase&logoColor=white"/>
<img src="https://img.shields.io/badge/MyBatis-9EB0A2?style=flat-square&logo=&logoColor=white"/>
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
<img src="https://img.shields.io/badge/Axios-5A29E4?style=flat-square&logo=axios&logoColor=white"/>
<br>
<img src="https://img.shields.io/badge/Swiper-6332F6?style=flat-square&logo=swiper&logoColor=white"/>
<img src="https://img.shields.io/badge/TinyMCE-2596BE?style=flat-square&logo=TinyMCE&logoColor=white"/>
<img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=flat-square&logo=thymeleaf&logoColor=white"/>
<br>
</p>

---
## 💿 프로젝트 기능

- #### 🙍🏻‍♂️ 유저
|      기능       |                      설명                       |                                 비고                                 |
|:-------------:|:---------------------------------------------:|:------------------------------------------------------------------:|
| 자체 회원가입 & 로그인 |              자체 서비스 회원 가입 및 로그인               |                                                                    |
| 소셜 회원가입 & 로그인 |          카카오, 네이버, 구글을 이용한 소셜 로그인 지원          |  첫 소셜 로그인 시<br/>자체 서비스 회원과의 구별을 위한<br/> 로그인한 해당 소셜 서비스 이름을 추가 저장   |
|    최근 본 상품    |           클릭했던 상품의 이미지를 3개까지 보여준다.            |            Cookie로 구현<br/>해당 상품의 이미지를 클릭하면 상세 페이지로 이동.             |
|     장바구니      |  구매하고자하는 상품을 장바구니에 담아 <br/>해당 상품의 구매할 수량을 조정  | 비회원은 LocalStorage에 저장<br/>회원은 DB에 저장<br/>비회원 로그인시 로그인한 회원 장바구니로 합침 |
|   구매 상품 내역    |              구매한 상품들의 내역을 보여준다.               |             원하는 조건별(날짜, 상품이름, 주문번호) 검색, 리뷰 작성, 재구매 가능              |
|    배송지 관리     |            유저의 배송지를 등록, 수정, 삭제 가능.            |                                                                    |
|   회원 정보 수정    |             닉네임과 비밀번호를 변경할 수 있다.              |                                                                    |
|     상품 상세     | 판매자가 등록한 상품의 정보 및 <br/>상품 상세 설명 이미지와 글을 보여준다. |           판매 상품의 상세 정보를 확인하고<br/>상품에 대한 리뷰를 확인 및 작성 가능.            |
|     리뷰작성      |            구매한 상품에 대한 리뷰 작성 가능  .             |                        해당 상품을 구매한 수량만큼 리뷰작성                        |
|     주문 결제     |       구매자가 선택한 상품들을 iamport API를 이용해 결제       |                  상품 재고 부족 시 자동 결제 취소 (동시성 문제 고려)                   |
<br>

- #### ️👨🏻‍🔧 관리자
|      기능       |         설명          |                              비고                               |
|:-------------:|:-------------------:|:-------------------------------------------------------------:|
| 상품등록 |  판매하고자 하는 상품을 등록.   |     상품명, 분류, 가격, 할인율, 수량, 대표이미지,<br/> 상품 상세 설명(텍스트 및 이미지)     |
| 상품관리 | 판매등록한 상품들을 확인하고 관리. | 조건별(판매상태, 분류, 상품명) 검색으로 조건에 맞는 상품 확인.<br/>상품 수정 및 상품 판매 중지 가능. |
<br>


---
## 📋 ERD
👉🏻 **ERD 링크 바로가기 :** https://www.erdcloud.com/d/N33PiySjCTmbMZxTq

![img_1.png](img_1.png)
