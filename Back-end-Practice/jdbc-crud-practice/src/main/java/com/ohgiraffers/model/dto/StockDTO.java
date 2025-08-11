package com.ohgiraffers.model.dto;

public class StockDTO {
    private String  supplCode;
    private int proCode;
    private String inDate;
    private int stEA;

    public StockDTO() {}

    public StockDTO(String supplCode, int proCode, String inDate, int stEA) {
        this.supplCode = supplCode;
        this.proCode = proCode;
        this.inDate = inDate;
        this.stEA = stEA;
    }

    public void setSupplCode(String supplCode) {
        this.supplCode = supplCode;
    }

    public void setProCode(int proCode) {
        this.proCode = proCode;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public void setStEA(int stEA) {
        this.stEA = stEA;
    }

    public String getSupplCode() {
        return supplCode;
    }

    public int getProCode() {
        return proCode;
    }

    public String getInDate() {
        return inDate;
    }

    public int getStEA() {
        return stEA;
    }

    @Override
    public String toString() {
        return "StockDTO{" +
                "supplCode='" + supplCode + '\'' +
                ", proCode=" + proCode +
                ", inDate='" + inDate + '\'' +
                ", stEA=" + stEA +
                '}';
    }
}
