import java.util.ArrayList;
import java.util.Date;
public class Customer{
	private int cust_id;
	private String name;
	private Date birthday;
	private ArrayList<Account>list;

	public Customer (int cust_id, String name, Date birthday){
		this.cust_id=cust_id;
		this.name=name;
		this.birthday=birthday;
		list= new ArrayList <Account>();		
	}

	public int getCust_id() {
		return cust_id;
	}

	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public ArrayList<Account> getList() {
		return list;
	}

	public void setList(ArrayList<Account> list) {
		this.list = list;
	}
	
    public void addToList(Account a){
    	list.add(a);
    }
    public Account getCheckinAccount(){
    	for(Account temp:this.getList()){
    		if(temp.getAcc_type().equalsIgnoreCase("Checking"))
    			return temp;    		
    	}
    	return null;
    }
    public Account getSavingsAccount(){
    	for(Account temp:this.getList()){
    		if(temp.getAcc_type().equalsIgnoreCase("Savings"))
    			return temp;    		
    	}
    	return null;
    }
    public Account getAccountforCust(int act_num){
    	for(Account temp:this.getList()){
    		if(temp.getAcc_num()==act_num)
    			return temp;    		
    	}
    	return null;
    }
	public void updateBalance(){
		if(!isSavingAccountExist()){
			for(Account temp:list){
				ArrayList<Transaction> trans=temp.getTransactions();
				for(Transaction t:trans){	
					if(t.getFlag()==0){
						if(!(t.getTransaction_type()==3)){				
							int bal=temp.getBalance();
							bal-=t.getAmount();
							temp.setBalance(bal);
							t.setFlag(1);
						}
						if(temp.getBalance()<0 && !(t.getTransaction_type()==3)){
							int bal=temp.getBalance();
							bal-=35;
							temp.setBalance(bal);
							//temp.setFlag(true);
						}
						
					}
				}
				
			}
		for(Account temp:list){
			ArrayList<Transaction> trans=temp.getTransactions();
			for(Transaction t:trans){			
				if(t.getFlag()==0){
				if(t.getTransaction_type()==3){
					int balance=temp.getBalance();
					balance+=t.getAmount();
					temp.setBalance(balance);
					t.setFlag(1);
				}			
			  }
		    }
		  }
	    }
		else{
				Account sav=null;
				Account chkng=null;		
				for(Account temp:list){
					if(temp.getAcc_type().equals("Savings"))
						sav=temp;
					else
						chkng=temp;
				}
				ArrayList<Transaction> trans_chk=chkng.getTransactions();
				//System.out.println(trans_chk);
				for(int i=0;i<trans_chk.size();i++)
				{	
					Transaction t=trans_chk.get(i);
					if(t.getFlag()==0)
					{
						if(!(t.getTransaction_type()==3))
						{				
							int bal=chkng.getBalance();
							bal-=t.getAmount();
							chkng.setBalance(bal);
							t.setFlag(1);
						}
						
						if(chkng.getBalance()<0 && !(t.getTransaction_type()==3))
						{
							int bal=chkng.getBalance();
							//System.out.println("bal="+bal);
							int bal1=bal-2*bal;
							//System.out.println("bal="+bal1);
							if(sav.getBalance()>=0 && sav.getBalance()>=(bal1+15) ){							
								sav.setBalance(sav.getBalance()-(bal1+15));
								chkng.setBalance(0);
								System.out.println("Overdraft.$15 transfer fee deducted from Savings Account");
								chkng.addToTransactions(new Transaction(3,new Date(),bal1,1));
								sav.addToTransactions(new Transaction(4,new Date(),bal1+15,1));							
							}
							else{
							bal-=35;
							chkng.setBalance(bal);
							}
							
						}
					
					}
				}
				
				for(int i=0;i<trans_chk.size();i++)
				{	
					Transaction t=trans_chk.get(i);
					if(t.getFlag()==0){
					if(t.getTransaction_type()==3){
						int balance=chkng.getBalance();
						balance+=t.getAmount();
						chkng.setBalance(balance);
						t.setFlag(1);
						
					}
					if(chkng.getBalance()<0 )
						{
							int bal=chkng.getBalance();
							//System.out.println("bal="+bal);
							int bal1=bal-2*bal;
							//System.out.println("bal="+bal1);
							if(sav.getBalance()>=0 && sav.getBalance()>=(bal1+15) ){							
								sav.setBalance(sav.getBalance()-(bal1+15));
								chkng.setBalance(0);
								System.out.println("Overdraft.$15 transfer fee deducted from Savings Account");
								chkng.addToTransactions(new Transaction(3,new Date(),bal1,1));
								sav.addToTransactions(new Transaction(4,new Date(),bal1+15,1));							
							}
						}							
				  }
			    }
				
			}
 }
public void updateSavingsAccountbalance(int amt){
	int bal=this.getSavingsAccount().getBalance();
	bal=bal+amt;
	this.getSavingsAccount().setBalance(bal);
}
public  boolean isSavingAccountExist(){
		boolean flag=false;
		for(Account temp:list){
			if(temp.getAcc_type().equals("Savings"))
				flag=true;			
		}
		return flag;
	}

public  String toString(){
	String out="CUSTOMER DATA:\n----------------------";
	out+="\nCustomer_ID: "+cust_id+"\nName: "+name+"\nBirthday: "+birthday+"\n";
	for(Account temp:list){
		out+=temp.toString()+"\n";
	}
	return out;
	
}

}






