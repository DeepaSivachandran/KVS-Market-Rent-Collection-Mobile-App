package com.example.collectionreport;


public class Collection_report_details {

    String receiptno,shopcode,shopname,tenantcode,tenantname,total_amount,rentcycle;
    boolean selected;

    public Collection_report_details(String receiptno,String shopcode,String  shopname,String tenantcode,String tenantname,String total_amount,String rentcycle, boolean selected) {
        this.receiptno =receiptno;
        this.shopcode = shopcode;
        this.shopname = shopname;
        this.tenantcode = tenantcode;
        this.tenantname = tenantname;
        this.total_amount = total_amount;
        this.selected = selected;
        this.rentcycle = rentcycle;

    }

    public String getReceiptno() {
        return receiptno;
    }

    public void setReceiptno(String receiptno) {
        this.receiptno = receiptno;
    }

    public String getRentcycle() {
        return rentcycle;
    }

    public void setRentcycle(String rentcycle) {
        this.rentcycle = rentcycle;
    }


    public String getShopcode() {
        return shopcode;
    }

    public void setShopcode(String shopcode) {
        this.shopcode = shopcode;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getTenantcode() {
        return tenantcode;
    }

    public void setTenantcode(String tenantcode) {
        this.tenantcode = tenantcode;
    }

    public String getTenantname() {
        return tenantname;
    }

    public void setTenantname(String tenantname) {
        this.tenantname = tenantname;
    }


    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

