import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class MovieTitlePart2
{
	public static void main(String[] args) throws Exception
	{

		
		 String url = "jdbc:oracle:thin:testuser/password@localhost"; 
	      
	        //properties for creating connection to Oracle database
	        Properties props = new Properties();
	        props.setProperty("user", "testdb");
	        props.setProperty("password", "password");
	      
	        //creating connection to Oracle database using JDBC
	        Connection conn = DriverManager.getConnection(url,props);

	        String sql ="SELECT * FROM (SELECT * FROM adjectives ORDER BY DBMS_RANDOM.RANDOM) WHERE rownum =1";

	        //creating PreparedStatement object to execute query
	        PreparedStatement preStatement = conn.prepareStatement(sql);

	        ResultSet result =null;
	        result  = preStatement.executeQuery();	
	        String adjective="";
	        while(result.next()){
	        	adjective = result.getString("ADJ");
	        }

        String sql1 ="SELECT * FROM (SELECT * FROM nouns ORDER BY DBMS_RANDOM.RANDOM) WHERE rownum =1";

        //creating PreparedStatement object to execute query
        PreparedStatement preStatement1 = conn.prepareStatement(sql1);
        ResultSet result1 =null;
        result1  = preStatement1.executeQuery();
        String noun=null;
        while (result1.next()){
		noun =result1.getString("noun");
        }
        String Title=adjective +" "+noun;
		System.out.println( "Your movie title is: " +Title );
		
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter a movie description:");
		String descript=sc.nextLine();
		Statement state=conn.createStatement(); 
		String qy="insert into movie_title values( '"+Title+"','"+ descript +"')";
		state.execute(qy);
		System.out.println("Movie entered into database.ThankYou!");
		sc.close();
		
	}
	
	

}

