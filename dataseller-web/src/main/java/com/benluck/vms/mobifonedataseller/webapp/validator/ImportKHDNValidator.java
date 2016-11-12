package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.ImportKHDNDTO;
import com.benluck.vms.mobifonedataseller.core.dto.KHDNDTO;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.ImportFileValidator;
import com.benluck.vms.mobifonedataseller.webapp.command.ImportKHDNCommand;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import javax.ejb.ObjectNotFoundException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/11/16
 * Time: 21:34
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ImportKHDNValidator extends ApplicationObjectSupport implements Validator{
    private Logger logger = Logger.getLogger(ImportKHDNValidator.class);

    @Autowired
    private KHDNManagementLocalBean khdnService;

    @Override
    public boolean supports(Class<?> aClass) {
        return ImportKHDNCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ImportKHDNCommand command = (ImportKHDNCommand) o;
        checkDuplicateKHDNData(command);
    }

    private void checkDuplicateKHDNData(ImportKHDNCommand command){
        MultipartFile fileUpload = command.getFileUpload();
        List<String> errorCodes = new ArrayList<String>();
        ImportFileValidator.validateFileImport4ExcelFormat(fileUpload, errorCodes);
        if(errorCodes.size() > 0){
            command.setErrorMessage(this.getMessageSourceAccessor().getMessage(errorCodes.get(0)));
        }else{
            try{
                List<ImportKHDNDTO> importKHDNDTOList = extractFileImport(fileUpload, command);
                if(StringUtils.isBlank(command.getErrorMessage())){
                    command.setImportKHDNDTOList(importKHDNDTOList);
                }
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
    }

    private List<ImportKHDNDTO> extractFileImport(MultipartFile fileUpload, ImportKHDNCommand command)throws Exception{
        HashSet<String> shopCodeHS = new HashSet<String>();
        List<ImportKHDNDTO> importKHDNDTOLIst = new ArrayList<ImportKHDNDTO>();
        try{
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook workbook = Workbook.getWorkbook(fileUpload.getInputStream(), ws);
            Sheet s = workbook.getSheet(0);

            int rowCount = s.getRows();
            int minRowIndex = 5;
            int maxColIndex = 6;
            int headerRowIndex = 4;

            Cell rowData[] = new Cell[7];
            ImportKHDNDTO dto = null;

            // Read contents for header list
            List<String> headerList = new ArrayList<String>();
            for(int colIndex = 0 ; colIndex < maxColIndex; colIndex ++){
                headerList.add(s.getRow(headerRowIndex)[colIndex].getContents());
            }
            command.setHeaderList(headerList);

            StringBuilder shopCode = null;
            StringBuilder shopName = null;
            StringBuilder mst = null;
            StringBuilder gpkd = null;
            StringBuilder issuedContractDate = null;
            StringBuilder stb_vas = null;

            // Read Data list for importing KHDN
            if(rowCount > minRowIndex && s.getColumns() >= maxColIndex){
                for (int rowIndex = minRowIndex; rowIndex < rowCount; rowIndex++){
                    rowData = s.getRow(rowIndex);

                    shopCode = new StringBuilder(rowData[0].getContents().trim());
                    shopName = new StringBuilder(rowData[1].getContents().trim());
                    mst = new StringBuilder(rowData[2].getContents().trim());
                    gpkd = new StringBuilder(rowData[3].getContents().trim());
                    issuedContractDate = new StringBuilder(rowData[4].getContents().trim());
                    stb_vas = new StringBuilder(rowData[5].getContents().trim());

                    dto = new ImportKHDNDTO(shopCode.toString(), shopName.toString(), mst.toString(), gpkd.toString(), issuedContractDate.toString(), stb_vas.toString());

                    boolean  hasError = validateRequiredFields(dto, shopCodeHS, rowIndex);
                    importKHDNDTOLIst.add(dto);

                    if(hasError){
                        command.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.some_error_found_in_import_file"));
                    }
                }
            }else{
                command.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.exceed_min_row_to_read"));
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            command.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.error_unknown"));
        }
        return importKHDNDTOLIst;
    }

    private boolean validateRequiredFields(ImportKHDNDTO dto, HashSet<String> shopCodeHS, int rowIndex) throws ObjectNotFoundException{
        if(StringUtils.isBlank(dto.getShopCode())){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.not_empty_shop_code"));
            return true;
        }else if(StringUtils.isNotBlank(dto.getShopCode())){
            KHDNDTO khdnDTO = null;
            try{
                khdnDTO = khdnService.findEqualUnique("shopCode", dto.getShopCode().trim());
            }catch (ObjectNotFoundException oe){}

            if(khdnDTO != null || shopCodeHS.contains(dto.getShopCode().trim())){
                dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.duplicated_shop_code"));
                return true;
            }
        }
        if(StringUtils.isBlank(dto.getName())){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.not_empty_shop_name"));
            return true;
        }
        if(StringUtils.isBlank(dto.getMst())){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.not_empty_mst"));
            return true;
        }
        if(StringUtils.isBlank(dto.getGpkd())){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.not_empty_gpkd"));
            return true;
        }if(StringUtils.isBlank(dto.getIssuedContractDateStr())){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.not_empty_issuedContractDate"));
            return true;
        }
        if(StringUtils.isBlank(dto.getStb_vas())){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.not_empty_stb"));
            return true;
        }

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = (Date) format.parse(dto.getIssuedContractDateStr());
            dto.setIssuedContractDate(new Timestamp(d.getTime()));
        } catch (Exception e) {
            logger.error("Invalid date format [" + dto.getIssuedContractDateStr() + "] of ShopCode at rowIndex: " + rowIndex);
            logger.error(e.getMessage());
            return true;
        }
        return false;
    }
}
