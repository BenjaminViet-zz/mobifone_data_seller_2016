package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.core.dto.MBDReportGeneralExpenseDTO;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 22:20
 * To change this template use File | Settings | File Templates.
 */
public class ReportGeneralExpenseCommand extends AbstractCommand<MBDReportGeneralExpenseDTO>{
    public ReportGeneralExpenseCommand(){
        this.pojo = new MBDReportGeneralExpenseDTO();
    }
}
