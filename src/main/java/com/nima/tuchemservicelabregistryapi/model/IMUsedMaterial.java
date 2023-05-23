package com.nima.tuchemservicelabregistryapi.model;

import java.util.Objects;

public class IMUsedMaterial {
    private Long id;
    private String name;
    private String manufacturer;
    private Long price;
    private Long maintenanceId;
    private Double quantity;
    private Short type;

    public IMUsedMaterial() {}

    public IMUsedMaterial(Long id, String name, String manufacturer, Long price, Long maintenanceId, Double quantity, Short type) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.maintenanceId = maintenanceId;
        this.quantity = quantity;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(Long maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public boolean isEqualTo(IMUsedMaterial m) {
        return id.equals(m.getId()) && name.equals(m.getName()) && manufacturer.equals(m.manufacturer) && price.equals(m.price) && maintenanceId.equals(m.maintenanceId) && quantity.equals(m.quantity) && type.equals(m.type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IMUsedMaterial that = (IMUsedMaterial) o;
        return Objects.equals(getId(), that.getId()) && getName().equals(that.getName()) && Objects.equals(getManufacturer(), that.getManufacturer()) && getPrice().equals(that.getPrice()) && Objects.equals(getMaintenanceId(), that.getMaintenanceId()) && Objects.equals(getQuantity(), that.getQuantity()) && Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getManufacturer(), getPrice(), getMaintenanceId(), getQuantity(), getType());
    }

    @Override
    public String toString() {
        return "IMUsedMaterial{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", price=" + price +
                ", maintenanceId=" + maintenanceId +
                ", quantity=" + quantity +
                ", type=" + type +
                '}';
    }
}
