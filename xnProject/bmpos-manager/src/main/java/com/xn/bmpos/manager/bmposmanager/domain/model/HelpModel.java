package com.xn.bmpos.manager.bmposmanager.domain.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HelpModel {
    private int id;
    private String name;
    private int pid;
    private int orderId;
    private List<HelpModel> Children;


}
