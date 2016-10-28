package com.benluck.vms.mobifonedataseller.util;

import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import jxl.WorkbookSettings;
import jxl.format.*;
import jxl.format.BoldStyle;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.Pattern;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 7/26/12
 * Time: 9:20 AM
 */
public class ExcelUtil {
    public static void addRow(WritableSheet sheet, int startRow, CellValue[] cellValues) throws WriteException {

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setWrap(true);
        cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        NumberFormat towdps = new NumberFormat("#,##0");
        WritableCellFormat towdpsFormat = new WritableCellFormat(towdps);
        towdpsFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        NumberFormat towdps2 = new NumberFormat("#,##0.00");
        WritableCellFormat towdpsFormat2 = new WritableCellFormat(towdps2);
        towdpsFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);

        DateFormat customDateFormat = new DateFormat ("dd MMM yyyy");
        WritableCellFormat dateFormat = new WritableCellFormat (customDateFormat);
        dateFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        for (int i = 0; i < cellValues.length; i++) {
            if (cellValues[i] != null && cellValues[i].getValue() != null) {
                if (cellValues[i].getType().equals(CellDataType.STRING)) {
                    Label label = new Label(i, startRow, String.valueOf(cellValues[i].getValue()), cellFormat);
                    sheet.addCell(label);
                }else if (cellValues[i].getType().equals(CellDataType.INT)) {
                    Number number = new Number(i, startRow, (Integer)cellValues[i].getValue(), towdpsFormat);
                    sheet.addCell(number);
                } else if (cellValues[i].getType().equals(CellDataType.DOUBLE)) {
                    Number number = new Number(i, startRow, (Double)cellValues[i].getValue(), towdpsFormat2);
                    sheet.addCell(number);
                } else if (cellValues[i].getType().equals(CellDataType.DATE)) {
                    Date now = new Date(((Timestamp)cellValues[i].getValue()).getTime());
                    DateTime dateCell = new DateTime(i, startRow, now, dateFormat);
                    sheet.addCell(dateCell);
                }
            }else{
                Label label = new Label(i, startRow, "", cellFormat);
                sheet.addCell(label);
            }
        }
    }
    public static void addRowNoBorder(WritableSheet sheet, int startRow, CellValue[] cellValues, WritableFont writableFont) throws WriteException {

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setWrap(true);
//        cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        addCellList(sheet, writableFont, cellFormat, startRow, cellValues);
    }

    public static void addWithCellFormat(WritableSheet sheet, int startRow, CellValue[] cellValues, WritableCellFormat cellFormat) throws WriteException {
        addCellList(sheet, new WritableFont(WritableFont.TAHOMA), cellFormat, startRow, cellValues);
    }

    public static void addRow(WritableSheet sheet, int startRow, CellValue[] cellValues, WritableCellFormat cellFormat, WritableCellFormat intCellFormat
            , WritableCellFormat doubleCellFormat, WritableCellFormat dateFormat) throws WriteException {
        for (int i = 0; i < cellValues.length; i++) {
            if (cellValues[i] != null && cellValues[i].getValue() != null) {
                if (cellValues[i].getType().equals(CellDataType.STRING)) {
                    Label label = new Label(i, startRow, String.valueOf(cellValues[i].getValue()), cellFormat);
                    sheet.addCell(label);
                } else if (cellValues[i].getType().equals(CellDataType.INT)) {
                    Number number = new Number(i, startRow, (Integer) cellValues[i].getValue(), intCellFormat);
                    sheet.addCell(number);
                } else if (cellValues[i].getType().equals(CellDataType.DOUBLE)) {
                    Number number = new Number(i, startRow, (Double) cellValues[i].getValue(), doubleCellFormat);
                    sheet.addCell(number);
                } else if (cellValues[i].getType().equals(CellDataType.DATE)) {
                    Date now = new Date(((Timestamp) cellValues[i].getValue()).getTime());
                    DateTime dateCell = new DateTime(i, startRow, now, dateFormat);
                    sheet.addCell(dateCell);
                }
            } else {
                Label label = new Label(i, startRow, "", cellFormat);
                sheet.addCell(label);
            }
        }
    }

    public static void addRow(WritableSheet sheet, int startRow, CellValue[] cellValues, WritableCellFormat cellFormat, int start, int end) throws WriteException {
        for (int i = start; i < end; i++) {
            if (cellValues[i] != null && cellValues[i].getValue() != null) {
                Label label = new Label(i, startRow, String.valueOf(cellValues[i].getValue()), cellFormat);
                sheet.addCell(label);
            } else {
                Label label = new Label(i, startRow, "", cellFormat);
                sheet.addCell(label);
            }
        }
    }

    public static void addRowBetweenCols(WritableSheet sheet, int startRow, CellValue[] cellValues, WritableCellFormat cellFormat, int start, int end) throws WriteException {
        int index = 0;
        for (int i = start; i < end; i++) {
            if (cellValues[index] != null && cellValues[index].getValue() != null) {
                Label label = new Label(i, startRow, String.valueOf(cellValues[index].getValue()), cellFormat);
                sheet.addCell(label);
            } else {
                Label label = new Label(i, startRow, "", cellFormat);
                sheet.addCell(label);
            }
            index++;
        }
    }

    public static void addRow(WritableSheet sheet, int startRow, CellValue[] cellValues, WritableFont writableFont) throws WriteException {

        WritableCellFormat cellFormat = new WritableCellFormat(writableFont);
        cellFormat.setWrap(true);
        cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        addCellList(sheet, writableFont, cellFormat, startRow, cellValues);
    }

    public static void addRow(WritableSheet sheet, WritableCellFormat cellFormat, int startRow, CellValue[] cellValues, WritableFont writableFont) throws WriteException {
        cellFormat.setWrap(true);
        cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        addCellList(sheet, writableFont, cellFormat, startRow, cellValues);
    }

    public static void addRowNoWrap(WritableSheet sheet, WritableCellFormat cellFormat, int startRow, CellValue[] cellValues, WritableFont writableFont) throws WriteException {
        addCellList(sheet, writableFont, cellFormat, startRow, cellValues);
    }

    public static void addRow(WritableSheet sheet, int startRow, CellValue[] cellValues, WritableFont writableFont, jxl.format.Colour backGroundColor) throws WriteException {
        WritableCellFormat cellFormat = new WritableCellFormat(writableFont);
        cellFormat.setWrap(true);
        cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        cellFormat.setBackground(backGroundColor, Pattern.GRAY_50);
        addCellList(sheet, writableFont, cellFormat, startRow, cellValues);

    }

    public static void setEncoding4Workbook(WorkbookSettings ws) {
    	String OS = System.getProperty("os.name").toLowerCase();
        if(OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 || OS.indexOf("sunos") >= 0 || OS.indexOf("mac") >= 0) {
        	ws.setEncoding("CP1252");
        }
    }

    public static void addColumn(WritableSheet sheet, int startCol,int startRow, CellValue[] cellValues) throws WriteException {

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setWrap(true);
        cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        NumberFormat towdps = new NumberFormat("#,##0");
        WritableCellFormat towdpsFormat = new WritableCellFormat(towdps);
        towdpsFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        NumberFormat towdps2 = new NumberFormat("#,##0.00");
        WritableCellFormat towdpsFormat2 = new WritableCellFormat(towdps2);
        towdpsFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);

        DateFormat customDateFormat = new DateFormat ("dd MMM yyyy");
        WritableCellFormat dateFormat = new WritableCellFormat (customDateFormat);
        dateFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        for (int i = startRow; i < cellValues.length; i++) {
            if (cellValues[i] != null && cellValues[i].getValue() != null) {
                if (cellValues[i].getType().equals(CellDataType.STRING)) {
                    Label label = new Label(startCol, i, String.valueOf(cellValues[i].getValue()), cellFormat);
                    sheet.addCell(label);
                }else if (cellValues[i].getType().equals(CellDataType.INT)) {
                    Number number = new Number(startCol, i, (Integer)cellValues[i].getValue(), towdpsFormat);
                    sheet.addCell(number);
                } else if (cellValues[i].getType().equals(CellDataType.DOUBLE)) {
                    Number number = new Number(startCol, i, (Double)cellValues[i].getValue(), towdpsFormat2);
                    sheet.addCell(number);
                }else if (cellValues[i].getType().equals(CellDataType.FLOAT)) {
                    Number number = new Number(startCol, i, (Float)cellValues[i].getValue(), towdpsFormat2);
                    sheet.addCell(number);
                }
                else if (cellValues[i].getType().equals(CellDataType.DATE)) {
                    Date now = new Date(((Timestamp)cellValues[i].getValue()).getTime());
                    DateTime dateCell = new DateTime(startCol, i, now, dateFormat);
                    sheet.addCell(dateCell);
                }
            }else{
                Label label = new Label(startCol, i, "", cellFormat);
                sheet.addCell(label);
            }
        }

    }

    private static void addCellList(WritableSheet sheet, WritableFont writableFont, WritableCellFormat cellFormat, int startRow, CellValue[] cellValues) throws WriteException {
        NumberFormat towdps = new NumberFormat("#,##0");
        WritableCellFormat towdpsFormat = new WritableCellFormat(writableFont, towdps);
        towdpsFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        NumberFormat towdps2 = new NumberFormat("#,##0.00");
        WritableCellFormat towdpsFormat2 = new WritableCellFormat(writableFont, towdps2);
        towdpsFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);

        DateFormat customDateFormat = new DateFormat("dd MMM yyyy");
        WritableCellFormat dateFormat = new WritableCellFormat(writableFont, customDateFormat);
        dateFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        for (int i = 0; i < cellValues.length; i++) {
            if (cellValues[i] != null && cellValues[i].getValue() != null) {
                if (cellValues[i].getType().equals(CellDataType.STRING)) {
                    Label label = new Label(i, startRow, String.valueOf(cellValues[i].getValue()), cellFormat);
                    sheet.addCell(label);
                } else if (cellValues[i].getType().equals(CellDataType.INT)) {
                    Number number = new Number(i, startRow, (Integer) cellValues[i].getValue(), towdpsFormat);
                    sheet.addCell(number);
                } else if (cellValues[i].getType().equals(CellDataType.DOUBLE)) {
                    Number number = new Number(i, startRow, (Double) cellValues[i].getValue(), towdpsFormat2);
                    sheet.addCell(number);
                } else if (cellValues[i].getType().equals(CellDataType.DATE)) {
                    Date now = new Date(((Timestamp) cellValues[i].getValue()).getTime());
                    DateTime dateCell = new DateTime(i, startRow, now, dateFormat);
                    sheet.addCell(dateCell);
                }
            } else {
                Label label = new Label(i, startRow, "", cellFormat);
                sheet.addCell(label);
            }
        }
    }
}
