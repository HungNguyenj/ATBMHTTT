package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.IDSDAO;
import com.ltweb_servlet_ecommerce.dao.impl.DSDAO;
import com.ltweb_servlet_ecommerce.model.DSModel;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.model.RoleModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IDSService;

import javax.inject.Inject;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.List;

public class DSService implements IDSService {
    KeyPair keyPair;
    SecureRandom secureRandom;
    Signature signature;

    PublicKey publicKey;
    PrivateKey privateKey;

    @Inject
    IDSDAO dsDAO;

    public DSService() throws NoSuchAlgorithmException, NoSuchProviderException {
        this("DSA", "SHA1PRNG", "SUN");
    }

    public DSService(String alg, String algRandom, String prov) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(alg, prov);
        secureRandom = SecureRandom.getInstance(algRandom, prov);
        generator.initialize(1024, secureRandom);
        keyPair = generator.genKeyPair();
        signature = Signature.getInstance(alg, prov);
    }

    public boolean genKey() {
        if (keyPair == null) return false;
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
        return true;
    }

    public void loadPublic(PublicKey key) {
        this.publicKey = key;
    }

    public void loadPrivate(PrivateKey key) {
        this.privateKey = key;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public String sign(String mes) throws InvalidKeyException, SignatureException {
        byte[] data = mes.getBytes();
        signature.initSign(privateKey);
        signature.update(data);
        byte[] sign = signature.sign();
        return Base64.getEncoder().encodeToString(sign);
    }

    public String signFile(String src) throws InvalidKeyException, SignatureException, IOException {
        byte[] data = src.getBytes();
        signature.initSign(privateKey);

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        byte[] buff = new byte[1024];
        int read;
        while ((read = bis.read(buff)) != -1) {
            signature.update(buff, 0, read);
        }
        bis.close();

        byte[] sign = signature.sign();
        return Base64.getEncoder().encodeToString(sign);
    }

    public boolean verify(String mes, String sign) throws InvalidKeyException, SignatureException {
        signature.initVerify(publicKey);
        byte[] data = mes.getBytes();
        byte[] signValue = Base64.getDecoder().decode(sign);
        signature.update(data);
        return signature.verify(signValue);
    }

    public boolean verifyFile(String src, String sign) throws InvalidKeyException, IOException, SignatureException {
        signature.initVerify(publicKey);
        byte[] data = src.getBytes();
        byte[] signValue = Base64.getDecoder().decode(sign);

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        byte[] buff = new byte[1024];
        int read;
        while ((read = bis.read(buff)) != -1) {
            signature.update(buff, 0, read);
        }
        bis.close();

        return signature.verify(signValue);
    }


    @Override
    public DSModel save(DSModel model) throws SQLException {
        if (dsDAO == null) {
            dsDAO = new DSDAO();
        }
        System.out.println("This is save in service");
        Long ds = dsDAO.save(model);
        return findById(ds);
    }

    @Override
    public DSModel update(DSModel model) throws SQLException {
        if (dsDAO == null) {
            dsDAO = new DSDAO();
        }
        DSModel oldModel = dsDAO.findById(model.getId());
        if (oldModel == null) {
            return null;
        }
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        dsDAO.update(model);
        DSModel newModel = findById(model.getId());
        return newModel;
    }

    @Override
    public DSModel delete(Long id) throws SQLException {
        if (dsDAO == null) {
            dsDAO = new DSDAO();
        }
        DSModel oldModel = findById(id);
        dsDAO.delete(id);
        return oldModel;
    }

    @Override
    public DSModel findWithFilter(DSModel model) throws SQLException {
        if (dsDAO == null) {
            dsDAO = new DSDAO();
        }
        DSModel ds = dsDAO.findWithFilter(model);
        return ds;
    }

    @Override
    public DSModel findById(Long id) throws SQLException {
        if (dsDAO == null) {
            dsDAO = new DSDAO();
        }
        DSModel ds = dsDAO.findById(id);
        return ds;
    }

    @Override
    public List<DSModel> findAllWithFilter(DSModel model, Pageble pageble) throws SQLException {
        if (dsDAO == null) {
            dsDAO = new DSDAO();
        }
        List<DSModel> list = dsDAO.findAllWithFilter(model, pageble);
        return list;
    }

    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException, NoSuchProviderException {
        DSModel model = new DSModel();
        IDSService dsService = new DSService();
        IDSDAO dsdao = new DSDAO();

//        model.setUser_id(21L);
//        model.setUsedNow(1);
//
//        model = dsService.findWithFilter(model);
        model = dsService.findById(23L);
        System.out.println(model.toString());

        model.setExpiredTime(20);
        model.setIsReported(2);
        model = dsService.update(model);
        System.out.println(model.toString());

//        List<DSModel> list = dsService.findAllWithFilter(model, null);
//        for (DSModel ds : list) {
//            System.out.println(ds.toString());
//        }

    }

}
