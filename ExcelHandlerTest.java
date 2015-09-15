package testBed;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelHandlerTest {
	private Workbook wb;
	private Sheet sheet;
	private ArrayList<String> skip;
	public ExcelHandlerTest(String str) throws InvalidFormatException, IOException{
		wb = WorkbookFactory.create(new File("/Users/MWT/Desktop/Spreadsheets/"+str));
		//wb = WorkbookFactory.create(new File(str));
		sheet = wb.getSheetAt(0);
		skip = new ArrayList<String>();
		getNullDates();
	}
	public String getCellContents(int r, int c){
		String contents = "";
		Row row = sheet.getRow(r);
		Cell cell = row.getCell(c);
		contents = cell.toString();
		return contents;
	}
	public void getNullDates(){
		int column = 1;
		Row row = sheet.getRow(0);
		for(Cell cell = row.getCell(column); cell.getColumnIndex() < row.getLastCellNum()-1; cell = row.getCell(column)){	
			column++;
			CellStyle style = cell.getCellStyle();
			if(style.getFillForegroundColor() == 23){
				skip.add(cell.toString());
			}
		}
	}
	public int getLates(int r, int c, int color) {
		int lates = 0;
		lates = readRowFor(r,c,"L",13);
		return lates;
	}
	public int getAbsents(int r, int c, int color) {
		int absents = 0;
		absents = readRowFor(r,c,"A",10);
		return absents;
	}
	public int readRowFor(int r, int c, String str, int color){
		int column = c;
		int ret = 0;
		Row row = sheet.getRow(r);
		for(Cell cell = row.getCell(column); cell.getColumnIndex() < row.getLastCellNum()-1; cell = row.getCell(column)){	
			column++;
			CellStyle style = cell.getCellStyle();
			if(cell.toString().equalsIgnoreCase(str) || style.getFillForegroundColor() == color){
				if(skip.contains(sheet.getRow(0).getCell(cell.getColumnIndex()).toString())){
					ret--;
				}
				ret++;
			}
		}
		return ret;
	}
	public void fillLates(int i, MemberRecordTest[] matrix) {
		int column = 1;
		Row dates = sheet.getRow(0);

		Row row = sheet.getRow(i);
		for(Cell cell = row.getCell(column); cell.getColumnIndex() < row.getLastCellNum()-1; cell = row.getCell(column)){	
			CellStyle style = cell.getCellStyle();
			if(cell.toString().equalsIgnoreCase("L") || style.getFillForegroundColor() == 13){
				if(!skip.contains(sheet.getRow(0).getCell(cell.getColumnIndex()).toString()))
					matrix[i-2].addLate(matrix[i-2].getName(), dates.getCell(column).toString());
			}
			column++;
		}

	}
	public void fillAbsents(int i, MemberRecordTest[] matrix) {
		int column = 1;
		Row dates = sheet.getRow(0);

		Row row = sheet.getRow(i);
		for(Cell cell = row.getCell(column); cell.getColumnIndex() < row.getLastCellNum()-1; cell = row.getCell(column)){	
			CellStyle style = cell.getCellStyle();
			if(cell.toString().equalsIgnoreCase("A") || style.getFillForegroundColor() == 10){
				if(!skip.contains(sheet.getRow(0).getCell(cell.getColumnIndex()).toString()))
					matrix[i-2].addAbsent(matrix[i-2].getName(), dates.getCell(column).toString());
			}
			column++;
		}

	}
	public int getDateColumn(String date){
		Row row = sheet.getRow(0);
		for (Cell cell = row.getCell(0); cell.getColumnIndex() < row.getLastCellNum(); cell = row.getCell(cell.getColumnIndex()+1)){
			if(cell.toString().equalsIgnoreCase(date))
				return cell.getColumnIndex();
		}
		return 0;
	}
	public String getLateFromDate(int dcol, int members){
		String str = "";
		for (Row row = sheet.getRow(0); row.getRowNum() < members; row = sheet.getRow(row.getRowNum()+1)){
			Cell cell = row.getCell(dcol);
			CellStyle style = cell.getCellStyle();
			if (cell.toString().equalsIgnoreCase("L") || style.getFillForegroundColor() == 13)
				str += row.getCell(0).toString() + "\n";
		}
		return str;
	}
	public String getAbsentFromDate(int dcol, int members) {
		String str = "";
		for (Row row = sheet.getRow(0); row.getRowNum() < members; row = sheet.getRow(row.getRowNum()+1)){
			Cell cell = row.getCell(dcol);
			CellStyle style = cell.getCellStyle();
			if (cell.toString().equalsIgnoreCase("A") || style.getFillForegroundColor() == 10)
				str += row.getCell(0).toString() + "\n";		
		}
		return str;
	}
	public void printDateReport(String date, int members) {
		int colInd = 0;
		colInd = getDateColumn(date);
		for (Row row = sheet.getRow(2); row.getRowNum() < members; row = sheet.getRow(row.getRowNum()+1)){
			System.out.println(row.getCell(0).toString()+": " + row.getCell(colInd).toString());
		}
	}
	public int getNumDates() {
		int numDates = 0;
		Row row = sheet.getRow(0);
		numDates = (row.getLastCellNum() - 1);
		return numDates;
	}
	public String getDate(int i) {
		Row row = sheet.getRow(0);
		Cell cell = row.getCell(i+1);
		return cell.toString();
	}
	public int numMembers() {
		int ret = 2;
		try{
			for(int i = 2; i < 65; i++){
				Row row = sheet.getRow(i);
				Cell cell = row.getCell(0);
				cell.toString();
				ret = i;
			}
		}
		catch(Exception E){
			System.out.println("num = " + ret);
			return ret-2;
		}
		return ret-2;
	}
}