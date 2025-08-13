package com.ohgiraffers.run;
import com.ohgiraffers.model.dao.ProductDAO;
import com.ohgiraffers.model.dao.StockDAO;
import com.ohgiraffers.model.dto.ProductDTO;
import com.ohgiraffers.model.dto.StockDTO;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.getConnection;
public class Application {

    public static void main(String[] args) {

        Connection con = getConnection();
        ProductDAO registDAO = new ProductDAO();
        StockDAO registDAO1 = new StockDAO();
        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("1. 전체 상품 조회");
            System.out.println("2. 펫 종류별 상품 조회");
            System.out.println("3. 신규 상품 등록");
            System.out.println("4. 입고 상품 수량 등록");
            System.out.println("5. 상품 변경");
            System.out.println("6. 상품 삭제");
            System.out.println("9. 프로그램 종료");
            System.out.print("메뉴를 선택해주세요 : ");

            int num = sc.nextInt();
            sc.nextLine();

            int maxProductCode = registDAO.selectLastProductCode(con);
            int proCode = maxProductCode + 1;


            switch (num) {
                case 1 :
                    /* 전체 상품 조회 */
                    List<Map<String, Object>> productList = registDAO.selectAllProduct(con);
                    for (Map<String, Object> product : productList) {
                        System.out.println("상품코드: " + product.get("PRODUCT_CODE")
                                + ", 상품명: " + product.get("PRODUCT_NAME")
                                + ", 가격: " + product.get("PRICE")
                                + ", 수량: " + product.get("EA"));
                    }
                    break;
                case 2:
                    System.out.println("조회할 펫 종류를 선택하세요.");
                    System.out.println("1. 강아지");
                    System.out.println("2. 고양이");
                    System.out.print("번호 입력 : ");
                    int petNum = sc.nextInt();
                    sc.nextLine();

                    String petType = "";
                    if (petNum == 1) {
                        petType = "강아지";
                    } else if (petNum == 2) {
                        petType = "고양이";
                    } else {
                        System.out.println("잘못된 입력입니다.");
                        break;
                    }

                    System.out.println("조회할 카테고리를 선택하세요.");
                    System.out.println("1. " + petType + "전체 상품");
                    System.out.println("2. " + petType + "음식");
                    System.out.println("3. " + petType + "용품");
                    System.out.print("번호 입력 : ");
                    int cateNum = sc.nextInt();
                    sc.nextLine();

                    List<Map<String, Object>> productList1 = null;

                    switch (cateNum) {
                        case 1:
                            // 전체 상품
                            productList1 = registDAO.selectProductByPetType(con, petType);
                            break;
                        case 2:
                            // 음식 카테고리
                            productList1 = registDAO.selectProductByPetTypeAndCategory(con, petType, petType + "음식");
                            break;
                        case 3:
                            // 용품 카테고리
                            productList1 = registDAO.selectProductByPetTypeAndCategory(con, petType, petType + "용품");
                            break;

                        default:
                            System.out.println("잘못된 입력입니다.");
                            break;
                    }

                    if (productList1 == null || productList1.isEmpty()) {
                        System.out.println("조회된 상품이 없습니다.");
                    } else {
                        for (Map<String, Object> product : productList1) {
                            System.out.println("------------------------");
                            System.out.println("상품코드 : " + product.get("PRODUCT_CODE"));
                            System.out.println("상품명 : " + product.get("PRODUCT_NAME"));
                            System.out.println("가격 : " + product.get("PRICE") + "원");
                            System.out.println("재고 : " + product.get("EA") + "개");
                            System.out.println("------------------------");
                        }
                    }
                    break;


                case 3 :
                    /* 신규 상품 등록 */
                    System.out.print("등록할 상품의 카테고리를 입력해주세요(강아지음식,고양이음식,강아지용품,고양이용품): ");
                    String cateName = sc.nextLine();
                    System.out.print("등록할 상품의 이름을 입력해주세요 : ");
                    String proName = sc.nextLine();
                    System.out.print("등록할 가격을 입력해주세요 : ");
                    int proPrice = sc.nextInt();
                    System.out.print("등록할 수량을 입력해주세요 : ");
                    int proEA = sc.nextInt();


                    String categoryCode = null;
                    switch (cateName) {
                        case "강아지음식" : categoryCode = "C1"; break;
                        case "고양이음식" : categoryCode = "C2"; break;
                        case "강아지용품" : categoryCode = "C3"; break;
                        case "고양이용품" : categoryCode = "C4"; break;
                    }

                    ProductDTO newProduct = new ProductDTO(proCode,categoryCode, proName, proPrice, proEA);

                    int result = registDAO.insertNewProduct(newProduct, con);

                    if(result > 0) {
                        System.out.println("신규 메뉴 등록 성공!");
                    } else {
                        System.out.println("신규 메뉴 등록 실패!");
                    }  break;
                case 4 :
                    /* 입고 */
                    System.out.print("입고된 상품의 발주처를 입력해주세요(K브랜드, J브랜드)  : ");
                    String suppl = sc.nextLine();
                    System.out.print("입고된 상품코드를 입력해주세요 : ");
                    int inProCode = sc.nextInt();
                    System.out.print("입고된 날짜를 입력해주세요(20**-**-**) : ");
                    sc.nextLine();
                    String inProDate = sc.nextLine();
                    System.out.print("상품의 입고 수량을 입력해주세요 : ");
                    int inProEA = sc.nextInt();

                    String supplier = null;
                    switch (suppl){
                        case "K브랜드" : supplier = "S1";break;
                        case "J브랜드" : supplier = "S2";break;
                    }
                    StockDTO stockIn = new StockDTO(supplier,inProCode,inProDate,inProEA);

                    int result3 = registDAO1.insertStockIn(stockIn, con);

                    if(result3 > 0) {
                        System.out.println("입고내역 등록 성공!");
                    } else {
                        System.out.println("입고내역 등록 실패!");
                    } break;
                case 5 :
                    /* 상품 변경 서브메뉴 ========================================================= */
                    System.out.println("상품 변경 메뉴입니다. 변경할 항목을 선택하세요.");
                    System.out.println("1. 상품 이름 변경");
                    System.out.println("2. 상품 가격 변경");
                    System.out.println("3. 상품 수량 (추가/감소)");
                    System.out.print("번호 입력 : ");
                    int changeNum = sc.nextInt();
                    sc.nextLine();

                    System.out.print("변경할 상품 코드를 입력해주세요 : ");
                    int changeProCode = sc.nextInt();
                    sc.nextLine();

                    int changeResult = 0;

                    switch (changeNum) {
                        case 1:
                            // 상품 이름 변경
                            System.out.print("새로운 상품 이름을 입력해주세요 : ");
                            String newName = sc.nextLine();
                            changeResult = registDAO.updateProductName(con, changeProCode, newName);
                            if (changeResult > 0) {
                                System.out.println("상품 이름 변경 성공!");
                            } else {
                                System.out.println("상품 이름 변경 실패!");
                            }
                            break;

                        case 2:
                            // 상품 가격 변경
                            System.out.print("새로운 가격을 입력해주세요 : ");
                            int newPrice = sc.nextInt();
                            sc.nextLine();
                            changeResult = registDAO.updateProductPrice(con, changeProCode, newPrice);
                            if (changeResult > 0) {
                                System.out.println("상품 가격 변경 성공!");
                            } else {
                                System.out.println("상품 가격 변경 실패!");
                            }
                            break;

                        case 3:
                            // 상품 수량 변경 (현재 DAO는 EA = EA + ? 형태)
                            System.out.print("추가 수량을 입력해주세요 : ");
                            int deltaEA = sc.nextInt();
                            sc.nextLine();
                            changeResult = registDAO.updateProductEA(con, changeProCode, deltaEA);
                            if (changeResult > 0) {
                                System.out.println("상품 수량 변경 성공!");
                            } else {
                                System.out.println("상품 수량 변경 실패!");
                            }
                            break;

                        default:
                            System.out.println("잘못된 입력입니다.");
                            break;
                    }
                    break;

                case 6 :
                    /* 상품 삭제 ========================================================= */
                    System.out.print("삭제할 상품 코드를 입력해주세요 : ");
                    int dpCode = sc.nextInt();

                    int result1 = registDAO.deleteProductCode(con, dpCode);

                    if(result1 > 0) {
                        System.out.println("상품 삭제 성공!");
                    } else {
                        System.out.println("상품 삭제 실패!");
                    } break;
                case 9 :
                    System.out.println("프로그램이 종료되었습습니다."); return;
                default:
                    System.out.println("잘못된 번호입니다.");
            }
        }
   }
}
