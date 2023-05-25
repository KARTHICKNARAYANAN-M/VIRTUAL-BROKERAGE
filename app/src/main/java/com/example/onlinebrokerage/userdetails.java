package com.example.onlinebrokerage;

public class userdetails {

    String UserID,Address,AdvanceAmount,Area,City,EmailAddress,FurnitureType,HouseType,HouseOwnerType,HouseOwnerName,PhoneNo,PreferredFamilyType,State,WhatsappNo,Rentfee;




    public userdetails()
    {

    }

    public userdetails(String USERID, String address, String advanceAmount, String area, String city, String emailAddress, String furnitureType, String houseType, String houseOwnerType, String houseOwnerName, String phoneNo, String preferredFamilyType,String state, String whatsappNo,String rentfee) {
        this.UserID = USERID;
        this.Address = address;
        this.AdvanceAmount = advanceAmount;
        this.Area = area;
        this.City = city;
        this.EmailAddress = emailAddress;
       this. FurnitureType = furnitureType;
        this.HouseType = houseType;
        this.HouseOwnerType = houseOwnerType;
        this.HouseOwnerName = houseOwnerName;
        this.PhoneNo = phoneNo;
        this.PreferredFamilyType = preferredFamilyType;
        this.State = state;
        this.WhatsappNo = whatsappNo;
        this.Rentfee=rentfee;
    }

    public String getRentfee() {
        return Rentfee;
    }

    public void setRentfee(String rentfee) {
        this.Rentfee = rentfee;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String USERID) {
        this.UserID = USERID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAdvanceAmount() {
        return AdvanceAmount;
    }

    public void setAdvanceAmount(String advanceAmount) {
        AdvanceAmount = advanceAmount;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getFurnitureType() {
        return FurnitureType;
    }

    public void setFurnitureType(String furnitureType) {
        FurnitureType = furnitureType;
    }

    public String getHouseType() {
        return HouseType;
    }

    public void setHouseType(String houseType) {
        HouseType = houseType;
    }

    public String getHouseOwnerType() {
        return HouseOwnerType;
    }

    public void setHouseOwnerType(String houseOwnerType) {
        HouseOwnerType = houseOwnerType;
    }

    public String getHouseOwnerName() {
        return HouseOwnerName;
    }

    public void setHouseOwnerName(String houseOwnerName) {
        HouseOwnerName = houseOwnerName;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getPreferredFamilyType() {
        return PreferredFamilyType;
    }

    public void setPreferredFamilyType(String preferredFamilyType) {
        PreferredFamilyType = preferredFamilyType;
    }


    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getWhatsappNo() {
        return WhatsappNo;
    }

    public void setWhatsappNo(String whatsappNo) {
        WhatsappNo = whatsappNo;
    }
}
