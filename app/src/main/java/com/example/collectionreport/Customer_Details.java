package com.example.collectionreport;

public class Customer_Details {

    String shopcode,shopname,tenantcode,tenantname,rent,olddue_amount,due_amount,total_amount,statuscode,statusname,shopstatus,rentcycle;
    boolean selected;

    public Customer_Details(String shopcode,String  shopname,String tenantcode,String tenantname,String rent,String olddue_amount, String due_amount, String total_amount,String statuscode,String statusname,String shopstatus,String rentcycle, boolean selected) {
        this.shopcode = shopcode;
        this.shopname = shopname;
        this.tenantcode = tenantcode;
        this.tenantname = tenantname;
        this.rent  = rent;
        this.olddue_amount = olddue_amount;
        this.due_amount = due_amount;
        this.total_amount = total_amount;
        this.statuscode =statuscode;
        this.statusname =statusname;
        this.selected = selected;
        this.shopstatus =shopstatus;
        this.rentcycle = rentcycle;

    }

    public String getShopstatus() {
        return shopstatus;
    }

    public void setShopstatus(String shopstatus) {
        this.shopstatus = shopstatus;
    }

    public String getRentcycle() {
        return rentcycle;
    }

    public void setRentcycle(String rentcycle) {
        this.rentcycle = rentcycle;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
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

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getOlddue_amount() {
        return olddue_amount;
    }

    public void setOlddue_amount(String olddue_amount) {
        this.olddue_amount = olddue_amount;
    }

    public String getDue_amount() {
        return due_amount;
    }

    public void setDue_amount(String due_amount) {
        this.due_amount = due_amount;
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
