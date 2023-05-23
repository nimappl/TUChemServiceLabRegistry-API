package com.nima.tuchemservicelabregistryapi.model;

import java.util.ArrayList;
import java.util.List;

public class Data<T> {
    public List<T> records;
    public List<Filter> filters;
    public String sortBy;
    public Short sortType;
    public Integer pageSize;
    public Integer pageNumber;
    public Integer count = 0;

    public Data() {
        this.records = new ArrayList();
        this.filters = new ArrayList();
        this.sortBy = null;
    }

    public String selectQuery(String tableName, String idColumn) {
        String query = "SELECT * FROM " + tableName;
        boolean firstFilter = true;

        // WHERE clause for each of filters
        for (Filter filter : filters) {
            if (firstFilter) {
                query += " WHERE ";
                firstFilter = false;
            } else query += " AND ";

            query += filter.key + " LIKE '%" + filter.value + "%' ";
        }

        // ORDER BY
        if (sortBy == null) sortBy = idColumn;
        if (sortType == null) sortType = 0;
        query += " ORDER BY " + sortBy + (sortType == 0 ? " ASC " : " DESC ");

        // PAGINATION
        if (pageSize != null) {
            query += " OFFSET " + ((pageNumber - 1) * pageSize) + " ROWS FETCH NEXT " + pageSize + " ROWS ONLY";
        }
        return query;
    }

    public String countQuery(String tableName, String idColumn) {
        String query = "SELECT COUNT(*) FROM " + tableName;
        boolean firstFilter = true;
        for (Filter filter : filters) {
            if (firstFilter) {
                query += " WHERE ";
                firstFilter = false;
            } else query += " AND ";

            query += filter.key + " LIKE '%" + filter.value + "%' ";
        }
        return query;
    }
}
