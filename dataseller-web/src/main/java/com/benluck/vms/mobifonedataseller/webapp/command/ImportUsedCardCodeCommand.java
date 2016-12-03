package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.dto.UsedCardCodeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/26/16
 * Time: 21:11
 * To change this template use File | Settings | File Templates.
 */
public class ImportUsedCardCodeCommand extends AbstractCommand<UsedCardCodeDTO>{
    public ImportUsedCardCodeCommand(){
        this.pojo = new UsedCardCodeDTO();
    }

    private Integer stepImportIndex = Constants.IMPORT_CARD_CODE_STEP_1_CHOOSE_FILE;
    private MultipartFile fileUpload;
    private List<UsedCardCodeDTO> importUsedCardCodeList;
    private Boolean importUsedCardCode = false;
    private String csvFileUploadPath;

    public Integer getStepImportIndex() {
        return stepImportIndex;
    }

    public void setStepImportIndex(Integer stepImportIndex) {
        this.stepImportIndex = stepImportIndex;
    }

    public MultipartFile getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(MultipartFile fileUpload) {
        this.fileUpload = fileUpload;
    }

    public List<UsedCardCodeDTO> getImportUsedCardCodeList() {
        return importUsedCardCodeList;
    }

    public void setImportUsedCardCodeList(List<UsedCardCodeDTO> importUsedCardCodeList) {
        this.importUsedCardCodeList = importUsedCardCodeList;
    }

    public Boolean getImportUsedCardCode() {
        return importUsedCardCode;
    }

    public void setImportUsedCardCode(Boolean importUsedCardCode) {
        this.importUsedCardCode = importUsedCardCode;
    }

    public String getCsvFileUploadPath() {
        return csvFileUploadPath;
    }

    public void setCsvFileUploadPath(String csvFileUploadPath) {
        this.csvFileUploadPath = csvFileUploadPath;
    }
}
