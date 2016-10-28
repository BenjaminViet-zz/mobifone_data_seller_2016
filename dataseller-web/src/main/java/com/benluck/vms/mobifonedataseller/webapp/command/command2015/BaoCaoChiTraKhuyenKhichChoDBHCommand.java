package com.benluck.vms.mobifonedataseller.webapp.command.command2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoChiTraKhuyenKhichChoDBHDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 3/6/15
 * Time: 9:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoChiTraKhuyenKhichChoDBHCommand extends AbstractCommand<BaoCaoChiTraKhuyenKhichChoDBHDTO> {

    public BaoCaoChiTraKhuyenKhichChoDBHCommand(){
          this.pojo = new BaoCaoChiTraKhuyenKhichChoDBHDTO();
    }

    private Date fromDate;
    private Date toDate;

    private Date fromPaymentDate;
    private Date toPaymentDate;

    private Integer loaiBaoBao = 0;


    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getFromPaymentDate() {
        return fromPaymentDate;
    }

    public void setFromPaymentDate(Date fromPaymentDate) {
        this.fromPaymentDate = fromPaymentDate;
    }

    public Date getToPaymentDate() {
        return toPaymentDate;
    }

    public void setToPaymentDate(Date toPaymentDate) {
        this.toPaymentDate = toPaymentDate;
    }

    public Integer getLoaiBaoBao() {
        return loaiBaoBao;
    }

    public void setLoaiBaoBao(Integer loaiBaoBao) {
        this.loaiBaoBao = loaiBaoBao;
    }
}
