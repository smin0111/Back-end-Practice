# Back-end Practice

## 펫진진 상품 관련 프로그램
### 프로그램 설명: 펫진진 쇼핑몰 상품에 관한 상품 및 재고관리 프로그램
 - 전체 상품 조회
 - 펫 종류별 상품 조회
 - 신규 상품 등록 및 삭제
 - 입고 내역에 따른 재고수량 자동 업데이트
 - 상품 수량 변경

## 사용기술
- java
- mysql
- jdbc
- DAO


# 논리 모델링
<img width="604" height="621" alt="image" src="https://github.com/user-attachments/assets/1baa30b1-482a-45e3-93c8-327210a95a66" />

현재 입고 내역과 상품과는 M : M 관계가 형성되고 있음
상품기준 여러 입고내역들이 존재하며 / 입고내역에서도 여러 상품들이 조회됨
따라서 상품엔터티와 입고내역 엔터티 사이에 새로운 엔터티를 만들어 주는게 좋음
- 입고내역(StockIn)
  - 입고코드 (PK)
  - 입고날짜
  - 발주센터코드 (FK)
- 상품(Product)
  - 상품코드 (PK)
  - 카테고리코드 (FK)
  - 상품명
  - 가격
  - 보유수량
- 입고상세(StockInDetail) ← 새로운 엔터티
  - 입고상세ID (PK)
  - 입고코드 (FK, StockIn 참조)
  - 상품코드 (FK, Product 참조)
  - 수량
  - 단가

# 물리 모델링
<img width="890" height="655" alt="image" src="https://github.com/user-attachments/assets/bd8c8c30-779a-4e24-afd4-796dc8d9b040" />


