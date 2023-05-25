package com.example.onlinebrokerage;

public class houseownerinformation {

    private String name,email,phoneno,whatsappno,preferfamilytype,housetype,furnituretype,address,area,city,state,advance,rentfee;
    public  houseownerinformation(){}

    public houseownerinformation(String name,String email,String phoneno,String whatsappno,String preferfamilytype,String housetype,String furnituretype,String address,String area,String city,String state,String advance,String rentfee)
    {
        this.name=name;
        this.email=email;
        this.phoneno=phoneno;
        this.whatsappno=whatsappno;
        this.preferfamilytype=preferfamilytype;
        this.housetype=housetype;
        this.furnituretype=furnituretype;
        this.address=address;
        this.area=area;
        this.city=city;
        this.state=state;
        this.advance=advance;
        this.rentfee=rentfee;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getWhatsappno() {
        return whatsappno;
    }

    public void setWhatsappno(String whatsappno) {
        this.whatsappno = whatsappno;
    }

    public String getPreferfamilytype() {
        return preferfamilytype;
    }

    public void setPreferfamilytype(String preferfamilytype) {
        this.preferfamilytype = preferfamilytype;
    }

    public String getHousetype() {
        return housetype;
    }

    public void setHousetype(String housetype) {
        this.housetype = housetype;
    }

    public String getFurnituretype() {
        return furnituretype;
    }

    public void setFurnituretype(String furnituretype) {
        this.furnituretype = furnituretype;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAdvance() {
        return advance;
    }

    public void setAdvance(String advance) {
        this.advance = advance;
    }

    public String getRentfee() {
        return rentfee;
    }

    public void setRentfee(String rentfee) {
        this.rentfee = rentfee;
    }
}
