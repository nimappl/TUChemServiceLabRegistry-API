package com.nima.tuchemservicelabregistryapi.model;

public class ServiceResultFile {
    private Long serviceId;
    private String fileName;
    private String filePath;

    public ServiceResultFile() {}

    public ServiceResultFile(Long serviceId, String fileName, String filePath) {
        this.serviceId = serviceId;
        this.fileName = fileName;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
