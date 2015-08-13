


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;



public class Customer  {
	private int account_num;
	private String cust_name;
	private int balance;
	private Date birthday;
	private ArrayList<Transaction> transactions;
	public Customer(int acct_num,String cust_name,int balance,Date date){
		account_num=acct_num;
		this.cust_name=cust_name;
		this.balance=balance;
		birthday=date;
		transactions=new ArrayList<Transaction>();		
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getAccount_num() {
		return account_num;
	}

	public void setAccount_num(int account_num) {
		this.account_num = account_num;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	//****The function below uncommented will process transactions based on the sorted dates
	
	/*public void updateBalance(){
		Collections.sort(transactions);
		for(Transaction temp:transactions){			
			if(temp.getTransaction_type().equalsIgnoreCase("dp"))
				balance+=temp.getAmount();
			else
				balance-=temp.getAmount();
			if(balance<0 && !(temp.getTransaction_type().equalsIgnoreCase("dp")))
				balance-=35;			
		}
	}*/
	
	
	//*****The function below will ignore the date priority for deposits-DP so as to process transactions in
	//      the order that generates most fees.
	
	public void updateBalance(){
		Collections.sort(transactions);
		for(Transaction temp:transactions){	
			if(temp.getFlag()==0){
				if(!(temp.getTransaction_type()==3)){				
					balance-=temp.getAmount();	
					temp.setFlag(1);
				}
				if(balance<0 && !(temp.getTransaction_type()==3)){
					balance-=35;
					//temp.setFlag(true);
				}
				
			}
		}
		for(Transaction temp:transactions){			
			if(temp.getFlag()==0){
				if(temp.getTransaction_type()==3){
					balance+=temp.getAmount();
					temp.setFlag(1);
				}
				
			}
		}
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public void addToTransactions(Transaction t){
		transactions.add(t);
	}
	
	public String toString(){
		String out="";
		out="Account no:"+account_num+"\nBalance: "+this.getFormattedBalance()+"\nCustomer Name:"+cust_name+"\nBirthday:"+birthday;
		for(Transaction temp:transactions)
			out+=temp.toString();
		return out;
		
	}
	public String getFormattedBalance()
    {
        NumberFormat currency = 
            NumberFormat.getCurrencyInstance();
        if(balance<0)
        	return "-"+currency.format(balance);
        else
        	return currency.format(balance);
    }
	

}


