package com.example.collectionreport;

public class City_details {
    String sno,city_name,total_customer;

    public City_details(String sno, String city_name, String total_customer) {
        this.sno = sno;
        this.city_name = city_name;
        this.total_customer = total_customer;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getTotal_customer() {
        return total_customer;
    }

    public void setTotal_customer(String total_customer) {
        this.total_customer = total_customer;
    }
}
