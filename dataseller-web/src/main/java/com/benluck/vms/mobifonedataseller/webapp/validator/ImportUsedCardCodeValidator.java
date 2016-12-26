package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.core.dto.UsedCardCodeDTO;
import com.benluck.vms.mobifonedataseller.util.ImportFileValidator;
import com.benluck.vms.mobifonedataseller.webapp.command.ImportUsedCardCodeCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
    private final Integer rowCardCodeIndexFrom = 10;

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
        ImportFileValidator.validateFileImport4ExcelExtenstionFormat(fileUpload, errorCodes);
        if(errorCodes.size() > 0){
            command.setErrorMessage(this.getMessageSourceAccessor().getMessage(errorCodes.get(0)));
        }else{
            try{
                Object[] importUsedCardCodeObjects = extractFileImport(fileUpload, command);
                command.setErrorImportUsedCardCodeList((List<UsedCardCodeDTO>)importUsedCardCodeObjects[0]);
                command.setImportUsedCardCodeList((List<UsedCardCodeDTO>)importUsedCardCodeObjects[1]);
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
    }

    private Object[] extractFileImport(MultipartFile fileUpload, ImportUsedCardCodeCommand command)throws Exception{
        List<UsedCardCodeDTO> errorImportUsedCardCodeList = new ArrayList<UsedCardCodeDTO>();
        List<UsedCardCodeDTO> importUsedCardCodeList = new ArrayList<UsedCardCodeDTO>();
        try{
            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fileUpload.getInputStream());

            // Return first sheet from the XLSX workbook
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);

            // Get interator to all the rows in current sheet
            Iterator<Row> rowIterator = mySheet.iterator();

            DataFormatter formatter = new DataFormatter();

            HashSet<String> cardCodeHS = new HashSet<String>();
            UsedCardCodeDTO dto = null;
            int cardCodeRowIndexFrom = 5;
            int rowIndex = 0;

            StringBuilder tmpCardCodeValue = null;

            // Traversing over each row of XLSX file
            while(rowIterator.hasNext()){
                dto = null;
                Row row = rowIterator.next();

                if(rowIndex < cardCodeRowIndexFrom){
                    rowIndex++;
                    continue;
                }

                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()){
                    tmpCardCodeValue = new StringBuilder(formatter.formatCellValue(cellIterator.next()));

                    if(StringUtils.isBlank(tmpCardCodeValue.toString())){
                        dto = new UsedCardCodeDTO();
                    }else{
                        dto = new UsedCardCodeDTO(tmpCardCodeValue.toString());
                    }

                    boolean  hasError = validateFields(dto, cardCodeHS);
                    if(hasError){
                        command.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.some_error_found_in_import_file"));
                        importUsedCardCodeList.add(dto);
                    }else{
                        cardCodeHS.add(dto.getCardCode());
                    }
                    break;
                }
                importUsedCardCodeList.add(dto);
                rowIndex++;
            }

            if(rowIndex < cardCodeRowIndexFrom){
                command.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.exceed_min_row_to_read"));
                command.setHasError(true);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            command.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.error_unknown"));
            command.setHasError(true);
        }
        return new Object[]{errorImportUsedCardCodeList, importUsedCardCodeList};
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
