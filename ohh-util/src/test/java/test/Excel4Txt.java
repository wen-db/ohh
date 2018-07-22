/**
 * Copyright (C), 2011-2018, 微贷网.
 */
package test;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author 文得保 2018/4/4.
 */
public class Excel4Txt {
    public void copy(String source, String fileNameStart) throws Exception {
        Workbook wb;
        if (source.endsWith(".xlsx")) {//EXCEL2007
            wb = new XSSFWorkbook(new FileInputStream(new File(source)));
        } else if (source.endsWith(".xls")) {//EXCEL97-2003
            wb = new HSSFWorkbook(new FileInputStream(new File(source)));
        } else {
            throw new Exception("");
        }
        File f = new File(source);
        String path = f.getParentFile().getPath();
        Sheet sheet1 = wb.getSheetAt(0);
        FileOutputStream fileOut = new FileOutputStream(path+File.pathSeparator+sheet1.getSheetName()+".txt");
        for (int i = sheet1.getFirstRowNum(); i <= sheet1.getLastRowNum(); i++) {
            Row row = sheet1.getRow(i);
            if (null == row) {
                continue;
            }
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                if (cell == null) continue;
                if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                    System.out.println("i=" + i + "j=" + j + "   text=" + cell.getStringCellValue());
                } else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                    System.out.println("i=" + i + "j=" + j + "   text=" + cell.getNumericCellValue());
                }



            }
        }

        wb.write(fileOut);
        fileOut.close();
        System.out.println(" 复制sheet成功!");
    }

}
