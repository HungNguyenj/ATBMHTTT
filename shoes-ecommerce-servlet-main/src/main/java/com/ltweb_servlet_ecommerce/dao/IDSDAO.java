package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.model.DSModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IDSDAO {
    List<DSModel> findAll(Pageble pageble) throws SQLException;
    DSModel findById(Long id) throws SQLException;
    DSModel findWithFilter(DSModel model) throws SQLException;
    List<DSModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException;
    List<DSModel> findAllWithFilter(DSModel model,Pageble pageble) throws SQLException;
    Long save(DSModel model) throws SQLException;
    void update(DSModel model) throws SQLException;
    void delete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;
}
