package org.example.OrangeHRM_Automation_Framework.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ExcelUtils {
    /**
     * Reads an Excel sheet and returns a 2D Object[][] array for TestNG @DataProvider.
     * Skips Row 0 (the header row).
     */
    public static Object[][] getExcelData(String filePath, String sheetName) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in: " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null || headerRow.getLastCellNum() <= 0) {
                throw new RuntimeException("Row 0 (header row) is empty in sheet '" + sheetName + "'. Re-run ExcelUtils.main() to regenerate the Excel file.");
            }

            int totalRows = sheet.getLastRowNum();          // Number of data rows (excluding row 0)
            int totalCols = headerRow.getLastCellNum();     // Must be getLastCellNum(), NOT getFirstCellNum()

            System.out.println("Loaded Excel Sheet '" + sheetName + "' -> Rows: " + totalRows + ", Columns: " + totalCols);

            Object[][] data = new Object[totalRows][totalCols];
            DataFormatter formatter = new DataFormatter();

            for (int i = 1; i <= totalRows; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < totalCols; j++) {
                    data[i - 1][j] = (row == null) ? "" : formatter.formatCellValue(row.getCell(j)).trim();
                }
            }
            return data;

        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file from path: " + filePath, e);
        }
    }

    public static void main(String[] args) throws IOException {
        String dirPath = "src/test/resources/testdata";
        String filePath = dirPath + "/OrangeHRM_TestData.xlsx";

        Path path = Paths.get(dirPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(filePath)) {

            Sheet sheet = workbook.createSheet("LoginData");

            String[][] rows = {
                    {"Username", "Password", "ExpectedResult"},
                    {"Admin", "admin123", "VALID"},
                    {"Admin", "wrongPass123", "INVALID"},
                    {"WrongUser", "admin123", "INVALID"}
            };

            for (int i = 0; i < rows.length; i++) {
                Row row = sheet.createRow(i);
                for (int j = 0; j < rows[i].length; j++) {
                    row.createCell(j).setCellValue(rows[i][j]);
                }
            }

            workbook.write(fos);
            System.out.println("Successfully generated Excel file with 3 columns at: " + filePath);
        }
    }

}
