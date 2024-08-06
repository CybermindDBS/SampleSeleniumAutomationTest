package utils;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class ExcelUtil {
    XSSFWorkbook workbook;
    Path workBookPath;

    public ExcelUtil create(Path path) {
        workBookPath = path;
        workbook = new XSSFWorkbook();
        workbook.createSheet();
        try {
            workbook.write(Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ExcelUtil load(Path path) {
        workBookPath = path;
        try {
            workbook = new XSSFWorkbook(Files.newInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public String[][] read(String sheetName, int... sheetIndex) {
        XSSFSheet sheet;
        if (sheetIndex.length != 0)
            sheet = workbook.getSheetAt(sheetIndex[0]);
        else if (sheetName != null)
            sheet = workbook.getSheet(sheetName);
        else sheet = workbook.getSheetAt(0);
        int noOfRows = sheet.getPhysicalNumberOfRows();
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (int i = 0; i < noOfRows; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) break;
            ArrayList<String> cellValues = new ArrayList<>();
            data.add(cellValues);
            for (int j = 0; row.getCell(j) != null; j++)
                cellValues.add(row.getCell(j).toString());
        }
        int noOfColumns = data.get(0).size();
        String[][] returnData = new String[noOfRows][noOfColumns];
        int index = 0;
        for (ArrayList<String> cells : data) {
            returnData[index++] = cells.toArray(new String[0]);
        }
        return returnData;
    }

    public void write(String[][] data, String... sheetName) {
        XSSFSheet sheet;
        if (sheetName.length != 0)
            sheet = workbook.getSheet(sheetName[0]);
        else if (workbook.getNumberOfSheets() > 0)
            sheet = workbook.getSheetAt(0);
        else sheet = workbook.createSheet();
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[0].length; j++) {
                XSSFRow row = sheet.getRow(i);
                if (row == null) row = sheet.createRow(i);
                XSSFCell cell = row.getCell(j);
                if (cell == null) cell = row.createCell(j);
                cell.setCellValue(data[i][j]);
            }
        try {
            workbook.write(Files.newOutputStream(workBookPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
