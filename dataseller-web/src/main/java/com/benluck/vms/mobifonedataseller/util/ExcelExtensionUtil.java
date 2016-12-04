package com.benluck.vms.mobifonedataseller.util;

import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import org.apache.poi.xssf.usermodel.*;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 12/4/16
 * Time: 08:37
 * To change this template use File | Settings | File Templates.
 */
public class ExcelExtensionUtil {

    public static void addRow(XSSFRow row, CellValue[] cellValues, XSSFCellStyle stringCellFormat, XSSFCellStyle intCellFormat, XSSFCellStyle doubleCellFormat) {
        XSSFCell cell = null;
        for (int i = 0; i < cellValues.length; i++) {
            cell = row.createCell(i);
            if (cellValues[i] != null && cellValues[i].getValue() != null) {
                if (cellValues[i].getType().equals(CellDataType.STRING)) {
                    cell.setCellValue(cellValues[i].getValue().toString());
                    cell.setCellStyle(stringCellFormat);
                } else if (cellValues[i].getType().equals(CellDataType.INT)) {
                    cell.setCellValue(Integer.valueOf(cellValues[i].getValue().toString()));
                    cell.setCellStyle(intCellFormat);
                } else if (cellValues[i].getType().equals(CellDataType.DOUBLE)) {
                    cell.setCellValue(Double.valueOf(cellValues[i].getValue().toString()));
                    cell.setCellStyle(doubleCellFormat);
                }
            } else {
                cell.setCellValue("");
            }
        }
    }
}
