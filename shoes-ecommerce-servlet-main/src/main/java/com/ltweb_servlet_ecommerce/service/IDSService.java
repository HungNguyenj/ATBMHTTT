package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.DSModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.sql.SQLException;
import java.util.List;

public interface IDSService {

    public boolean genKey();
    public void loadPublic(PublicKey key);
    public void loadPrivate(PrivateKey key);
    public String sign(String mes) throws InvalidKeyException, SignatureException;
    public String signFile(String src) throws InvalidKeyException, SignatureException, IOException;
    public boolean verify(String mes, String sign) throws InvalidKeyException, SignatureException;
    public boolean verifyFile(String src, String sign) throws InvalidKeyException, IOException, SignatureException;
    public PublicKey getPublicKey();
    public PrivateKey getPrivateKey();
    public DSModel save(DSModel model) throws SQLException;
    public DSModel update(DSModel model) throws SQLException;
    public DSModel delete(Long id) throws SQLException;
    public DSModel findWithFilter(DSModel model) throws SQLException;
    public DSModel findById(Long id) throws SQLException;
    public List<DSModel> findAllWithFilter(DSModel model, Pageble pageble) throws SQLException;
}
