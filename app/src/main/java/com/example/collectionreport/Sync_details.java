package com.example.collectionreport;

public class Sync_details {
    String acno, receiptno,  ag_code,  actype,  modepay,  cancel,  tr_time, chequedt, chequeno, collectdt, collectamt, remark, receipt_code;
    int customer_type;

    public Sync_details(String acno, String receiptno, String ag_code, String actype, String modepay, String cancel, String tr_time, String chequedt, String chequeno, String collectdt, String collectamt, String remark, String receipt_code, int customer_type) {
        this.acno = acno;
        this.receiptno = receiptno;
        this.ag_code = ag_code;
        this.actype = actype;
        this.modepay = modepay;
        this.cancel = cancel;
        this.tr_time = tr_time;
        this.chequedt = chequedt;
        this.chequeno = chequeno;
        this.collectdt = collectdt;
        this.collectamt = collectamt;
        this.remark = remark;
        this.receipt_code = receipt_code;
        this.customer_type = customer_type;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getReceiptno() {
        return receiptno;
    }

    public void setReceiptno(String receiptno) {
        this.receiptno = receiptno;
    }

    public String getAg_code() {
        return ag_code;
    }

    public void setAg_code(String ag_code) {
        this.ag_code = ag_code;
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

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getTr_time() {
        return tr_time;
    }

    public void setTr_time(String tr_time) {
        this.tr_time = tr_time;
    }

    public String getChequedt() {
        return chequedt;
    }

    public void setChequedt(String chequedt) {
        this.chequedt = chequedt;
    }

    public String getChequeno() {
        return chequeno;
    }

    public void setChequeno(String chequeno) {
        this.chequeno = chequeno;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReceipt_code() {
        return receipt_code;
    }

    public void setReceipt_code(String receipt_code) {
        this.receipt_code = receipt_code;
    }

    public int getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(int customer_type) {
        this.customer_type = customer_type;
    }


}
