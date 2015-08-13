import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Scanner;
public class EvilCorpBankApp {
	
	public static void main(String[] args)throws SQLException  {
		
        	
		Scanner sc=new Scanner(System.in);
        System.out.println("Enter the account nunmber: ");
		String acct_number=sc.nextLine();
		 String url = "jdbc:oracle:thin:testuser/password@localhost"; 
	      
	        //properties for creating connection to Oracle database
	        Properties props = new Properties();
	        props.setProperty("user", "testdb");
	        props.setProperty("password", "password");
	      
	        //creating connection to Oracle database using JDBC
	        Connection conn = DriverManager.getConnection(url,props);

	        String sql ="select * from evilcorpcustomer where acc_num='"+acct_number+"'";

	        //creating PreparedStatement object to execute query
	        PreparedStatement preStatement = conn.prepareStatement(sql);
	        ResultSet result = preStatement.executeQuery();
	        Customer cust=null;
	        while(result.next()){
	        	  cust=new Customer(result.getInt("acc_num"),result.getString("Name"),result.getInt("start_bal"),result.getDate("birthday"));
	        	 System.out.println("customer Information:\n\n"+cust);
	        }
	        //String sql1="select * from evilcorptrans join evilcorp_type on evilcorptrans.trans_type=evilcorp_type.type_id where user_id='"+acct_number+"'";
	        String sql1="select * from evilcorptrans where user_id='"+acct_number+"' and trans_processed=0";
	        //System.out.println(sql1);
	        PreparedStatement preStatement1 = conn.prepareStatement(sql1);
	        //System.out.println( preStatement1);
	        ResultSet  result2 = preStatement1.executeQuery();	       
	        System.out.println();
	        while(result2.next()){
	        	Transaction t=new Transaction(result2.getInt("trans_type"),result2.getDate("trans_date"),result2.getInt("amount"));
	        	//Transaction t=new Transaction(result2.getString("descript"),result2.getDate("trans_date"),result2.getInt("amount"));
	        	cust.addToTransactions(t);
	        	System.out.println(t);	        	 
        }
	        cust.updateBalance();
	        System.out.println("Current balance= "+cust.getBalance());
           System.out.println("done"); 
           System.out.println("Do you want to process aother transaction(Y/N):");
           String choice=sc.nextLine();
           while(choice.equalsIgnoreCase("y")){
        	   System.out.println("Enter a transaction type (Check-1, Debit card-2, Deposit-3 or Withdrawal-4 or CloseAccount=5):");
        	   int type=Integer.parseInt(sc.nextLine());
        	   System.out.println("Enter the amount:");				
				int amount=Integer.parseInt(sc.nextLine());
				System.out.println("Enter the date of transaction");
				String userInput="";			
				userInput=sc.nextLine();
				Date dt=ChangeToDate(userInput);  
				Transaction trans=new Transaction(type,dt,amount);
				Statement st = conn.createStatement();
		        String query="insert into evilcorptrans values(transaction_seq.nextval,"+Integer.parseInt(acct_number)+","+trans.getAmount()+","+trans.getTransaction_type()+","+"to_date('"+userInput+"','mm/dd/yyyy'),1)" ;   
		        System.out.println(query);
		        st.execute(query);
		        cust.addToTransactions(trans);
				 System.out.println("Do you want to process another transaction(Y/N):");
		         choice=sc.nextLine();
           }
           
           cust.updateBalance();
           Statement stmt = conn.createStatement();
           String query1="update evilcorpcustomer set start_bal="+cust.getBalance()+" where acc_num='"+acct_number+"'";           
           stmt.execute(query1);          
           String query2="update evilcorptrans set trans_processed=1 where user_id='"+acct_number+"'";
           stmt.execute(query2); 
           System.out.println("Current balance= "+cust.getBalance());
           conn.close();
           
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