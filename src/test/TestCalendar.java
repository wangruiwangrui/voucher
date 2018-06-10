import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class TestCalendar {

	public static void main(String[] args) {
		
		Scanner s=new Scanner(System.in);
		
		int d=Integer.parseInt(s.next());
		
		Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), d, 0, 0, 0, 0);  
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        
        String startTime = null;
		
		String endTime=null;
		
		startTime=sdf.format(cal.getTime());
        
        System.out.println(""+cal.getActualMinimum(Calendar.DAY_OF_MONTH)+"  "
        		+cal.get(Calendar.MONDAY)+"  "+Calendar.DAY_OF_MONTH);
		
		System.out.println("searchMap="+startTime);
        
	}
	
}
