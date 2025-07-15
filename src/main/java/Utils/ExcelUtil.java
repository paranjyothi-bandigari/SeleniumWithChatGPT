package Utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;

public class ExcelUtil {

    public static Object[][] getTestData(String fileName, String sheetName) {
        try {
            InputStream inputStream = ExcelUtil.class.getClassLoader()
                    .getResourceAsStream("testdata.xlsx"); // Adjust the path as needed
            // InputStream inputStream = ExcelUtil.class.getClassLoader().getResourceAsStream("
            if (inputStream == null) {
                throw new RuntimeException("File not found: " + fileName);
            }

            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            Object[][] data = new Object[rowCount - 1][colCount];

            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    data[i - 1][j] = getCellValue(cell);
                }
            }

            workbook.close();
            return data;

        } catch (Exception e) {
            throw new RuntimeException("Error reading Excel file: " + e.getMessage(), e);
        }
    }

    private static Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return DateUtil.isCellDateFormatted(cell) ? cell.getDateCellValue() : cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            // Handles blank cells
            case ERROR:
                return "ERROR";
            // Consider handling ERROR cell type
            default:
                return ""; // Default case for any other unhandled cell types
        }
    }
}
