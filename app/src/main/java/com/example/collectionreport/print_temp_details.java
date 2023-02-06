package com.example.collectionreport;


public class print_temp_details {

    String receiptno,shopname,tenantname,total_amount;
    double grand_total;

    int arr_position;
    public print_temp_details(String receiptno,String  shopname,String tenantname,String total_amount,int arr_position, double grand_total) {
        this.receiptno = receiptno;
        this.shopname = shopname;
        this.tenantname = tenantname;
        this.arr_position = arr_position;
        this.total_amount = total_amount;
        this.grand_total =grand_total;
    }

    public String getReceiptno() {
        return receiptno;
    }

    public void setReceiptno(String receiptno) {
        this.receiptno = receiptno;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getTenantname() {
        return tenantname;
    }

    public void setTenantname(String tenantname) {
        this.tenantname = tenantname;
    }

    public int getArr_position() {
        return arr_position;
    }

    public void setArr_position(int arr_position) {
        this.arr_position = arr_position;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public double getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(double grand_total) {
        this.grand_total = grand_total;
    }
}
