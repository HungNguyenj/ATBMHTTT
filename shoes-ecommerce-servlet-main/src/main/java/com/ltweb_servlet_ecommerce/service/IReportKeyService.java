package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.DSModel;
import com.ltweb_servlet_ecommerce.model.UserModel;

import java.sql.SQLException;

public interface IReportKeyService {
    public void reportKey(DSModel dsModel, UserModel userModel) throws SQLException;
}
