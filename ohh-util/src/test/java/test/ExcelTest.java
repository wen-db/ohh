package test;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExcelTest {


    public void copy(String source, String fileNameStart, Calendar c, List<Replace> list) throws Exception {
        Workbook wb;
        String end;
        if (source.endsWith(".xlsx")) {//EXCEL2007
            wb = new XSSFWorkbook(new FileInputStream(new File(source)));
            end=".xlsx";
        } else if (source.endsWith(".xls")) {//EXCEL97-2003
            wb = new HSSFWorkbook(new FileInputStream(new File(source)));
            end=".xls";
        } else {
            throw new Exception("");
        }
        int week = c.get(Calendar.DAY_OF_WEEK);
        if (week == Calendar.SATURDAY || week == Calendar.SUNDAY) {//0代表周日，6代表周六
            return;
        }
        String folder = source.substring(0, source.lastIndexOf("\\"));
        folder = folder + "\\" + fileNameStart + "\\" + new SimpleDateFormat("yyyyMM").format(c.getTime());
        File f = new File(folder);
        if (!f.exists()) {
            f.mkdirs();
        }
        String toFile = folder + "\\" + fileNameStart + new SimpleDateFormat("yyyy.MM.dd").format(c.getTime()) + end;


        Sheet sheet1 = wb.getSheetAt(0);
       /* if (wb.getNumberOfSheets() > 1) {
            wb.removeSheetAt(1);
        }*/
        int tt =  sheet1.getLastRowNum();
        Row row2= sheet1.getRow(tt);
        for (int i = sheet1.getFirstRowNum(); i <= sheet1.getLastRowNum(); i++) {
            Row row = sheet1.getRow(i);
            if (null == row) {
                continue;
            }
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                if (cell==null)continue;
                if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                    System.out.println("i=" + i + "j=" + j + "   text=" + cell.getStringCellValue());
                } else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
                    System.out.println("i=" + i + "j=" + j+ "   text=" + cell.getNumericCellValue() );
                }
                for (Replace r : list) {
                    if (r.getI() == i && r.getJ() == j) {
                        cell.setCellValue(r.getText());
                    }
                }


            }
        }
        FileOutputStream fileOut = new FileOutputStream(toFile);
        wb.write(fileOut);
        fileOut.close();
        System.out.println(" 复制sheet成功!");
    }


    public void j(Calendar c) throws Exception {
        String path = "C:\\Users\\Administrator\\Desktop\\f\\j\\" + new SimpleDateFormat("yyyyMM").format(c.getTime());
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        Workbook wb = new HSSFWorkbook(new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\f\\经纪业务监控日志20160406.xls")));
        Sheet sheet1 = wb.getSheetAt(0);
        if (wb.getNumberOfSheets() > 1) {
            wb.removeSheetAt(1);
        }
       /* if (file.endsWith(".xlsx")) {//EXCEL2007
            wb = new XSSFWorkbook(new FileInputStream(new File(file)));
        } else if (file.endsWith(".xls")) {//EXCEL97-2003
            wb = new HSSFWorkbook(new FileInputStream(new File(file)));
        } else {
            throw new Exception("");
        }*/
        for (int day = 0; day < c.getActualMaximum(Calendar.DAY_OF_MONTH); day++) {
            if (day != 0)
                c.add(Calendar.DAY_OF_MONTH, 1);
            String d1 = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
            String toFile = path + "\\经纪业务监控日志" + new SimpleDateFormat("yyyyMMdd").format(c.getTime()) + ".xls";
            int week = c.get(Calendar.DAY_OF_WEEK);
            //System.out.println(week);
            if (week == Calendar.SATURDAY || week == Calendar.SUNDAY) {//0代表周日，6代表周六
                continue;
            }

            Date dd1 = new SimpleDateFormat("yyyyMMdd").parse("20160903");
            Date dd2 = new SimpleDateFormat("yyyyMMdd").parse("20170622");
            if (c.getTime().compareTo(dd1) < 0 || c.getTime().compareTo(dd2) > 0) {
                continue;
            }
            //单元格合并
            CellRangeAddress region = null;

            Sheet sheet2 = wb.createSheet();
            for (int i = 0; i < sheet1.getNumMergedRegions(); i++) {
                region = sheet1.getMergedRegion(i);
                if ((region.getFirstColumn() >= sheet1.getFirstRowNum())
                        && (region.getLastRow() <= sheet1.getLastRowNum())) {
                    sheet2.addMergedRegion(region);
                }
            }

            //复制内容
            Row rowFrom = null;
            Row rowTo = null;
            Cell cellFrom = null;
            Cell cellTo = null;
            for (int i = sheet1.getFirstRowNum(); i <= sheet1.getLastRowNum(); i++) {
                rowFrom = sheet1.getRow(i);
                if (null == rowFrom) {
                    continue;
                }

                rowTo = sheet2.createRow(i);
                rowTo.setHeight(rowFrom.getHeight());
                for (int j = 0; j < rowFrom.getLastCellNum(); j++) {
                    sheet2.setColumnWidth(j, sheet1.getColumnWidth(j));
                    if (null != sheet1.getColumnStyle(j)) {
                        sheet2.setDefaultColumnStyle(j, sheet1.getColumnStyle(j));
                    }

                    cellFrom = rowFrom.getCell(j);
                    if (null == cellFrom) {
                        continue;
                    }

                    cellTo = rowTo.createCell(j);
                    cellTo.setCellStyle(cellFrom.getCellStyle());
                    cellTo.setCellType(cellFrom.getCellType());

                    if (Cell.CELL_TYPE_STRING == cellFrom.getCellType()) {
                        cellTo.setCellValue(cellFrom.getStringCellValue());
                    } else if (Cell.CELL_TYPE_NUMERIC == cellFrom.getCellType()) {
                        cellTo.setCellValue(cellFrom.getNumericCellValue());
                    }

                /*    if (cellTo.getCellType() == CellType.STRING.getCode()) {
                        System.out.println("i=" + i + "j=" + j + "--" + cellTo.getRichStringCellValue());
                    }*/
                    if (i == 2) {

                        cellTo.setCellValue("97  监控人：  钱晓雷 钱晓岚                                                                 日期："
                                + d1);
                    }
                }
            }
            sheet2.setDisplayGridlines(true);//
            wb.removeSheetAt(0);

            FileOutputStream fileOut = new FileOutputStream(toFile);
            wb.write(fileOut);
            fileOut.close();
            System.out.println(" 复制sheet成功!" + new SimpleDateFormat("MMdd").format(c.getTime()));
        }

    }

    @Test
    public void 融资融券业务日志() throws Exception {
        String source = "E:\\wenruo\\融资融券业务日志（苏州）2017.01.02.xlsx";
        String fileNameStart = "融资融券业务日志（苏州）";
        Calendar start = getCalendar("2017-01-01");
        Calendar end = getCalendar("2018-02-30");
        do {
            List<Replace> list = new ArrayList<Replace>();
            list.add(new Replace(23, 0, "数据日期：" + new SimpleDateFormat("yyyy年MM月dd日").format(start.getTime())));
            list.add(new Replace(23, 13, "填表日期：" + new SimpleDateFormat("yyyy年MM月dd日").format(start.getTime())));
            copy(source, fileNameStart, start, list);
            addDay(start);
        } while (start.compareTo(end) < 0);

    }

    @Test
    public void 营业部经纪业务监控日志() throws Exception {

        String source = "E:\\wenruo\\营业部合规经理工作日志0331.xls";
        String fileNameStart = "营业部合规经理工作日志";
        Calendar start = getCalendar("2017-10-19");
        Calendar end = getCalendar("2018-2-30");
        // Calendar end = getCalendar("2018-02-30");
        do {
            List<Replace> list = new ArrayList<Replace>();
            list.add(new Replace(17, 1, "合规经理：钱晓岚                                                    日期：" ));
            list.add(new Replace(17, 8,  new SimpleDateFormat("yyyyMMdd").format(start.getTime())));
            copy(source, fileNameStart, start, list);
            addDay(start);
        } while (start.compareTo(end) < 0);

    }
    @Test
    public void 经纪业务监控日志0() throws Exception {

        String source = "E:\\wenruo\\经纪业务监控日志0531.xls";
        String fileNameStart = "经纪业务监控日志";
        Calendar start = getCalendar("2017-10-19");
        Calendar end = getCalendar("2018-02-30");
        // Calendar end = getCalendar("2018-02-30");
        do {
            List<Replace> list = new ArrayList<Replace>();
            list.add(new Replace(2, 0, "97  监控人：    钱晓岚                                                           日期："+new SimpleDateFormat("yyyyMMdd").format(start.getTime())+""));
            copy(source, fileNameStart, start, list);
            addDay(start);
        } while (start.compareTo(end) < 0);

    }

    public Calendar getCalendar(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public Date addDay(Calendar c) {
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    class Replace {
        public int i;
        public int j;
        public String text;

        public Replace(int i, int j, String text) {
            this.i = i;
            this.j = j;
            this.text = text;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public int getJ() {
            return j;
        }

        public void setJ(int j) {
            this.j = j;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}