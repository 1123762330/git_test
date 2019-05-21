package com.xn.bmpos.manager.bmposmanager.domain.model;

import lombok.Data;

@Data
public class HttpResultTokenModel {
    private String access_token;
    private String token_type;
    private String scope;
    private String is_app;
    private String userPhone;
    private String user_nickname;
    private String userEmail;
    private String jti;
}
