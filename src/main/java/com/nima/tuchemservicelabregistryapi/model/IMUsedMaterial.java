package com.nima.tuchemservicelabregistryapi.model;

public class IMUsedMaterial {
    private long id;
    private String name;
    private String manufacturer;
    private double price;
    private long maintenanceId;
    private double quantity;
    private short type;

    public IMUsedMaterial() {}

    public IMUsedMaterial(long id, String name, String manufacturer, double price, long maintenanceId, double quantity, short type) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.maintenanceId = maintenanceId;
        this.quantity = quantity;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(long maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }
}
