package com.nhnacademy.shoppingmall.address.domain;

public class Address {
    private int addressId;
    private String userId;
    private String streetAddress;
    private String postalCode;

    public Address() {
    }

    public Address(int addressId, String userId, String streetAddress, String postalCode) {
        this.addressId = addressId;
        this.userId = userId;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
    }

    public int getAddressId() { return addressId; }
    public void setAddressId(int addressId) { this.addressId = addressId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getStreetAddress() { return streetAddress; }
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
}
