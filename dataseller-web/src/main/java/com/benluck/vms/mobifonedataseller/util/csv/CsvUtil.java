package com.benluck.vms.mobifonedataseller.util.csv;

import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.util.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.util.List;

public class CsvUtil {
    private static transient final Log log = LogFactory.getLog(CsvUtil.class);

    public static void doExport(ReportCsvInfo csvInfo, OutputStream outputStream, List<Object> lstReport) throws Exception {
        ReportCsvWriter csvWriter = ReportCsvWriter.getReportCsvWriterInstance(csvInfo);
        csvWriter.reportWrite(outputStream, lstReport);
    }

    public static void doComplexExport(ComplexReportCsvInfo csvInfo, OutputStream outputStream, List<Object> lstReport) throws Exception {
        ReportCsvWriter csvWriter = ReportCsvWriter.getComplexReportCsvWriterInstance(csvInfo);
        csvWriter.complexReportWrite(outputStream, lstReport);
    }

    public static void doExport(ReportCsvInfo csvInfo, HttpServletRequest request, HttpServletResponse response, List<Object> lstReport, String fileName) throws Exception {
        response.setCharacterEncoding("UTF-8");
        String destFolder = CommonUtil.getBaseFolder() + CommonUtil.getTempFolderName();
        String systemFileName = fileName + System.currentTimeMillis() + ".csv";
        File pathToSave = FileUtils.buildDestinationFile(request, destFolder, systemFileName);
        String destFileName = request.getSession().getServletContext().getRealPath(destFolder + "/" + pathToSave.getName());
        ReportCsvWriter csvWriter = ReportCsvWriter.getReportCsvWriterInstance(csvInfo);
        log.debug("Save temp file: " + destFileName);
        csvWriter.reportWrite(destFileName, lstReport);
        FileUtils.downLoadCsvFile(response, destFileName, systemFileName);
        log.debug("Remove temp file: " + destFileName);
        FileUtils.remove(destFileName);
    }
    
    public static void doComplexExport(ComplexReportCsvInfo csvInfo, HttpServletRequest request, HttpServletResponse response, List<Object> lstReport, String fileName) throws Exception {
        String destFolder = CommonUtil.getBaseFolder() + CommonUtil.getTempFolderName();
        String systemFileName = fileName + System.currentTimeMillis() + ".csv";
        File pathToSave = FileUtils.buildDestinationFile(request, destFolder, systemFileName);
        String destFileName = request.getSession().getServletContext().getRealPath(destFolder + "/" + pathToSave.getName());
        ReportCsvWriter csvWriter = ReportCsvWriter.getComplexReportCsvWriterInstance(csvInfo);
        log.debug("Save temp file: " + destFileName);
        csvWriter.complexReportWrite(destFileName, lstReport);
        FileUtils.downLoadCsvFile(response, destFileName, systemFileName);
        log.debug("Remove temp file: " + destFileName);
        FileUtils.remove(destFileName);
    }
}
