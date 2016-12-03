package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.core.business.OrderDataCodeManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.UsedCardCodeDTO;
import com.benluck.vms.mobifonedataseller.util.ImportFileValidator;
import com.benluck.vms.mobifonedataseller.util.RedisUtil;
import com.benluck.vms.mobifonedataseller.utils.MobiFoneSecurityBase64Util;
import com.benluck.vms.mobifonedataseller.webapp.command.OrderCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/2/16
 * Time: 07:15
 * To change this template use File | Settings | File Templates.
 */
@Component
public class OldOrderValidator extends ApplicationObjectSupport implements Validator{
    private Logger logger = Logger.getLogger(OldOrderValidator.class);

    @Autowired
    private OrderDataCodeManagementLocalBean orderDataCodeService;

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        OrderCommand command = (OrderCommand)o;
        checkRequiredFields(command, errors);
        validateCardCodeList(command);
    }

    /**
     * Validate required fields to make sure it is enough of required properties to insert or update.
     * @param command
     * @param errors
     */
    private void checkRequiredFields(OrderCommand command, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.packageData.packageDataId", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("order.label.package_data")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.khdn.KHDNId", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("order.label.khdn")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.unitPrice", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("order.label.unit_price")}, "non-empty value required.");
    }

    private void validateCardCodeList(OrderCommand command){
        MultipartFile fileUpload = command.getFileUpload();
        List<String> errorCodes = new ArrayList<String>();
        ImportFileValidator.validateFileImport4ExcelExtenstionFormat(fileUpload, errorCodes);
        if(errorCodes.size() > 0){
            command.setErrorMessage(this.getMessageSourceAccessor().getMessage(errorCodes.get(0)));
        }else{
            try{
                command.getPojo().setImportCardCodeList4OldOrder(extractFileImport(fileUpload, command));
                command.getPojo().setQuantity(command.getPojo().getImportCardCodeList4OldOrder().size());
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
    }

    private List<UsedCardCodeDTO> extractFileImport(MultipartFile fileUpload, OrderCommand command)throws Exception{
        List<UsedCardCodeDTO> importKHDNDTOLIst = new ArrayList<UsedCardCodeDTO>();
        HashSet<String> usedCardCodeHS = decodeUsedCardCodeHS();
        HashSet<String> usedCardCodeOldOrderHS = fetchImportedUsedCardCode();
        try{
            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fileUpload.getInputStream());

            // Return first sheet from the XLSX workbook
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);

            // Get interator to all the rows in current sheet
            Iterator<Row> rowIterator = mySheet.iterator();

            HashSet<String> cardCodeHS = new HashSet<String>();
            UsedCardCodeDTO dto = null;
            int cardCodeRowIndexFrom = 5;
            int rowIndex = 0;

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
                    Cell cell = cellIterator.next();

                    if(StringUtils.isBlank(cell.getStringCellValue())){
                        dto = new UsedCardCodeDTO();
                    }else{
                        dto = new UsedCardCodeDTO(cell.getStringCellValue());
                    }

                    boolean  hasError = validateCardCode(dto, cardCodeHS, usedCardCodeHS, usedCardCodeOldOrderHS);
                    if(hasError){
                        command.setErrorMessage(this.getMessageSourceAccessor().getMessage("import_used_card_code.some_error_found_in_import_card_cord_file"));
                    }else{
                        cardCodeHS.add(dto.getCardCode());
                    }
                    break;
                }
                importKHDNDTOLIst.add(dto);
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
        return importKHDNDTOLIst;
    }

    private HashSet<String> fetchImportedUsedCardCode(){
        HashSet<String> usedCardCodeOldOrderHS = this.orderDataCodeService.findCardCodeImported4OldOrder();
        HashSet<String> decodedImportedUsedCardCodeHS = new HashSet<String>();
        Iterator<String> ito = usedCardCodeOldOrderHS.iterator();
        StringBuilder tmpEncodedCardCode = null;

        while(ito.hasNext()){
            tmpEncodedCardCode = new StringBuilder(ito.next());
            decodedImportedUsedCardCodeHS.add(MobiFoneSecurityBase64Util.decode(tmpEncodedCardCode.toString()));
        }

        return decodedImportedUsedCardCodeHS;
    }

    private HashSet<String> decodeUsedCardCodeHS(){
        HashSet<String> usedCardCodeHS = RedisUtil.getUsedCardCodeByKey();
        HashSet<String> decodedUsedCardCodeHS = new HashSet<String>();
        Iterator<String> ito = usedCardCodeHS.iterator();
        StringBuilder tmpEncodedCardCode = null;

        while(ito.hasNext()){
            tmpEncodedCardCode = new StringBuilder(ito.next());
            decodedUsedCardCodeHS.add(MobiFoneSecurityBase64Util.decode(tmpEncodedCardCode.toString()));
        }

        return decodedUsedCardCodeHS;
    }

    /**
     * Validate Card Code and make sure it is not used before.
     * @param dto
     * @param cardCodeHS
     * @return
     */
    private boolean validateCardCode(UsedCardCodeDTO dto, HashSet<String> cardCodeHS, HashSet<String> usedCardCodeHS, HashSet<String> usedCardCodeOldOrderHS){
        if(StringUtils.isBlank(dto.getCardCode())){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import_used_card_code.not_empty_card_code"));
            return true;
        }else if(dto.getCardCode().length() != 12){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import_used_card_code.invalid_card_code_length"));
            return true;
        }else if(cardCodeHS.contains(dto.getCardCode())){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import_used_card_code.duplicated_card_code"));
            return true;
        }else if(!usedCardCodeHS.contains(dto.getCardCode())){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import_used_card_code.not_found_card_code"));
            return true;
        }else if(usedCardCodeOldOrderHS.contains(dto.getCardCode())){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import_used_card_code.used_card_code_old_order"));
            return true;
        }
        return false;
    }
}
