package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.ImportKHDNDTO;
import com.benluck.vms.mobifonedataseller.core.dto.KHDNDTO;
import com.benluck.vms.mobifonedataseller.util.ImportFileValidator;
import com.benluck.vms.mobifonedataseller.webapp.command.ImportKHDNCommand;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
        ImportFileValidator.validateFileImport4ExcelExtenstionFormat(fileUpload, errorCodes);
        if(errorCodes.size() > 0){
            command.setErrorMessage(this.getMessageSourceAccessor().getMessage(errorCodes.get(0)));
        }else{
            try{
                command.setImportKHDNDTOList(extractFileImport(fileUpload, command));
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
    }

    private List<ImportKHDNDTO> extractFileImport(MultipartFile fileUpload, ImportKHDNCommand command)throws Exception{
        HashSet<String> shopCodeHS = new HashSet<String>();
        HashSet<String> mstHS = new HashSet<String>();
        HashSet<String> gpkdHS = new HashSet<String>();
        HashSet<String> stbHS = new HashSet<String>();
        List<ImportKHDNDTO> importKHDNDTOLIst = new ArrayList<ImportKHDNDTO>();
        try{
            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fileUpload.getInputStream());

            // Return first sheet from the XLSX workbook
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);

            // Get interator to all the rows in current sheet
            Iterator<Row> rowIterator = mySheet.iterator();

            int khdnRowHasValueIndexFrom = 3;
            int headerRowHasValueIndex = 2;
            ImportKHDNDTO dto = null;
            int rowIndex = 0;

            StringBuilder shopCode = null;
            StringBuilder shopName = null;
            StringBuilder mst = null;
            StringBuilder gpkd = null;
            StringBuilder issuedContractDate = null;
            StringBuilder stb_vas = null;
            Cell cell = null;

            // Traversing over each row of XLSX file
            while(rowIterator.hasNext()){
                dto = null;
                Row row = rowIterator.next();

                if(rowIndex < khdnRowHasValueIndexFrom){
                    rowIndex++;
                    continue;
                }

                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                int colIndex = 0;

                while (cellIterator.hasNext()){
                    cell = cellIterator.next();

                    switch (colIndex){
                        case 0:
                            shopCode = new StringBuilder(cell.getStringCellValue());
                            break;
                        case 1:
                            shopName = new StringBuilder(cell.getStringCellValue());
                            break;
                        case 2:
                            mst = new StringBuilder(cell.getStringCellValue());
                            break;
                        case 3:
                            gpkd = new StringBuilder(cell.getStringCellValue());
                            break;
                        case 4:
                            issuedContractDate = new StringBuilder(cell.getStringCellValue());
                            break;
                        case 5:
                            stb_vas = new StringBuilder(cell.getStringCellValue());
                            break;
                    }
                    colIndex++;
                }


                if(rowIndex == headerRowHasValueIndex){
                    // build header list
                    List<String> headerList = new ArrayList<String>();
                    headerList.add(shopCode.toString());
                    headerList.add(shopName.toString());
                    headerList.add(mst.toString());
                    headerList.add(gpkd.toString());
                    headerList.add(issuedContractDate.toString());
                    headerList.add(stb_vas.toString());
                    command.setHeaderList(headerList);

                }else{
                    // build khdn content list
                    dto = new ImportKHDNDTO(shopCode.toString(), shopName.toString(), mst.toString(), gpkd.toString(), issuedContractDate.toString(), stb_vas.toString());

                    boolean  hasError = validateRequiredFields(dto, shopCodeHS, mstHS, gpkdHS, stbHS, rowIndex);
                    if(hasError){
                        command.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.some_error_found_in_import_file"));
                    }else{
                        shopCodeHS.add(dto.getShopCode());
                        gpkdHS.add(dto.getGpkd());
                        stbHS.add(dto.getStb_vas());
                        mstHS.add(dto.getMst());
                    }
                }

                importKHDNDTOLIst.add(dto);
                rowIndex++;
            }

            if(rowIndex < khdnRowHasValueIndexFrom){
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

    private boolean validateRequiredFields(ImportKHDNDTO dto, HashSet<String> shopCodeHS, HashSet<String> mstHS, HashSet<String> gpkdHS, HashSet<String> stbHS, int rowIndex) throws ObjectNotFoundException{
        KHDNDTO khdnDTO = null;

        if(StringUtils.isBlank(dto.getIssuedContractDateStr())){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.not_empty_issuedContractDate"));
            return true;
        }else{
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date d = (Date) format.parse(dto.getIssuedContractDateStr());
                dto.setIssuedContractDate(new Timestamp(d.getTime()));
            } catch (Exception e) {
                logger.error("Invalid date format [" + dto.getIssuedContractDateStr() + "] of ShopCode at rowIndex: " + rowIndex);
                logger.error(e.getMessage());
                return true;
            }
        }

        if(StringUtils.isBlank(dto.getShopCode())){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.not_empty_shop_code"));
            return true;
        }else{
            try{
                khdnDTO = null;
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
        }else{
            try{
                khdnDTO = null;
                khdnDTO = khdnService.findEqualUnique("mst", dto.getMst().trim());
            }catch (ObjectNotFoundException oe){}

            if(khdnDTO != null || mstHS.contains(dto.getMst().trim())){
                dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.duplicated_mst"));
                return true;
            }
        }

        if(StringUtils.isBlank(dto.getGpkd())){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.not_empty_gpkd"));
            return true;
        }else{
            try{
                khdnDTO = null;
                khdnDTO = khdnService.findEqualUnique("gpkd", dto.getGpkd().trim());
            }catch (ObjectNotFoundException oe){}

            if(khdnDTO != null || gpkdHS.contains(dto.getGpkd().trim())){
                dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.duplicated_gpkd"));
                return true;
            }
        }

        if(StringUtils.isBlank(dto.getStb_vas())){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.not_empty_stb_vas"));
            return true;
        }else{
            try{
                khdnDTO = null;
                khdnDTO = khdnService.findEqualUnique("stb_vas", dto.getStb_vas().trim());
            }catch (ObjectNotFoundException oe){}

            if(khdnDTO != null || stbHS.contains(dto.getStb_vas().trim())){
                dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.duplicated_stb_vas"));
                return true;
            }
        }

        if(StringUtils.isBlank(dto.getStb_vas())){
            dto.setErrorMessage(this.getMessageSourceAccessor().getMessage("import.not_empty_stb"));
            return true;
        }
        return false;
    }
}
