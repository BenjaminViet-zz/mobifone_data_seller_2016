package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.dto.ImportKHDNDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/11/16
 * Time: 21:52
 * To change this template use File | Settings | File Templates.
 */
public class ImportKHDNCommand extends AbstractCommand<ImportKHDNDTO>{
    public ImportKHDNCommand(){
        this.pojo = new ImportKHDNDTO();
    }

    private List<String> headerList;
    private Integer stepImportIndex = Constants.IMPORT_ORDER_STEP_1_CHOOSE_FILE;
    private MultipartFile fileUpload;
    private List<ImportKHDNDTO> importKHDNDTOList;

    public Integer getStepImportIndex() {
        return stepImportIndex;
    }

    public void setStepImportIndex(Integer stepImportIndex) {
        this.stepImportIndex = stepImportIndex;
    }

    public List<String> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(List<String> headerList) {
        this.headerList = headerList;
    }

    public MultipartFile getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(MultipartFile fileUpload) {
        this.fileUpload = fileUpload;
    }

    public List<ImportKHDNDTO> getImportKHDNDTOList() {
        return importKHDNDTOList;
    }

    public void setImportKHDNDTOList(List<ImportKHDNDTO> importKHDNDTOList) {
        this.importKHDNDTOList = importKHDNDTOList;
    }
}
