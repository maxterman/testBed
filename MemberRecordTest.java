package testBed;


public class MemberRecordTest {

	private String name;
	private Late[] lates;
	private Absent[] absents;
	private int lateIndex, absentIndex;
	private double total;
	private int n, k;

	public MemberRecordTest(String str,int l,int a,double t){
		name = str;
		lateIndex = l;
		lates = new Late[30];
		absents = new Absent[15];
		absentIndex = a;
		total = t;
		n = 0;
		k = 0;
	}
	public String getName(){
		return name;
	}
	public void setName(String str){
		name = str;
	}
	public int getLateNumber(){
		return lateIndex;
	}
	public void addLate(String m, String d){
		lates[n] = new Late(m,d,n);
		n++;
	}
	public void addAbsent(String m, String d) {
		absents[k] = new Absent(m,d,k);
		k++;
	}
	public int getAbsents(){
		return absentIndex;
	}
	public void setAbsents(int a){
		absentIndex = a;
	}
	public double getTotal(){
		total = absentIndex + (lateIndex*(1.0/3.0));
		return total;
	}
	public String printLateReport(){
		String str = "";
		for(int i = 0; i < lateIndex; i++)
			str += lates[i].printReport() + "\n";
		return str;
	}
	public String printAbsentReport() {
		String str = "";
		for(int i = 0; i < absentIndex; i++)
			str += absents[i].printReport() + "\n";
		return str;
	}
	public void printRecord(){
		getTotal();
		System.out.println(name+":  "+lateIndex+",  "+absentIndex+",  "+total);
	}
	public String printRecordTotal(){
		String str;
		getTotal();
		str = name+":  "+total+"\n";
		return str;
	}
	public String printRecordNeat(){
		String str = "";
		getTotal();
		str = name + "\n" + "  Lates: "+lateIndex + "\n" +"  Absents: "+absentIndex + "\n" + "  Total: "+total+ "\n";
		return str;
	}
}
