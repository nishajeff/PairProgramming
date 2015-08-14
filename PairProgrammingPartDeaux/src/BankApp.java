import java.util.Date;
import java.util.GregorianCalendar;


public class BankApp {

	public static void main(String[] args) {
		Date dt=ChangeToDate("09/09/2015");
	   Customer c=new Customer(123,"Nisha",dt);
	   Account chk=new Account(345,"Checking",1000);
	   Account sav=new Account(346,"Savings",100);
	   Transaction t=new Transaction(1,dt,500);
	   Transaction t1=new Transaction(4,dt,700);
	   Transaction t2=new Transaction(3,dt,150);
	   chk.addToTransactions(t);
	   chk.addToTransactions(t1);
	   chk.addToTransactions(t2);
	   c.addToList(chk);
	   c.addToList(sav);
	   System.out.println(c);
	   c.updateBalance();
	   System.out.println(c);
	   
	   
		}
	public static Date ChangeToDate(String userInput){
		int month,day,year;					
		String []Input=userInput.split("/");
		month=Integer.parseInt(Input[0]);
		day=Integer.parseInt(Input[1]);
		year=Integer.parseInt(Input[2]);
		GregorianCalendar n=new GregorianCalendar( year,month-1,day);
		Date date=new Date();
	    date=n.getTime();
	    return date;
	}
	}


