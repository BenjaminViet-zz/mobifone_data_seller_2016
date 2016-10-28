package com.benluck.vms.mobifonedataseller.dialect;

import org.hibernate.dialect.Oracle10gDialect;

import java.sql.Types;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 11/30/13
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class VMSOracleDialect extends Oracle10gDialect {
    public VMSOracleDialect() {
        super();
        registerColumnType(Types.DOUBLE, "float");
    }
}
