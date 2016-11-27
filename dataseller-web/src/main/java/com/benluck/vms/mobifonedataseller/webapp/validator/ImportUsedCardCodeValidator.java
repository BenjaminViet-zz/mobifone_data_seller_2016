package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.core.dto.UsedCardCodeDTO;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.ImportFileValidator;
import com.benluck.vms.mobifonedataseller.webapp.command.ImportUsedCardCodeCommand;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/26/16
 * Time: 21:16
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ImportUsedCardCodeValidator extends ApplicationObjectSupport implements Validator{
    private Logger logger = Logger.getLogger(ImportUsedCardCodeValidator.class);

    @Override
    public boolean supports(Class<?> aClass) {
        return ImportUsedCardCodeValidator.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ImportUsedCardCodeCommand command = (ImportUsedCardCodeCommand)o;
        validateCardCodeList(command);
    }

    private void validateCardCodeList(ImportUsedCardCodeCommand command){
        MultipartFile fileUpload = command.getFileUpload();
        List<String> errorCodes = new ArrayList<String>();
        ImportFileValidator.validateFileImport4ExcelFormat(fileUpload, errorCodes);
        if(errorCodes.size() > 0){
            command.setErrorMessage(this.getMessageSourceAccessor().getMessage(errorCodes.get(0)));
        }else{
            try{
                command.setImportUsedCardCodeList(extractFileImport(fileUpload, command));
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
    }

    private List<UsedCardCodeDTO> extractFileImport(MultipartFile fileUpload, ImportUsedCardCodeCommand command)throws Exception{
        List<UsedCardCodeDTO> importKHDNDTOLIst = new ArrayList<UsedCardCodeDTO>();
        try{
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook workbook = Workbook.getWorkbook(fileUpload.getInputStream(), ws);
            Sheet s = workbook.getSheet(0);

            int rowCount = s.getRows();
            int minRowIndex = 7;
            int maxColIndex = 1;

            Cell rowData[] = new Cell[1];
            UsedCardCodeDTO dto = null;
            HashSet<String> cardCodeHS = new HashSet<String>();

            // Read Card Code list
            if(rowCount > minRowIndex && s.getColumns() >= maxColIndex){
                for (int rowIndex = minRowIndex; rowIndex < rowCount; rowIndex++){
                    rowData = s.getRow(rowIndex);

                    dto = new UsedCardCodeDTO(rowData[0].getContents().trim());

                    boolean  hasError = validateFields(dto, cardCodeHS);
                    importKHDNDTOLIst.add(dto);

                    if(hasError){
                        command.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.some_error_found_in_import_file"));
                    }else{
                        cardCodeHS.add(dto.getCardCode());
                    }
                }
            }else{
                command.setErrorMessage(this.getMessageSourceAccessor().getMessage("import_used_card_code.no_data_import"));
                command.setHasError(true);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            command.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.error_unknown"));
            command.setHasError(true);
        }
        return importKHDNDTOLIst;
    }

    /**
     * Validate Card Code and make sure it is not used before.
     * @param dto
     * @param cardCodeHS
     * @return
     */
    private boolean validateFields(UsedCardCodeDTO dto, HashSet<String> cardCodeHS){
        if(StringUtils.isBlank(dto.getCardCode())){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import_used_card_code.not_empty_card_code"));
            return true;
        }else if(dto.getCardCode().length() != 12){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import_used_card_code.invalid_card_code_length"));
            return true;
        }else if(cardCodeHS.contains(dto.getCardCode())){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import_used_card_code.duplicated_card_code"));
            return true;
        }
        return false;
    }
}
