package model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TestL")
public class TestL extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public TestL() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String res = getResponseFrom("https://freegeoip.net/json"),x,y;
        //System.out.println(res);
		x= res.split(":")[9].split(",")[0];
    	y= res.split(":")[10].split(",")[0]; 
    	
    	response.getOutputStream().println("<html><head/> <body>");

	    res = getResponseFrom("http://samples.openweathermap.org/data/2.5/weather?lat="+x+"&lon="+y+"&appid=5320ff8302cdcb9ebfbca1142b1a2df5");
    	response.getOutputStream().println("Temp "+(new Double(res.split("temp")[1].split(",")[0].split(":")[1])-273)+" celsius");
    	response.getOutputStream().println("<br/>");
    	response.getOutputStream().println("Humidity "+res.split("humidity")[1].split(",")[0].split(":")[1]);
    	response.getOutputStream().println("<br/>");
    	String ln="http://openweathermap.org/img/w/"+res.split("icon")[1].split("\"")[2]+".png";
    	response.getOutputStream().println("<img src="+ln+" />");
    	response.getOutputStream().println("</body></html>");
	}
	
	public String  getResponseFrom(String adr) {
		URL url = null;
		try {
			url = new URL(adr);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		String ab = "";

	    HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
		    
	        connection.connect();
	        
	        String x,y;
		    
	        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
		        
	        	Scanner a = new Scanner (connection.getInputStream());
	        	
	        	while (a.hasNext()) ab+=a.next();
	        	
	        	//http://samples.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=5320ff8302cdcb9ebfbca1142b1a2df5

	        } 
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
        
        return ab;
    }

}
