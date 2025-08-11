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
            System.out.println("2. 마지막 상품 코드 조회");
            System.out.println("3. 신규 상품 등록");
            System.out.println("4. 입고 상품 등록");
            System.out.println("5. 상품 수량 변경");
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
                    List<Map<Integer, String>> productList = registDAO.selectAllProduct(con);
                    for(Map<Integer, String> product : productList) {
                        System.out.println("product = " + product);
                    } break;

                case 2 :         /*  상품의 마지막 번호 조회 */
                    System.out.println("maxProductCode = " + maxProductCode); break;
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
                    /* 수량 업데이트 ========================================================= */
                    System.out.print("입고된 상품 코드를 입력하세요 : ");
                    int upCode = sc.nextInt();

                    System.out.print("입고된 수량을 입력하세요 : ");
                    int upEA = sc.nextInt();

                    int result2 = registDAO.updateProductEA(con, upCode, upEA);

                    if (result2 > 0) {
                        System.out.println("상품 수량 업데이트 성공!");
                    } else {
                        System.out.println("상품 수량 업데이트 실패!");
                    } break;
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
