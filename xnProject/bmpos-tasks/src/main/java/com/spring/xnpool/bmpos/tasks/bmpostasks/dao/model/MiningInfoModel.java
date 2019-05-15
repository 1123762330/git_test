package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class MiningInfoModel {

    private Integer blocks;

    private Integer currentblockweight;



    private Integer currentblocktx;


    private BigDecimal difficulty;


    private String networkHashPs;

    private String pooledTx;

    private String chain;

    private String warnings;

    @Override
    public String toString() {
        return "MiningInfoModel{" +
                "blocks=" + blocks +
                ", currentblockweight=" + currentblockweight +
                ", currentblocktx=" + currentblocktx +
                ", difficulty='" + difficulty + '\'' +
                ", networkHashPs='" + networkHashPs + '\'' +
                ", pooledTx='" + pooledTx + '\'' +
                ", chain='" + chain + '\'' +
                ", warnings='" + warnings + '\'' +
                '}';
    }

}
