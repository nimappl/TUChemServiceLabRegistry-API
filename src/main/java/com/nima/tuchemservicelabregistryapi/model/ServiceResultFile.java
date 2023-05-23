package com.nima.tuchemservicelabregistryapi.model;

public class ServiceResultFile {
    private Long serviceId;
    private String filePath;

    public ServiceResultFile() {}

    public ServiceResultFile(Long serviceId, String filePath) {
        this.serviceId = serviceId;
        this.filePath = filePath;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
