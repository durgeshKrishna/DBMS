    import java.sql.*;  
    public class OracleCon{  
    public static void main(String args[]){  
    try{  
    //step1 load the driver class  
    Class.forName("oracle.jdbc.driver.OracleDriver");  
      
    //step2 create  the connection object  
    Connection con=DriverManager.getConnection(  
    "jdbc:oracle:thin:@localhost:1521:xe","system","dksql");  
    System.out.println("Connection established to Database");
	
    con.close();  
      
    }catch(Exception e){ System.out.println(e);}  
      
    }  
    }  