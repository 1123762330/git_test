package com.xnpool.account.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Base implements Serializable {
    private static final long serialVersionUID = 1990114085616615106L;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
