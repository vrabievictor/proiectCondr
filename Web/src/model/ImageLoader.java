package model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.internal.OraclePreparedStatement;
import oracle.jdbc.internal.OracleResultSet;
import common.DBConnection;


@WebServlet("/ImageLoader")
public class ImageLoader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ImageLoader() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		byte[] content =null;
		OracleConnection c = DBConnection.getConnection();
		try {
//			OraclePreparedStatement st = (OraclePreparedStatement) c.prepareStatement("insert into item_ values('1',?,?,?,'1','1','daa')");
//		    st.setBinaryStream(1, new ByteArrayInputStream(extractBytes("/home/nemo/Desktop/proiectCondr/Web/WebContent/images/dog.jpg")));
//		    st.setBinaryStream(2, new ByteArrayInputStream(extractBytes("/home/nemo/Desktop/proiectCondr/Web/WebContent/images/dogee.png")));
//		    st.setBinaryStream(3, new ByteArrayInputStream(extractBytes("/home/nemo/Desktop/proiectCondr/Web/WebContent/images/yoda.jpg")));
//		    st.executeUpdate();
//		    st.close();
//		    c.commit();
			OraclePreparedStatement st = (OraclePreparedStatement) c.prepareStatement("select image1 from item_ where id='1'");
		    OracleResultSet r = (OracleResultSet) st.executeQuery();
		    if (r.next()) {
		    	content=r.getBytes(1);
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		response.setContentType(getServletContext().getMimeType("x.jpg"));
        response.setContentLength(content.length);
        response.getOutputStream().write(content);
        response.getOutputStream().close();
	}
	
	public byte[] extractBytes (String ImageName) throws IOException {
		 
		File fi = new File(ImageName);
		byte[] fileContent = Files.readAllBytes(fi.toPath());

		 return fileContent;
		}

}
