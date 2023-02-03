package com.example.collectionreport;

public class report_shift_details {
    String new_mob_no,sno,acno,actype,modepay,tr_time,collectdt,collectamt,customer_name,city_name,cancel,receipt_code,phone_no,prvs_amount, agent_code,remark,name,City;
    int customer_type,close_status;

    public report_shift_details(String sno, String acno, String actype, String modepay, String tr_time, String collectdt, String collectamt, String customer_name, String city_name,int customer_type,int close_status,String cancel,String receipt_code,String phone_no,String prvs_amount,String agent_code,String remark,String name,String new_mob_no,String City) {
        this.sno = sno;
        this.acno = acno;
        this.actype = actype;
        this.modepay = modepay;
        this.tr_time = tr_time;
        this.collectdt = collectdt;
        this.collectamt = collectamt;
        this.customer_name = customer_name;
        this.city_name = city_name;
        this.customer_type = customer_type;
        this.close_status = close_status;
        this.cancel = cancel;
        this.receipt_code =receipt_code;
        this.phone_no = phone_no;
        this.prvs_amount = prvs_amount;
        this.agent_code = agent_code;
        this.remark = remark;
        this.name = name;
        this.City = City;
        this.new_mob_no = new_mob_no;
    }

    public String getNew_mob_no() {
        return new_mob_no;
    }

    public void setNew_mob_no(String new_mob_no) {
        this.new_mob_no = new_mob_no;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAgent_code() {
        return agent_code;
    }

    public void setAgent_code(String agent_code) {
        this.agent_code = agent_code;
    }

    public String getPrvs_amount() {
        return prvs_amount;
    }

    public void setPrvs_amount(String prvs_amount) {
        this.prvs_amount = prvs_amount;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getReceipt_code() {
        return receipt_code;
    }

    public void setReceipt_code(String receipt_code) {
        this.receipt_code = receipt_code;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public int getClose_status() {
        return close_status;
    }

    public void setClose_status(int close_status) {
        this.close_status = close_status;
    }

    public int getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(int customer_type) {
        this.customer_type = customer_type;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }

    public String getModepay() {
        return modepay;
    }

    public void setModepay(String modepay) {
        this.modepay = modepay;
    }

    public String getTr_time() {
        return tr_time;
    }

    public void setTr_time(String tr_time) {
        this.tr_time = tr_time;
    }

    public String getCollectdt() {
        return collectdt;
    }

    public void setCollectdt(String collectdt) {
        this.collectdt = collectdt;
    }

    public String getCollectamt() {
        return collectamt;
    }

    public void setCollectamt(String collectamt) {
        this.collectamt = collectamt;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
