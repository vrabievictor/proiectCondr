package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.internal.OraclePreparedStatement;
import common.DBConnection;


public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Controller() {
        super();
        new Thread(){
        	public void run() {
        		while (true) {
	        		Calendar date = new GregorianCalendar();
	        		date.set(Calendar.HOUR_OF_DAY, 0);
	        		date.set(Calendar.MINUTE, 0);
	        		date.set(Calendar.SECOND, 0);
	        		date.set(Calendar.MILLISECOND, 0);
	        		long x = 24*3600*1000-new Date().getTime() + date.getTime().getTime();
	        		
	        		try {
	        			System.out.println("astept ora 00:00 ca sa apelez trigger ul pentru a sterge conturile ");
						Thread.sleep(x);
	        			 OracleConnection c = DBConnection.getConnection();
						  OraclePreparedStatement st = (OraclePreparedStatement) c.prepareStatement("update util set id = ?");
						  st.setInt(1, 4);
						  st.executeUpdate();
						st.close();
						c.commit();
						System.out.println("gata");
						
					} catch (SQLException e) {
						e.printStackTrace();
					}catch (InterruptedException e) {
						e.printStackTrace();
					}
        		}
 
        	};
        	
        }.start();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url=request.getRequestURL().toString();
		
		String result="";
		
		RequestDispatcher requestDispatcher=null;
		
		int i=url.length()-1;
		
		while (i>=0 && url.charAt(i)!='/') {
			result=url.charAt(i)+result;
			i--;
		}

		if (result.equals("ImageLoader"))
			 requestDispatcher = request.getRequestDispatcher("/ImageLoader"); else
	    if (result.equals("Scan"))
		     requestDispatcher = request.getRequestDispatcher("/Scan"); else
		if (result.equals("DeleteAcount"))
			 requestDispatcher = request.getRequestDispatcher("/DeleteAcount"); else
		if (result.equals("UserProfile"))
			 requestDispatcher = request.getRequestDispatcher("/UserProfile"); else
		if (result.equals("MainPageModel"))
			 requestDispatcher = request.getRequestDispatcher("/MainPageModel"); else 
		if (result.equals("Logout"))
			 requestDispatcher = request.getRequestDispatcher("/Logout"); else 
		if (result.equals("LoginFail"))
			 requestDispatcher = request.getRequestDispatcher("/LoginFail"); else 
		if (result.equals("UserProfile"))
			 requestDispatcher = request.getRequestDispatcher("/UserProfile"); else 
	    if (result.equals("Test"))
		 requestDispatcher = request.getRequestDispatcher("/Test"); else 
			 if (result.equals("TestL"))
				 requestDispatcher = request.getRequestDispatcher("/TestL"); else 
	    if (result.equals("Login"))
		 requestDispatcher = request.getRequestDispatcher("/Login"); else 
		if (request.getSession().getAttribute("name")==null)
	     requestDispatcher = request.getRequestDispatcher("/MainPageModel"); else 
	    	 requestDispatcher = request.getRequestDispatcher("/UserProfile");
	    
		 	 
		requestDispatcher.forward(request, response);
        
	}

}