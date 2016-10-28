package com.benluck.vms.mobifonedataseller.util;

import com.benluck.vms.mobifonedataseller.common.Constants;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Thoai Bui
 * Date: 4/10/13
 * Time: 3:18 AM
 */
public class FileUtils {
    private static final int BUFSIZE = 2 << 15;

    public static String upload(HttpServletRequest request, String destFolder,
            MultipartFile uplFile) throws IOException, ServletException {
        String fileName = normalizeFilename(uplFile.getOriginalFilename());
        File pathToSave = buildDestinationFile(request, destFolder, fileName);
        // Save to file
        uplFile.transferTo(pathToSave);
        return pathToSave.getName();
    }

    public static File buildDestinationFile(HttpServletRequest request,
            String destFolder, String fileName) {
        ServletContext context = request.getSession().getServletContext();
        String commonDirpath = context.getRealPath(destFolder);
        File baseFile = new File(commonDirpath);
        if (!baseFile.exists()) {
            baseFile.mkdir();
        }

        String newName = "";
        // Parse the request
        // Get just file name of upload file

        newName = normalizeFilename(fileName);
        // Get name withoout exension , get extension
        String nameWithoutExt = normalizeFilename(WebCommonUtil
                .getNameWithoutExtension(fileName));
        String ext = WebCommonUtil.getExtension(fileName);

        // ********************PATH to SAVE FILE************************
        File pathToSave = new File(commonDirpath, fileName);

        int counter = 1;
        // Check if existed, generating new file name
        while (pathToSave.exists()) {
            // New filename = name(counter).ext
            StringBuffer buffer = new StringBuffer();
            buffer.append(nameWithoutExt).append("(").append(counter).append(")").append(".").append(ext);
            newName = buffer.toString();

            // Create new file to receive uploaded file
            pathToSave = new File(commonDirpath, newName);

            counter++;
        }
        return pathToSave;
    }

    private static String normalizeFilename(String fileName) {
        String res = fileName.replaceAll(" ", "_");
        return res;
    }

    public static void remove(String filename){
        File file = new File(filename);
        file.delete();
    }

    public static void downLoadCsvFile(HttpServletResponse response,
                                       String filePath, String downloadFileName) throws IOException {
        ServletOutputStream out = null;
        try {
            FileInputStream in = new FileInputStream(filePath);
            out = response.getOutputStream();

            response.setContentType(String.format("application/octet-stream; name=%s", downloadFileName));
            response.setHeader("Content-Disposition", String.format("attachment; filename=%s", downloadFileName));
            response.setContentLength(in.available());

            byte[] buf = new byte[BUFSIZE];
            int count = 0;
            while ((count = in.read(buf)) != -1) {
                out.write(buf, 0, count);
            }
            in.close();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }


}
