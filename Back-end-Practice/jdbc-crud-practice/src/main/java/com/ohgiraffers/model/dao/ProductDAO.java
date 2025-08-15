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

    public List<Map<String, Object>> selectAllProduct(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<Map<String, Object>> productList = new ArrayList<>();

        String query = prop.getProperty("selectAllProduct");

        try {
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                Map<String, Object> product = new HashMap<>();
                product.put("PRODUCT_CODE", rset.getInt("PRODUCT_CODE"));
                product.put("PRODUCT_NAME", rset.getString("PRODUCT_NAME"));
                product.put("PRICE", rset.getInt("PRICE"));
                product.put("EA", rset.getInt("EA"));

                productList.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }

        return productList;
    }

    /* 상품삭제 ====================================================================*/
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
    /* 수량 변경 =============================================================*/
    public int updateProductEA(Connection con, int upCode, int upEA) {
        PreparedStatement pstmt = null;
        int result2 = 0;

        String query = prop.getProperty("updateProductEA");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, upEA);
            pstmt.setInt(2, upCode);
            result2 = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }

        return result2;
    }
    /* 펫 종류별 조회 ========================================================================== */
    public List<Map<String, Object>> selectProductByPetType(Connection con, String petType) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> productList = new ArrayList<>();

        String query = prop.getProperty("selectProductByPetType");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, petType);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> product = new HashMap<>();
                product.put("PRODUCT_CODE", rs.getInt("PRODUCT_CODE"));
                product.put("PRODUCT_NAME", rs.getString("PRODUCT_NAME"));
                product.put("PRICE", rs.getInt("PRICE"));
                product.put("EA", rs.getInt("EA"));
                productList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(pstmt);
        }

        return productList;
    }

    /* 입고로인한 수량 변경===========================================================*/
    public int increaseProductEA(Connection con, int productCode, int addAmount) {
        PreparedStatement pstmt = null;
        int result = 0;

        String query = prop.getProperty("increaseProductEA");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, addAmount);
            pstmt.setInt(2, productCode);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }

        return result;
    }

    // 펫 종류 + 카테고리별 상품 조회
    public List<Map<String, Object>> selectProductByPetTypeAndCategory(Connection con, String petType, String categoryName) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> productList = new ArrayList<>();

        String query = prop.getProperty("selectProductByPetTypeAndCategoryName");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, petType);
            pstmt.setString(2, categoryName);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> product = new HashMap<>();
                product.put("PRODUCT_CODE", rs.getInt("PRODUCT_CODE"));
                product.put("PRODUCT_NAME", rs.getString("PRODUCT_NAME"));
                product.put("PRICE", rs.getInt("PRICE"));
                product.put("EA", rs.getInt("EA"));
                productList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(pstmt);
        }

        return productList;
    }

    /* 상품명 변경 ==================================================================== */
    public int updateProductName(Connection con, int productCode, String newName) {
        PreparedStatement pstmt = null;
        int result = 0;

        String query = prop.getProperty("updateProductName");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, newName);
            pstmt.setInt(2, productCode);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }

        return result;
    }

    /* 상품 가격 변경 ==================================================================== */
    public int updateProductPrice(Connection con, int productCode, int newPrice) {
        PreparedStatement pstmt = null;
        int result = 0;

        String query = prop.getProperty("updateProductPrice");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, newPrice);
            pstmt.setInt(2, productCode);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }

        return result;
    }
  // ========================= 코드로 인한 상품 검색 ==============================
    public Map<String, Object> selectProductByCode(Connection con, int productCode) {
        Map<String, Object> product = null;
        String query = "SELECT PRODUCT_CODE, PRODUCT_NAME, PRICE, EA FROM PRODUCT WHERE PRODUCT_CODE = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, productCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    product = new HashMap<>();
                    product.put("PRODUCT_CODE", rs.getInt("PRODUCT_CODE"));
                    product.put("PRODUCT_NAME", rs.getString("PRODUCT_NAME"));
                    product.put("PRICE", rs.getInt("PRICE"));
                    product.put("EA", rs.getInt("EA"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }


}

