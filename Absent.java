package testBed;

public class Absent {

	private String date;
	private int index;
	private String member;
	
	public Absent(String m, String d, int ind){
		date = d;
		member = m;
		index = ind;
	}
	
	public String getDate(){
		return date;
	}
	public String getMember(){
		return member;
	}
	public int getIndex(){
		return index;
	}
	public String printReport(){
		return member+":  "+date+",  absence number "+(index+1);
	}
}
