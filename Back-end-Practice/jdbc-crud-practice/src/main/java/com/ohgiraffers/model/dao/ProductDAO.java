package com.ohgiraffers.model.dao;

import com.ohgiraffers.model.dto.ProductDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.close;

public class ProductDAO {

    private Properties prop = new Properties();

    public ProductDAO() {
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/jinjin-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
/* 마지막 상품 코드 검샘 =================================================================== */
    public int selectLastProductCode(Connection con) {

        Statement stmt = null;
        ResultSet rset = null;

        int maxProductCode = 0;

        String query = prop.getProperty("selectLastProductCode");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            if(rset.next()){
                maxProductCode = rset.getInt("MAX(A.PRODUCT_CODE)");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(stmt);
        }

        return maxProductCode;
    }
    /* 신규 상품 INSERT ============================================================ */
    public int insertNewProduct(ProductDTO newProduct, Connection con) {

        PreparedStatement pstmt = null;

        int result = 0;

        String query = prop.getProperty("insertProduct");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, newProduct.getCode());
            pstmt.setString(2, newProduct.getCateCode());
            pstmt.setString(3, newProduct.getProName());
            pstmt.setInt(4, newProduct.getProPrice());
            pstmt.setInt(5, newProduct.getProEA());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }

        return result;
    }
/* 전체 상품 목록 검색 ======================================================================== */

    public List<Map<Integer, String>> selectAllProduct(Connection con) {

        Statement stmt = null;
        ResultSet rset = null;

        List<Map<Integer, String>> productList = null;

        String query = prop.getProperty("selectAllProductList");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            productList = new ArrayList<>();

            while (rset.next()) {
                Map<Integer, String> product = new HashMap<>();
                product.put(rset.getInt("Product_CODE"), rset.getString("Product_NAME"));

                productList.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(stmt);
        }

        return productList;
    }

    public int deleteProductCode(Connection con, int proCode) {

        PreparedStatement pstmt = null;
        int result1 = 0;

        String query = prop.getProperty("deleteProductCode");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, proCode);
            result1 = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }

        return result1;
    }

    public int updateProductEA(Connection con, int upCode, int upEA) {
        PreparedStatement pstmt = null;
        int result2 = 0;

        String query = prop.getProperty("updateProductEA");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, upEA);     // 첫 번째 ? → 변경할 수량
            pstmt.setInt(2, upCode);   // 두 번째 ? → 해당 제품 코드
            result2 = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }

        return result2;
    }



}

