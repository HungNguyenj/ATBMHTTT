package com.ltweb_servlet_ecommerce.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;

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
}
