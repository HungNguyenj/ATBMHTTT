package com.ltweb_servlet_ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DSModel extends AbstractModel<DSModel>{
    private Long user_id;
    private String private_key;
    private String public_key;
    private int expiredTime;
    private int used;
}
