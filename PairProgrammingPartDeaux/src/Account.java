import java.util.ArrayList;


public class Account {
	private int acc_num;
	private String acc_type;
	private int balance;
	private ArrayList<Transaction> transactions;

	public Account(int acc_num, String acc_type, int balance){
		this.acc_num=acc_num;
		this.acc_type=acc_type;
		this.balance=balance;
		transactions= new ArrayList<Transaction>();
			}

	public int getAcc_num() {
		return acc_num;
	}

	public void setAcc_num(int acc_num) {
		this.acc_num = acc_num;
	}

	public String getAcc_type() {
		return acc_type;
	}

	public void setAcc_type(String acc_type) {
		this.acc_type = acc_type;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
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
		String out ="";
		out+= "\nAccount No: "+ acc_num +"\nAccount Type: "+ acc_type+ "\nBalance: "+ balance;
		for (Transaction temp: transactions){
			out+= temp.toString();			
		}
		return out;
	}
	
	}
