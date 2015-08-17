


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
public class BankApp {
	
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

	        String sql ="select * from ev_Accounts where acc_num='"+acct_number+"'";

	        //creating PreparedStatement object to execute query
	        PreparedStatement preStatement = conn.prepareStatement(sql);

	        ResultSet result =null;
	        result  = preStatement.executeQuery();	        
	        //System.out.println(result);
	        if(result==null || result.next()==false){
	        	System.out.println("Account does not exist.Do you wish to open an account?(y/n)");	        	
	        	String ch=sc.nextLine();
	        	int acnumber=0; 
	        	int sacnumber=0;
	        	ResultSet rs=null;
	        	ResultSet rs1=null;
	        	Random r=new Random(); 
	        	String qury;
	        	String qury1;
	        	String acc_type;
	        	String acc_type1 = null;
	        	Statement s=conn.createStatement(); 
	        	if(ch.equalsIgnoreCase("y")){
	        		//do{	
	        			System.out.println("Do you wish to open a Checkin account-Checking or Savings-Savings or both-ChSav.Enter your decision:");
	        			String decision=sc.nextLine();
	        			if(decision.equalsIgnoreCase("Checking") || decision.equalsIgnoreCase("Savings")){
	        				acc_type=decision;
	        	        	acnumber=1000+r.nextInt(9990);	
	        	        	qury="select acc_num from ev_Accounts where acc_num="+acnumber; 
	        	        	do{		        				
			        			rs=s.executeQuery(qury);
			        			acnumber=1000+r.nextInt(9990);
		        			}while(rs.next()==true); 
	        			}	        			
	        			else {
	        				acnumber=1000+r.nextInt(9990);
	        				do{
	        					sacnumber=1000+r.nextInt(9990);
	        				}while(sacnumber==acnumber);	        				
	        				qury="select acc_num from ev_Accounts where acc_num="+acnumber; 
	        				qury1="select acc_num from ev_Accounts where acc_num="+sacnumber;
	        				
	        				
	        				do{
	        					rs=s.executeQuery(qury);
	        					acnumber=1000+r.nextInt(9990);
	        				}while(rs.next()==true);
	        				do{
	        					rs1=s.executeQuery(qury1);
	        					sacnumber=1000+r.nextInt(9990);
	        				}while(rs1.next()==true);
	        				acc_type="Checking";
	        				acc_type1="Savings";
	        			}
	        				
	        			
	        			
	        		if(decision.equalsIgnoreCase("Checking") || decision.equalsIgnoreCase("Savings")){
	        		System.out.println("New "+acc_type+" Account No:="+acnumber);
	        		System.out.println("Enter the name:");
	        		String name=sc.nextLine();
	        		System.out.println("Enter a birth date(mm/dd/yyyy)");
	        		String birthday=sc.nextLine();
	        		Statement st = conn.createStatement();
			        String q="insert into ev_customer values(transaction_seq.nextval,'"+name+"',"+"to_date('"+birthday+"','mm/dd/yyyy'))" ;	
			        st.execute(q);
			        String q1="select id from ev_customer where name='"+name+"'";
			        ResultSet rest=st.executeQuery(q1);
			        int cus_id = 0;
			        while(rest.next()){
			        	cus_id=rest.getInt("id");
			        }
			        String q3="insert into ev_Accounts values("+acnumber+","+cus_id+",'"+acc_type+"',0)";
			        System.out.println(q3);
			        st.execute(q3);
			        System.out.println("Congrats!"+acc_type+" Account Open.");
	        		}
	        		else{
	        			System.out.println("New "+acc_type+" Account No:="+acnumber);
	        			System.out.println("New "+acc_type1+" Account No:="+sacnumber);
		        		System.out.println("Enter the name:");
		        		String name=sc.nextLine();
		        		System.out.println("Enter a birth date(mm/dd/yyyy)");
		        		String birthday=sc.nextLine();
		        		Statement st = conn.createStatement();
				        String q="insert into ev_customer values(transaction_seq.nextval,'"+name+"',"+"to_date('"+birthday+"','mm/dd/yyyy'))" ;		       
				        st.execute(q);
				        String q1="select id from ev_customer where name='"+name+"'";
				        ResultSet rest=st.executeQuery(q1);
				        int cus_id = 0;
				        while(rest.next()){
				        	cus_id=rest.getInt("id");
				        }
				        String q3="insert into ev_Accounts values("+acnumber+","+cus_id+",'"+acc_type+"',0)";
				        st.execute(q3);
				        String q4="insert into ev_Accounts values("+sacnumber+","+cus_id+",'"+acc_type1+"',0)";
				        st.execute(q4);
				        System.out.println("Congrats!"+acc_type+" Account  and "+acc_type1+" Account Open.");
	        		}
	        		
	        	}
	        	       	
	        }
	    else{
	    		sql ="select * from ev_Accounts,ev_customer where acc_num='"+acct_number+"' and ev_Accounts.cust_id=ev_Customer.id";

	        //creating PreparedStatement object to execute query
	        PreparedStatement preStatement5 = conn.prepareStatement(sql);

	        	result = preStatement5.executeQuery();		
		        Customer cust=null;	       
		        while(result.next()){
		        	  cust=new Customer(result.getInt("cust_id"),result.getString("Name"),result.getDate("birthday"));
		        	 System.out.println("customer Information:\n\n");
		        }
		        
		      
		        String sql1="select acc_num,acc_type,balance from ev_Accounts,ev_Customer where ev_Customer.id=ev_Accounts.cust_id and cust_id="+cust.getCust_id();
		        ResultSet result1 =null;
		        PreparedStatement preStatement1= conn.prepareStatement(sql1);
		        result1  = preStatement1.executeQuery();	 
		        while(result1.next()){
		        	Account a=new Account(result1.getInt("acc_num"),result1.getString("acc_type"),result1.getInt("balance"));
		        	cust.addToList(a);
		        }
		      //-------------print out customer information
		        System.out.println(cust);
		        int acctnum=0;
		        
		       	for(Account temp:cust.getList()) {  		        	
		       		acctnum=temp.getAcc_num();		        	
		        	String sql2=" select  trans_type,trans_date,amount,status from ev_transactions,ev_Accounts where ev_Accounts.acc_num=ev_transactions.acc_num and ev_transactions.acc_num="+acctnum+" and status=0"	;		
		        	Statement Statement2= conn.createStatement();		       
			        ResultSet  result2 = Statement2.executeQuery(sql2);			       
			        while(result2.next()){			        	
			        	Transaction t=new Transaction(result2.getInt("trans_type"),result2.getDate("trans_date"),result2.getInt("amount"));		        	
			        	cust.getCheckinAccount().addToTransactions(t);
			        		        	 
			        }
		       	}
		        
		        
		        
		        cust.updateBalance();
		        for(Account temp1:cust.getList()){
		        System.out.println("Current balance for "+temp1.getAcc_type()+ "account = "+temp1.getBalance());	 
		        }
	            System.out.println("Do you want to process aother transaction(Y/N):");
	            String choice=sc.nextLine();
	            int type=0;
	            int actnumber=0;
	            while(choice.equalsIgnoreCase("y")){
	        	    System.out.println("Enter a transaction type (Check-1, Debit card-2, Deposit-3 or Withdrawal-4 or CloseAccount=5):");
	        	    type=Integer.parseInt(sc.nextLine());
	        	    if(type!=5){
		        	    System.out.println("Enter the amount:");				
						int amount=Integer.parseInt(sc.nextLine());
						System.out.println("Enter the date of transaction");
						String userInput="";
						String query="";
						userInput=sc.nextLine();
						Date dt=ChangeToDate(userInput);  
						Transaction trans=new Transaction(type,dt,amount);
						Statement st = conn.createStatement();
						
						if(type==3){
							if(cust.isSavingAccountExist()){
								System.out.println("Do you wish to deposit to  Savings or Checking account(Savings/Checking):");
								String tpe=sc.nextLine();
								for(Account temp2:cust.getList()){
									if(temp2.getAcc_type().equalsIgnoreCase(tpe))
									 actnumber=temp2.getAcc_num();
									
								}
								cust.updateSavingsAccountbalance(amount);
							
							}
						}
						else{	
							actnumber=Integer.parseInt(acct_number);
						}
						query="insert into ev_transactions values(transaction_seq.nextval,"+actnumber+","+trans.getAmount()+","+trans.getTransaction_type()+","+"to_date('"+userInput+"','mm/dd/yyyy'),1)" ; 
				        st.execute(query);				    
				    	cust.getAccountforCust(actnumber).addToTransactions(trans);	
				    	//System.out.println(cust.getAccountforCust(actnumber));
						System.out.println("Do you want to process another transaction(Y/N):");
				        choice=sc.nextLine();
	        	    }
	        	    else
	        	    	choice="n";
	           }
          
	           cust.updateBalance();
	           for(Account temp1:cust.getList()){
			        System.out.println("Current balance for "+temp1.getAcc_type()+ "account = "+temp1.getBalance());	 
			        }
	           for(Account temp:cust.getList()){
	        	   int acnumber=temp.getAcc_num();
	        	   Statement stmt = conn.createStatement();
		           String query1="update ev_Accounts set balance="+temp.getBalance()+" where acc_num="+acnumber;           
		           stmt.execute(query1);  
		           String query2="update ev_transactions set status=1 where acc_num="+acnumber;
		           stmt.execute(query2);
	           }
	                   
	           
           if(type==5){
        	   if((cust.getCheckinAccount().getBalance()==0) && ( cust.getSavingsAccount().getBalance()==0)){ 
        		   Statement state=conn.createStatement(); 
        		   
        		   String qy="delete from ev_customer where name='"+cust.getName()+"'"; 
        		   state.execute(qy);
        		   String qy1;
        		   qy1="delete from ev_accounts where cust_id="+cust.getCust_id();
        		   state.execute(qy1);
        		   for(Account temp:cust.getList()){
        			   int acnumber=temp.getAcc_num();
        			   qy1="delete from ev_transactions where acc_num="+acnumber;             		   
            		   state.execute(qy1);
            		   
        		   }
        		   
        		   System.out.println("Account Closed.Thank You.");
        		   } 
        		   else if((cust.getCheckinAccount().getBalance()>0 )|| (cust.getSavingsAccount().getBalance()>0)) 
        			   System.out.println("Cannot close account.Non-zero balance.Withdraw current balances and try again!"); 
        		   else 
        			   System.out.println("Cannot close acount due to negative balance.Pay dues and try again!"); 


           }

	    }
           conn.close();
           sc.close();
           
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