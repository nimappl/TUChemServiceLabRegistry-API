package com.nima.tuchemservicelabregistryapi.model;

public class TestPrep {
    private Long id;
    private Long testId;
    private String type;
    private Long price;

    public TestPrep() {}

    public TestPrep(Long id, Long testId, String type, Long price) {
        this.id = id;
        this.testId = testId;
        this.type = type;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public boolean isEqualTo(TestPrep prep) {
        return testId.equals(prep.getTestId()) && type.equals(prep.getType()) && price.equals(prep.getPrice());
    }

    @Override
    public String toString() {
        return "TestPrep{" +
                "id=" + id +
                ", testId=" + testId +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}
