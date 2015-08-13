
import java.util.Date;

public class Transaction implements Comparable<Transaction>{
	private int transaction_type;
	private Date date;
	private int amount;
	int flag;
	
	public Transaction(int type,Date d,int amount){
		transaction_type=type;
		date=d;
		this.amount=amount;
		flag=0;
	}
	public Transaction(){
		transaction_type=0;
		date=new Date();
		amount=0;
		flag=0;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getTransaction_type() {
		return transaction_type;
	}
	public void setTransaction_type(int transaction_type) {
		this.transaction_type = transaction_type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public int compareTo(Transaction o) {
	    if (getDate() == null || o.getDate() == null)
	      return 0;
	    return getDate().compareTo(o.getDate());
	  }
	public String toString(){
		return "Transaction: "+transaction_type+"\nDate: "+date+"\nAmount:"+amount+"flag="+flag+"\n\n";
	}
}
