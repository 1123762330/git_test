package com.xn.bmpos.manager.bmposmanager.domain.dao;

import lombok.Data;

import javax.xml.soap.Text;

/**
 * 帮助对应的的文本实体类
 */
@Data
public class HelpText {
    private int id;

    private int helpId;

    private String text;

}
