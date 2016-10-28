package com.benluck.vms.mobifonedataseller.util.csv;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import org.apache.commons.beanutils.PropertyUtils;

import au.com.bytecode.opencsv.CSVWriter;

public class ReportCsvWriter {
	
	private ReportCsvInfo reportCsvInfo;
	private ComplexReportCsvInfo complexReportCsvInfo;
	private static ReportCsvWriter reportCsvWriter;
	
	private ReportCsvWriter(ReportCsvInfo reportCsvInfo) {
		super();
		this.reportCsvInfo = reportCsvInfo;
	}
	
	private ReportCsvWriter(ComplexReportCsvInfo complexReportCsvInfo) {
		super();
		this.complexReportCsvInfo = complexReportCsvInfo;
	}

	public static ReportCsvWriter getReportCsvWriterInstance(ReportCsvInfo reportCsvInfo){
		if (reportCsvWriter == null) {
			reportCsvWriter = new ReportCsvWriter(reportCsvInfo);
		} else {
			reportCsvWriter.reportCsvInfo = reportCsvInfo;
		}
		return reportCsvWriter;
	}
	
	public static ReportCsvWriter getComplexReportCsvWriterInstance(ComplexReportCsvInfo complexReportCsvInfo){
		if (reportCsvWriter == null) {
			reportCsvWriter = new ReportCsvWriter(complexReportCsvInfo);
		} else {
			reportCsvWriter.complexReportCsvInfo = complexReportCsvInfo;
		}
		return reportCsvWriter;
	}

    public void reportWrite(String fileName, List<Object> lstReportDTO) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        reportWrite(new FileOutputStream(fileName), lstReportDTO);
    }

    public void complexReportWrite(String fileName, List<Object> lstComplexReportDTO) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        complexReportWrite(new FileOutputStream(fileName), lstComplexReportDTO);
    }

    public void reportWrite(OutputStream outputStream, List<Object> lstReportDTO) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        if(lstReportDTO != null) {
            CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream, "UTF-8"), ',', CSVWriter.DEFAULT_QUOTE_CHARACTER);

            writer.writeNext(reportCsvInfo.getHeaders());
            List<String> lstValues;
            String[] entries = new String[]{};
            for (Object reportCsvDTO : lstReportDTO) {
                lstValues = new ArrayList<String>();
                for (int i = 0; i < reportCsvInfo.getCsvFields().length; i++) {
                    String csvField = reportCsvInfo.getCsvFields()[i];
                    Object value = PropertyUtils.getProperty(reportCsvDTO, csvField);
                    int decimalToRound = reportCsvInfo.getCsvFieldDecimalRound(csvField);
                    if (decimalToRound != -1 && value != null) {
                        lstValues.add(CommonUtil.roundUp(Double.parseDouble(value.toString()) * reportCsvInfo.getFactor(csvField), decimalToRound) + reportCsvInfo.getSymbol(csvField) );
                    } else {
                        lstValues.add((value != null)? value.toString() + reportCsvInfo.getSymbol(csvField): (csvField.equals("gradeLevel")?"N/A":""));
                    }
                }
                entries = lstValues.toArray(entries);
                writer.writeNext(entries);
            }
            writer.close();
        }
    }

    public void complexReportWrite(OutputStream outputStream, List<Object> lstComplexReportDTO) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if(lstComplexReportDTO != null) {
            CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream), ',', CSVWriter.DEFAULT_QUOTE_CHARACTER);
            writer.writeNext(complexReportCsvInfo.getRootHeaders());
            List<String> lstRootValues;
            String[] rootEntries = new String[]{};
            for (Object complexReportCsvDTO : lstComplexReportDTO) {
                lstRootValues = new ArrayList<String>();
                for (int i = 0; i < complexReportCsvInfo.getRootCsvFields().length; i++) {
                    String rootCsvField = complexReportCsvInfo.getRootCsvFields()[i];
                    Object value = PropertyUtils.getProperty(complexReportCsvDTO, rootCsvField);
                    int decimalToRound = complexReportCsvInfo.getCsvFieldDecimalRound(rootCsvField);
                    if (decimalToRound != -1 && value != null) {
                        lstRootValues.add(CommonUtil.roundUp(Double.parseDouble(value.toString()) * complexReportCsvInfo.getFactor(rootCsvField), decimalToRound) + reportCsvInfo.getSymbol(rootCsvField) );
                    } else {
                        lstRootValues.add((value != null)? value.toString() + complexReportCsvInfo.getSymbol(rootCsvField): "");
                    }
                }
                rootEntries = lstRootValues.toArray(rootEntries);
                writer.writeNext(rootEntries);
                Object objChildNodes = PropertyUtils.getProperty(complexReportCsvDTO, complexReportCsvInfo.getChildNodes());
                List<Object> childNodes = (List<Object>) objChildNodes;
                writer.writeNext(complexReportCsvInfo.getHeaders());
                List<String> lstValues;
                String[] entries = new String[]{};
                for (Object reportCsvDTO : childNodes) {
                    lstValues = new ArrayList<String>();
                    for (int i = 0; i < complexReportCsvInfo.getCsvFields().length; i++) {
                        String csvField = complexReportCsvInfo.getCsvFields()[i];
                        Object value = PropertyUtils.getProperty(reportCsvDTO, csvField);
                        int decimalToRound = complexReportCsvInfo.getCsvFieldDecimalRound(csvField);
                        if (decimalToRound != -1 && value != null) {
                            lstValues.add(CommonUtil.roundUp(Double.parseDouble(value.toString()) * complexReportCsvInfo.getFactor(csvField), decimalToRound) + complexReportCsvInfo.getSymbol(csvField) );
                        } else {
                            lstValues.add((value != null)? value.toString() + complexReportCsvInfo.getSymbol(csvField): "");
                        }
                    }
                    entries = lstValues.toArray(entries);
                    writer.writeNext(entries);
                }
            }

            writer.close();
        }
    }
}
