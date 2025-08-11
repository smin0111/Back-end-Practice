# Back-end Practice

## 펫진진 상품 관련 프로그램
- 프로그램 설명
상품이 입고 내역에 따라 상품수량을 업데이트하고
신규 상품을 추가, 삭제, 전체 상품조회를 할 수 있다.

## 사용기술
- java
- mysql
- jdbc
- productdao
- stockdao


# 논리 모델링
<img width="596" height="617" alt="image" src="https://github.com/user-attachments/assets/836cf4c1-0819-4a70-8cb0-982d52c57646" />


# 물리 모델링
<img width="944" height="637" alt="image" src="https://github.com/user-attachments/assets/6e3eed65-a01b-453f-bc5e-3ae5ad8983f0" />

입고테이블에 입고된 상품들의 정보 입력
입고 된 상품의 수량만큼 상품의 갯수 추가 (보유량 : 5 / 입고량 : 13 / 5 + 13)

**상품은 수량이 없어도 등록가능
but 입고상품은 등록되어있는 상품이 아니면 입고될 수 없음.**
