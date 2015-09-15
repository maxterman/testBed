package testBed;

public class Late {

	private String date;
	private int index;
	private String member;
	
	public Late(String m, String d, int ind){
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
		return member+":  "+date+",  late number "+(index+1);
	}
}
