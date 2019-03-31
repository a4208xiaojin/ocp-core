package com.nbd.ocp.core.repository.multiTenancy.constant;

/**
 * Created by Administrator on 2019/2/15.
 */
public enum DbTypeEnum {


    /**
     * oracle
     */
    DISCRIMINATOR_MODE("oracle"),
    /**
     * mysql
     */
    SCHEMA_MODE("mysql");

    private String type;


    DbTypeEnum(String type){
        this.type = type;
    }


    public String getType() {
        return type;
    }

}
