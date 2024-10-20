package com.ltweb_servlet_ecommerce.mapper.result;

import java.util.List;

public class MapSQLAndParamsResult {
    private String sql;
    private List<Object> params;
    public MapSQLAndParamsResult(String sql, List<Object> params) {
        this.sql = sql;
        this.params = params;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
