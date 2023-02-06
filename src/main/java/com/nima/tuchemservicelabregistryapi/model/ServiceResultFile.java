package com.nima.tuchemservicelabregistryapi.model;

public class ServiceResultFile {
    private long serviceId;
    private String filePath;

    public ServiceResultFile() {}

    public ServiceResultFile(long serviceId, String filePath) {
        this.serviceId = serviceId;
        this.filePath = filePath;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
