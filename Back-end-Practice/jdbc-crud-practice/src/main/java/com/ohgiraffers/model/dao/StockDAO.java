package com.ohgiraffers.model.dao;

import com.ohgiraffers.model.dto.StockDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.close;

public class StockDAO {

    private Properties prop = new Properties();

    public StockDAO() {
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/jinjin-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int insertStockIn(StockDTO stockIn, Connection con) {
        PreparedStatement pstmt = null;

        int result3 = 0;

        String query = prop.getProperty("insertStock");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, stockIn.getSupplCode());
            pstmt.setInt(2,stockIn.getProCode());
            pstmt.setString(3,stockIn.getInDate());
            pstmt.setInt(4,stockIn.getStEA());

            result3 = pstmt.executeUpdate();

            if(result3 > 0) {

                ProductDAO productDAO = new ProductDAO();
                int updateResult = productDAO.increaseProductEA(con, stockIn.getProCode(), stockIn.getStEA());

                if(updateResult <= 0) {
                    throw new SQLException("상품 수량 업데이트 실패");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(pstmt);
        }
        return result3;
    }

}
