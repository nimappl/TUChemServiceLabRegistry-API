package com.nima.tuchemservicelabregistryapi.model;

import java.util.ArrayList;
import java.util.List;

public class Data<T> {
    public List<T> records;
    public List<Filter> filters;
    public String sortBy;
    public short sortType;
    public int pageSize;
    public int pageNumber;
    public int count = 0;

    public Data() {
        this.records = new ArrayList();
        this.filters = new ArrayList();
        this.sortBy = null;
        this.pageSize = 0;
        this.count = 0;
    }
}
