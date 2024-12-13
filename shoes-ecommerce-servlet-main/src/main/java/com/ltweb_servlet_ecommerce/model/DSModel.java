package com.ltweb_servlet_ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;
import java.security.PublicKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DSModel {
    private String privateKey;
    private String publicKey;
    private String sign;
}
