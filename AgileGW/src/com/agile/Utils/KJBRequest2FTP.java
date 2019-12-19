package com.agile.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class KJBRequest2FTP {

	public static Boolean UploadFTP(String fileName, InputStream is) {
		Boolean result = null;
		FTPClient ftp = new FTPClient();
		try {
			ftp.connect("10.10.10.121");
			ftp.login( "a2in" , "Ab-123456" );
			ftp.setControlEncoding("iso-8859-1");
			boolean hasDir = ftp.changeWorkingDirectory( "AgileIn" );
			System.out.println(hasDir);
			ftp.setFileType(FTP.BINARY_FILE_TYPE);	        
	        result = ftp.storeFile(new String(fileName.getBytes("GBK"),"iso-8859-1"), is);
			System.out.println("Result:"+ result);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return result;
	}
	
	

}
