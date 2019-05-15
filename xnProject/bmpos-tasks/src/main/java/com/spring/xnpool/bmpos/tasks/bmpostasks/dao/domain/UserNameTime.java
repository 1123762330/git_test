package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class UserNameTime  implements Serializable {
    private static final long serialVersionUID = 3274883495668712984L;
    private String username;
    private Date time;
}
