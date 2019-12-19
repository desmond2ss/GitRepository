package com.agile.Utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.log4j.Logger;


public class ConfigFile
{
	private static Logger logger = Logger.getLogger(ConfigFile.class);
	//private static final String BUNDLE_NAME = "eCADTool";
	//private static final ResourceBundle resBundle = ResourceBundle.getBundle(BUNDLE_NAME);
	private static final String AGILE_HOST = "Agile.Host";
	private static final String AGILE_PORT = "Agile.Port";
	private static final String AGILE_USERID = "Agile.username";
	private static final String AGILE_PASSWORD = "Agile.password";
	private static final String AGILE_SITES = "Agile.sites";
	
	private static final String SAP_NAME = "Sap.name";//要连接的SAP名称
	private static final String SAP_CLIENT = "";//SAP连接客户端名称
	private static final String SAP_USERID = "";//用户名
	private static final String SAP_PASSWORD = "";//密码
	private static final String SAP_LANGUAGE = "";//语言
	private static final String SAP_SYSNR = "";//sap系统号码
	private static final String SAP_HOSTNAME = "";//
	
	private static ConfigFile instance;
	private static Properties settings;
	private static String FILE_SEPARATOR = "";
  
	private ConfigFile() throws IOException
	{
		logger.debug("Initial Config File Class");
		settings = new Properties();
		reloadConfig();
	}
	
	public static synchronized ConfigFile getInstance()
	{
		if (instance == null)
		{
			try
			{
				instance = new ConfigFile();
			}
			catch (IOException ex)
			{
				logger.error("Load properties file /AgileReport.properties failed.", ex);
			}
		}
		return instance;
	}
	
	public void reloadConfig() throws IOException
	{
		logger.debug("Load Config File from /AgileReport.properties");
		InputStream in = getClass().getResourceAsStream("/AgileReport.properties");
		settings.load(in);
		in.close();
	}
	
	public void saveConfig()
	{
		logger.info("Save Config file to /AgileReport.properties");
		if (settings != null)
		{
			FileOutputStream out = null;
			try
			{
				String path = ConfigFile.class.getResource(
				"/AgileReport.properties").getPath();
				path = URLDecoder.decode(path);
				out = new FileOutputStream(path);
				settings.store(out, "");
			}	catch (Exception ex)   {
				logger.error("", ex);
			}
			if (out != null)
			{
				try
				{
					out.close();
				}	catch (IOException e)	{
					logger.error("", e);
				}
			}
		}
	}
	
    public String getPath(String name){
    	logger.debug("getPath for project " + name);
    	String [] projects = getValue("OpenProjects").split("\\|");

    	for(int i = 0;i < projects.length; i++)
    	{
    		if(name.equals(projects[i].split("\\?")[0]))
    			return projects[i].split("\\?")[1];
    	}
    	return null;
    }
    
    public static String FILE_SEPARATOR(){
    	if(FILE_SEPARATOR.length() == 0)		  
    		FILE_SEPARATOR = System.getProperty("file.separator");
    	return FILE_SEPARATOR;
    }
	
	public static String getValue(String key)
	{
		return settings.getProperty(key);
	}
  
	public  void setValue(String key, String value)
	{
		settings.put(key, value);
	}

	public  String getHost()
	{
		return getValue(AGILE_HOST);
	}

	public  String getPort()
	{
		return getValue(AGILE_PORT);
	}

	public  String getPassword()
	{
		return getValue(AGILE_PASSWORD);
	}

	public  String getUserID()
	{
		return getValue(AGILE_USERID);
	}
    
	public  void setHost(String value)
	{
		setValue(AGILE_HOST, value);
	}

	public  void setPort(String value)
	{
		setValue(AGILE_PORT, value);
	}

	public  void setPassword(String value)
	{
		setValue(AGILE_PASSWORD, value);
	}

	public  void setUserID(String value)
	{
		setValue(AGILE_USERID, value);
	}
	
	public void setSap_Name(String value){
		setValue(SAP_NAME,value);
	}
	public String getSap_Name(){
		return getValue(SAP_NAME);
	}
	
	public void setSap_Client(String value){
		setValue(SAP_CLIENT,value);
	}
	public String getSap_Client(){
		return getValue(SAP_CLIENT);
	}
	
	public void setSap_UserId(String value){
		setValue(SAP_USERID,value);
	}
	public String getSap_UserId(){
		return getValue(SAP_USERID);
	}
	
	public void setSap_Password(String value){
		setValue(SAP_PASSWORD,value);
	}
	public String getSap_Password(){
		return getValue(SAP_PASSWORD);
	}
	
	public void setSap_Language(String value){
		setValue(SAP_LANGUAGE,value);
	}
	public String getSap_Language(){
		return getValue(SAP_LANGUAGE);
	}
	
	public void setSap_Sysnr(String value){
		setValue(SAP_SYSNR,value);
	}
	public String getSap_Sysnr(){
		return getValue(SAP_SYSNR);
	}
	
	public void setSap_HostName(String value){
		setValue(SAP_HOSTNAME,value);
	}
	public String getSap_HostName(){
		return getValue(SAP_HOSTNAME);
	}
		
}
