package com.ohgiraffers.model.dto;

public class ProductDTO {

    private int code;
    private String cateCode;
    private String proName;
    private int proPrice;
    private int proEA;

    public ProductDTO(){}

    public ProductDTO(int code, String cateCode, String proName, int proPrice, int proEA) {
        this.code = code;
        this.cateCode = cateCode;
        this.proName = proName;
        this.proPrice = proPrice;
        this.proEA = proEA;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setCateCode(String cateCode) {
        this.cateCode = cateCode;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public void setProPrice(int proPrice) {
        this.proPrice = proPrice;
    }

    public void setProEA(int proEA) {
        this.proEA = proEA;
    }

    public int getCode() {
        return code;
    }

    public String getCateCode() {
        return cateCode;
    }

    public String getProName() {
        return proName;
    }

    public int getProPrice() {
        return proPrice;
    }

    public int getProEA() {
        return proEA;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "code=" + code +
                ", cateCode='" + cateCode + '\'' +
                ", proName='" + proName + '\'' +
                ", proPrice=" + proPrice +
                ", proEA=" + proEA +
                '}';
    }
}
