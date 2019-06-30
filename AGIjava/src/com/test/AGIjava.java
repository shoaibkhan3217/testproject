	 package com.test;
	 import java.io.*;
	 import java.sql.*;
	 import java.util.*;
	 import java.lang.*;
	 import org.asteriskjava.fastagi.AgiChannel;
	 import org.asteriskjava.fastagi.AgiException;
	 import org.asteriskjava.fastagi.AgiRequest;
	 import org.asteriskjava.fastagi.BaseAgiScript;

	public class AGIjava extends BaseAgiScript
	{  public Statement st=null;
	   public ResultSet rs,rs1=null;
	   

		public void dbconnection() throws Exception
		{
		     String url="jdbc:mysql://localhost:3306/asterisk";
		     String uname="testuser";
		     String pass="1234";
		try
		{   //System.out.println("ooooookkkkkkkkkk");
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    Connection con=DriverManager.getConnection(url,uname,pass);
		    st=con.createStatement();
		    //st1=con.createStatement();
		    //ResultSet rs=stmt.executeQuery("select * from ivr_info");
		    //while(rs.next())
		    //System.out.println(rs.getInt(1)+"  "+rs.getString(2));
		    //con.close();
		}
		    catch(Exception e)
		    {
		    System.out.println("error connecting");
		    }
		}

	    public void service(AgiRequest request, AgiChannel channel)
	            throws AgiException
	  {
	       try
	   {   
	      
	      // String callEndTime;
	      // String callStartTime;
	       int db_duration;
	       String dialplan_dur;
	       String sql="";
	       String sql1="";
	       String variable;
	       int number;
	       String num;
	       int last_row_num=0;
	       int last_duration=0;
	       int asterisk_dur=0;
	       int    exten;
	          
	      
	       
	       dbconnection();
	        //get duration from dial plan
	       dialplan_dur=request.getParameter("dur");
	      // System.out.println("dialplan duration is"+dialplan_dur);
	       
	       //get source number from dialplan
	  	       //duration=request.getParameter("duration");
	       
	       dbconnection();

	       
	       if(dialplan_dur==null)
	       {
	       variable=request.getParameter("var1");
	       number=Integer.parseInt(variable);	       //duration=request.getParameter("duration");
	       //System.out.println("src no is "+number);
	       //dbconnection();
	    System.out.println("hoiiiiiiiiiiiiiiiiiiiiiiiiiii");
	       rs = st.executeQuery("select * from ivr_info");
	       
	       while(rs.next())
	       {
	    	  
	    	  last_row_num=rs.getInt(1); 
	    	  last_duration=rs.getInt(2);
	       }
	       if(last_row_num==number)
	       {
	    	  
	    	
	      String  lst_duration=Integer.toString(last_duration*1000);
	      channel.setVariable("asterisk_dur", lst_duration);
	      channel.setContext("menu2");
	     
	    	  
	       }
	       else
	       {
	    	  System.out.println("sorry invalid source number");   
	       }
	      }
	       
	      else{
	    	  db_duration=Integer.parseInt(dialplan_dur);
	          String dialplan_no=request.getParameter("src");
		      int db_number=Integer.parseInt(dialplan_no);
	          sql="insert into ivr_info(number,duration)" + "values('"+db_number+"','"+db_duration+"')";
	          st.executeUpdate(sql);
		   
		      
		      System.out.println("dialplan no is "+ dialplan_no);
		      System.out.println("dialplan dur is "+dialplan_dur);
		       
		       
	    	  
	    	  
	      }
	       
	   	       
	  }
	       catch ( Exception e )
	       {

	         System.out.println("Connection failed");


	       }
	   
	 }

	}

