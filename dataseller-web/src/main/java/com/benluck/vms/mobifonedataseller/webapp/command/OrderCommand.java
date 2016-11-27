package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/2/16
 * Time: 07:14
 * To change this template use File | Settings | File Templates.
 */
public class OrderCommand extends AbstractCommand<OrderDTO>{
    public OrderCommand(){
        this.pojo = new OrderDTO();
    }

    public Date issuedDate;
    private Date shippingDate;
    private Integer exportOptionType = Constants.ADMIN_EXPORT_4_KHDN;
    private MultipartFile fileUpload;

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public Integer getExportOptionType() {
        return exportOptionType;
    }

    public void setExportOptionType(Integer exportOptionType) {
        this.exportOptionType = exportOptionType;
    }

    public MultipartFile getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(MultipartFile fileUpload) {
        this.fileUpload = fileUpload;
    }
}
