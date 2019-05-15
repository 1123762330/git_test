package com.xn.bmpos.manager.bmposmanager.domain.model;

import lombok.Data;

@Data
public class IndexNodeModel {
    private String node;
    private double fee;
    private double netDifficulty;
    private double confirmed;
}
