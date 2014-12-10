package com.tmkarthi.sharefood;

/**
 * Created by kmarudhachalam on 12/10/14.
 */
public class Donation {
    private String description;
    private int quantity;
    private String pickupAddress1;
    private String pickupAddress2;
    private String pickupCity;
    private String pickupPostalCode;
    private String pickupPhone;

    public Donation() {
    }

    public Donation(String description, int quantity) {
        this.description = description;
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPickupAddress1() {
        return pickupAddress1;
    }

    public void setPickupAddress1(String pickupAddress1) {
        this.pickupAddress1 = pickupAddress1;
    }

    public String getPickupAddress2() {
        return pickupAddress2;
    }

    public void setPickupAddress2(String pickupAddress2) {
        this.pickupAddress2 = pickupAddress2;
    }

    public String getPickupCity() {
        return pickupCity;
    }

    public void setPickupCity(String pickupCity) {
        this.pickupCity = pickupCity;
    }

    public String getPickupPostalCode() {
        return pickupPostalCode;
    }

    public void setPickupPostalCode(String pickupPostalCode) {
        this.pickupPostalCode = pickupPostalCode;
    }

    public String getPickupPhone() {
        return pickupPhone;
    }

    public void setPickupPhone(String pickupPhone) {
        this.pickupPhone = pickupPhone;
    }
}
