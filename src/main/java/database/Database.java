package database;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Database {
    private Workbook workbook;
    private String filename;

    public Database(String filename) throws IOException {
        File file = new File(filename);
        if (file.exists() && file.isFile()) {
            this.filename = filename;
            FileInputStream inputStream = new FileInputStream(filename);
            workbook = new HSSFWorkbook(inputStream);
            inputStream.close();
        } else {
            workbook = new HSSFWorkbook();
            FileOutputStream outputStream = new FileOutputStream(filename);
            workbook.write(outputStream);
            outputStream.close();
        }
    }

    private String[] rowToStringArray(Row row) {
        int n = row.getLastCellNum();
        String[] entry = new String[n];
        for (int j = 0; j < n; ++j) {
            Cell cell = row.getCell(j);
            entry[j] = cell.getStringCellValue();
        }
        return entry;
    }

    private int getColumn(Sheet sheet, String columnName) {
        int column = -1;
        Row title = sheet.getRow(0);
        for (Cell cell : title) {
            if (cell.getStringCellValue().equals(columnName)) {
                column = cell.getColumnIndex();
            }
        }
        return column;
    }

    @Deprecated
    public List<String[]> getEntries(String sheetName, String columnName, String value) {
        List<String[]> result = new ArrayList<>();

        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            return result;
        }

        Row title = sheet.getRow(0);
        int col = -1;
        for (Cell cell : title) {
            if (cell.getStringCellValue().equals(columnName)) {
                col = cell.getColumnIndex();
            }
        }
        if (col == -1) {
            return result;
        }

        for (int i = 0; i < sheet.getLastRowNum(); ++i) {
            Row row = sheet.getRow(i);
            if (row.getCell(col).getStringCellValue().equals(value)) {
                String[] entry = rowToStringArray(row);
                result.add(entry);
            }
        }
        return result;
    }

    public List<String> getAllPages() {
        List<String> pages = new ArrayList<>();
        for (Sheet sheet : workbook) {
            pages.add(sheet.getSheetName());
        }
        return pages;
    }

    public List<String> getTitleOfPage(String pageName) {
        List<String> title = new ArrayList<>();
        Sheet sheet = workbook.getSheet(pageName);
        if (sheet == null) {
            return title;
        }
        Row firstRow = sheet.getRow(0);
        if (firstRow == null) {
            return title;
        }
        for (Cell cell : firstRow) {
            title.add(cell.getStringCellValue());
        }
        return title;
    }

    public List<String[]> getAllEntries(String sheetName) {
        List<String[]> result = new ArrayList<>();
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            return result;
        }
        for (int i = 1; i <= sheet.getLastRowNum(); ++i) {
            Row row = sheet.getRow(i);
            String[] entry = rowToStringArray(row);
            result.add(entry);
        }
        return result;
    }

    public boolean addEntry(String sheetName, String[] entry) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            return false;
        }
        int n = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(n);
        for (int i = 0; i < entry.length; ++i) {
            row.createCell(i);
            row.getCell(i).setCellValue(entry[i]);
        }
        return true;
    }

    public boolean updateEntry(String[] newEntry, String sheetName, String columnName, String value) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            return false;
        }
        int col = getColumn(sheet, columnName);
        if (col == -1) {
            return false;
        }
        int n = newEntry.length;
        for (int i = 0; i < sheet.getLastRowNum(); ++i) {
            Row row = sheet.getRow(i);
            if (row.getCell(col).getStringCellValue().equals(value)) {
                // 这里没有检查 newEntry 和表是否等宽
                for (int j = 0; j < n; ++j) {
                    row.getCell(j).setCellValue(newEntry[j]);
                }
            }
        }
        return true;
    }

    public boolean deleteEntry(String sheetName, String columnName, String value) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            return false;
        }
        int col = getColumn(sheet, columnName);
        if (col == -1) {
            return false;
        }

        for (int i = 1; i < sheet.getLastRowNum(); ++i) {
            Row row = sheet.getRow(i);
            if (row.getCell(col).getStringCellValue().equals(value)) {
                if (i == sheet.getLastRowNum()) {
                    sheet.removeRow(row);
                } else {
                    sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                    i--;
                }
            }
        }
        return true;
    }

    public boolean deleteEntry(String sheetName, Predicate<String[]> predicate) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            return false;
        }
        for (int i = 1; i < sheet.getLastRowNum(); ++i) {
            Row row = sheet.getRow(i);
            List<String> listEntry = new ArrayList<String>();
            for (Cell cell : row) {
                listEntry.add(cell.getStringCellValue());
            }
            if (predicate.test((String[]) listEntry.toArray())) {
                if (i == sheet.getLastRowNum()) {
                    sheet.removeRow(row);
                } else {
                    sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                    i--;
                }
            }
        }
        return true;
    }

    public boolean createPage(String sheetName, String[] title) {
        Sheet sheet = workbook.createSheet(sheetName);
        if (sheet == null) {
            return false;
        }
        Row row = sheet.createRow(0);
        if (row == null) {
            return false;
        }
        for (int i = 0; i < title.length; ++i) {
            Cell cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }
        return true;
    }

    public boolean deletePage(String sheetName) {
        workbook.removeSheetAt(workbook.getSheetIndex(sheetName));
        return true;
    }

    public void presist() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }
}
