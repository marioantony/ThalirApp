package com.example.thalir_app;

public class OrderModal {
    String farmerName;
    String farmerLocation;
    String requestedBag;

    public OrderModal(String farmerName, String farmerLocation, String requestedBag) {
        this.farmerName = farmerName;
        this.farmerLocation = farmerLocation;
        this.requestedBag = requestedBag;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getFarmerLocation() {
        return farmerLocation;
    }

    public void setFarmerLocation(String farmerLocation) {
        this.farmerLocation = farmerLocation;
    }

    public String getRequestedBag() {
        return requestedBag;
    }

    public void setRequestedBag(String requestedBag) {
        this.requestedBag = requestedBag;
    }
}
