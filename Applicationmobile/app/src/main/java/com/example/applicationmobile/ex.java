package com.example.applicationmobile;

public class ex {
    private String EX_id;
    private String EX_Type;
    private String EX_Expense;
    private String EX_Time;
    private String EXTRIP_id;

    public ex (String EX_id,String EX_Type,String EX_Expense,String EX_Time,String EXTRIP_id){
        this.EX_id = EX_id;
        this.EX_Type = EX_Type;
        this.EX_Expense = EX_Expense;
        this.EX_Time = EX_Time;
        this.EXTRIP_id = EXTRIP_id;

    }

    public String getEX_id() {
        return EX_id;
    }

    public void setEX_id(String EX_id) {
        this.EX_id = EX_id;
    }

    public String getEX_Type() {
        return EX_Type;
    }

    public void setEX_Type(String EX_Type) {
        this.EX_Type = EX_Type;
    }

    public String getEX_Expense() {
        return EX_Expense;
    }

    public void setEX_Expense(String EX_Expense) {
        this.EX_Expense = EX_Expense;
    }

    public String getEX_Time() {
        return EX_Time;
    }

    public void setEX_Time(String EX_Time) {
        this.EX_Time = EX_Time;
    }

    public String getEXTRIP_id() {
        return EXTRIP_id;
    }

    public void setEXTRIP_id(String EXTRIP_id) {
        this.EXTRIP_id = EXTRIP_id;
    }
}
