package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.core.dto.KHDNDTO;

import java.util.Date;

/**
 * Created by thaihoang on 11/5/2016.
 */
public class KHDNCommand extends AbstractCommand<KHDNDTO> {
    public KHDNCommand(){
        this.pojo = new KHDNDTO();
    }

    private Date issuedContractDate;
    private Date issuedContractDateFrom;
    private Date issuedContractDateTo;

    public Date getIssuedContractDate() {
        return issuedContractDate;
    }

    public void setIssuedContractDate(Date issuedContractDate) {
        this.issuedContractDate = issuedContractDate;
    }

    public Date getIssuedContractDateFrom() {
        return issuedContractDateFrom;
    }

    public void setIssuedContractDateFrom(Date issuedContractDateFrom) {
        this.issuedContractDateFrom = issuedContractDateFrom;
    }

    public Date getIssuedContractDateTo() {
        return issuedContractDateTo;
    }

    public void setIssuedContractDateTo(Date issuedContractDateTo) {
        this.issuedContractDateTo = issuedContractDateTo;
    }
}
