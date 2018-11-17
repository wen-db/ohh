/**
 * Copyright (C), 2011-2018, 微贷网.
 */
package test;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 文得保 2018/11/9.
 */
public class PhotoDownload {

    public static void main(String[] args) throws Exception {
        String excel = "C:\\Users\\wendb\\Desktop\\云南信托url.xlsx";
        String folder = "C:\\Users\\wendb\\Desktop\\云南信托1\\";
        int pageSize = 500;
        new PhotoDownload().d(excel, folder, pageSize);
    }

    private void d(String sourceExcel, String downloadFolder, int pageSize) throws Exception {
        Workbook wb = new XSSFWorkbook(new FileInputStream(new File(sourceExcel)));
        Sheet sheet = wb.getSheetAt(0);
        int maxRowNum = sheet.getLastRowNum();
        File f = new File(downloadFolder);
        if (!f.exists()) {
            f.mkdirs();
        }
        for (int i = 1; i < maxRowNum; i += pageSize) {
            new Thread(new DownloadObj(sheet, downloadFolder, i, pageSize)).start();
        }
    }

    private static byte[] downloadImage(String strUrl) throws Exception {
        URL url = new URL(strUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
        return readInputStream(inStream);

    }

    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    class DownloadObj implements Runnable {
        private Sheet sheet;//excel
        private String folder;//存储目标父文件夹
        private int startRow;//开始行
        private int count;//执行数量

        public DownloadObj(Sheet sheet, String folder, int startRow, int count) {
            this.sheet = sheet;
            this.folder = folder;
            this.startRow = startRow;
            this.count = count;
        }

        public void run() {
            for (int i = startRow; i < startRow + count; i++) {
                Row row = sheet.getRow(i);
                String name = row.getCell(1).getStringCellValue();
                String fileUrl = row.getCell(2).getStringCellValue();
                String nameFolder = folder + name;
                File f = new File(nameFolder);
                if (!f.exists()) {
                    f.mkdirs();
                }
                String fileName = fileUrl.substring(fileUrl.lastIndexOf("/"));
                try {
                    FileOutputStream outputStream = new FileOutputStream(nameFolder + "\\" + fileName);
                    outputStream.write(downloadImage(fileUrl));
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("完成下载行：" + i);
            }
        }
    }
}
