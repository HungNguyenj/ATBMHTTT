package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IDSDAO;
import com.ltweb_servlet_ecommerce.dao.IUserDAO;
import com.ltweb_servlet_ecommerce.mapper.impl.DSMapper;
import com.ltweb_servlet_ecommerce.mapper.impl.UserMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.DSModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DSDAO extends AbstractDAO<DSModel> implements IDSDAO {
    @Override
    public List<DSModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM `digital_signatures` WHERE isDeleted=0 ");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder,pageble);
        return query(sqlStrBuilder.toString(),new DSMapper(),null, DSModel.class);
    }

    @Override
    public DSModel findById(Long id) throws SQLException {
        String sql = "select * from `digital_signatures` where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<DSModel> result =  query(sql,new DSMapper(),params,DSModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public DSModel findWithFilter(DSModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM `digital_signatures` WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new DSMapper().mapSQLAndParams(sqlStrBuilder,model,"select",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<DSModel> result = query(sql.toString(), new DSMapper(),params,DSModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<DSModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM `digital_signatures` WHERE 1=1 ");
        List<DSModel> result = queryWithSubQuery(sqlStrBuilder,new DSMapper(),subQueryList,"in",DSModel.class,pageble);
        return result;
    }

    @Override
    public List<DSModel> findAllWithFilter(DSModel model, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM `digital_signatures` WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new DSMapper().mapSQLAndParams(sqlStrBuilder,model,"select",pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<DSModel> result = query(sql.toString(), new DSMapper(),params,DSModel.class);
        return result;
    }

    @Override
    public Long save(DSModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO `digital_signatures` SET ");
        MapSQLAndParamsResult sqlAndParams = new DSMapper().mapSQLAndParams(sqlStrBuilder,model,"insert",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql,params);
    }

    @Override
    public void update(DSModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE `digital_signatures` SET ");
        MapSQLAndParamsResult sqlAndParams = new DSMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from `digital_signatures` where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql,params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql,params);
    }
}
