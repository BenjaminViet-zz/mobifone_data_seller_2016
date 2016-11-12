package com.benluck.vms.mobifonedataseller.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Quoc Viet
 * Date: 3/28/14
 * Time: 11:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class ImportFileValidator {
    public static void validateFileImport4ExcelFormat(MultipartFile file, List<String> errorCode){
        if(file == null || file.getSize() == 0 || !file.getOriginalFilename().endsWith(".xls")){
            errorCode.add("import.error.invalid-format_excel_file");
        }
    }

    public static boolean checkValidFileFormat(MultipartFile file, String fileType){
        if(file == null || file.getSize() == 0 || !file.getOriginalFilename().endsWith(fileType)){
            return false;
        }
        return true;
    }
}
