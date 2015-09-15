package testBed;

import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class AttendanceDriverTest implements Runnable{
	private MemberRecordTest[] matrix;
	private ExcelHandlerTest handler;
	private String[] str;
	private String[] dtr;
	private int numDates;
	public void run() {
		try {handler = new ExcelHandlerTest("Men's Glee Club Attendance.xls");} catch (InvalidFormatException e) {} catch (IOException e) {System.out.println("404");}	
		matrix = new MemberRecordTest[handler.numMembers()];
		str = new String[handler.numMembers()];
		numDates = handler.getNumDates();
		dtr = new String[numDates+1];
		dtr[0] = "All Dates";
		for(int i = 1; i < numDates; i++){
			dtr[i] = handler.getDate(i);
		}

		str[0] = "All Members";
		for(int i = 2; i<matrix.length-1; i++){
			matrix[i-2] = new MemberRecordTest(handler.getCellContents(i, 0),handler.getLates(i,1,0),handler.getAbsents(i,1,0),0.0);
		}
		for (int i = 1; i < matrix.length-3; i++){
			str[i] = matrix[i].getName();
			//System.out.println(str[i]);
		}
		for (int i = 2; i < matrix.length-1; i++){
			handler.fillLates(i,matrix);
			handler.fillAbsents(i,matrix);
		}
	}
	/*public static void main(String[] args) throws InvalidFormatException, IOException {
		AttendanceDriverTest driver = new AttendanceDriverTest();
		//driver.run();
		//MainWindow window = new MainWindow(driver);


		matrix = new MemberRecordTest[65];
		handler = new ExcelHandlerTest("Men's Glee Club Attendance.xls");	

		for(int i = 2; i<matrix.length-1; i++){
			matrix[i-2] = new MemberRecordTest(handler.getCellContents(i, 0),handler.getLates(i,1,0),handler.getAbsents(i,1,0),0.0);
			//matrix[i-2].printRecordTotal();
		}

		for (int i = 2; i < matrix.length-1; i++){
			handler.fillLates(i,matrix);
			handler.fillAbsents(i,matrix);
		}

		//getBubble();												//Bubble
		//getHitList();												//HitList

		//getDateLate("Fri., Sept. 5"); getDateAbsent(date)			//Date
		//getDateLate("Fri., Sept. 5");								//Date+Late
		//getDateAbsent("Fri., Sept. 5");							//Date+Absent
		//getDateLate("Fri., Sept. 5"); getDateAbsent(date)			//Date+Total

		//getMembersReport();										//Member(All)

		//getMemberAttendanceReport("Terman, Max");					//Member(Single)
		//getMemberLateReport("Terman, Max");						//Member(Single)+Late
		//getMemberAbsentReport("Terman, Max");						//Member(Single)+Absent
		//getMemberTotalReport("Terman, Max");						//Member(Single)+Total
	}*/
	public String getBubble(){
		String str = "On The Bubble:\n";
		for(int n = 0; n < matrix.length-3; n++){
			if((matrix[n].getTotal() > 1.9) && (matrix[n].getTotal() < 3.1)){
				str += matrix[n].printRecordTotal();
			}
		}
		return str;
	}
	public String getHitList(){
		String str = "Over 3 Total:\n";
		for(int n = 0; n < matrix.length-3; n++){
			if((matrix[n].getTotal() > 3)){
				str += matrix[n].printRecordTotal();
			}
		}
		return str;
	}
	public String getMemberLateReport(String name){
		String str = "";
		for (int i = 0; i < matrix.length-3; i++){
			if(matrix[i].getName().equalsIgnoreCase(name)){
				str += "Late Report for "+name+": \n";
				str += matrix[i].printLateReport();
				break;
			}
		}
		return str;
	}
	public String getMemberAbsentReport(String name){
		String str = "";
		for (int i = 0; i < matrix.length-3; i++){
			if(matrix[i].getName().equalsIgnoreCase(name)){
				str += "Absence Report for "+name+": " +"\n";
				str += matrix[i].printAbsentReport();
				System.out.println("");
				break;
			}
		}
		return str;
	}
	public String getMemberTotalReport(String name){
		String str = "";
		for (int i = 0; i < matrix.length-3; i++){
			if(matrix[i].getName().equalsIgnoreCase(name)){
				str += "Intervention-Index Report for "+name+": " + "\n";
				str += matrix[i].printRecordTotal();
				System.out.println("");
				break;
			}
		}
		return str;
	}
	public String getDateLate(String date){
		String str = "Members late on "+date+":\n";
		str += handler.getLateFromDate(handler.getDateColumn(date), matrix.length-1) + "\n";
		return str;
	}
	public String getDateAbsent(String date){
		String str = "Members absent on "+date+":\n";
		str += handler.getAbsentFromDate(handler.getDateColumn(date), matrix.length-1) + "\n";
		return str;
	}
	public void getDateReport(String date){
		handler.printDateReport(date, matrix.length-1);
	}
	public String getMembersReport(){
		String str = "";
		for (int i = 0; i < matrix.length-3; i++){
			str += matrix[i].printRecordNeat();
			str += "------------------------------------\n";
		}
		return str;
	}
	public String getMemberAttendanceReport(String name){
		String str = "";
		for (int i = 0; i < matrix.length-3; i++){
			if(matrix[i].getName().equalsIgnoreCase(name)){
				str += ("Attendance Report for "+name+": \n");
				str += matrix[i].printRecordNeat();
				str += ("------------------------------------\n");
				System.out.println("");
				break;
			}
		}
		return str;
	}
	public String[] getMemberList(){
		return str;
	}
	public String[] getDateList(){
		return dtr;
	}
	public MemberRecordTest[] getMatrix(){
		return matrix;
	}
	public void access(){}
}