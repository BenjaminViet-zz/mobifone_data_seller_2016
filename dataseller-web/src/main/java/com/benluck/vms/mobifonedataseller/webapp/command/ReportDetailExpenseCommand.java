package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.core.dto.MBDReportDetailExpenseDTO;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 22:20
 * To change this template use File | Settings | File Templates.
 */
public class ReportDetailExpenseCommand extends AbstractCommand<MBDReportDetailExpenseDTO>{
    public ReportDetailExpenseCommand(){
        this.pojo = new MBDReportDetailExpenseDTO();
    }
}