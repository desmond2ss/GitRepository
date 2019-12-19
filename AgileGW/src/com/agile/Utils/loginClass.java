package com.agile.Utils;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.agile.api.APIException;
import com.agile.api.AgileSessionFactory;
import com.agile.api.IAgileSession;

public class loginClass {

	private static final Logger logger = Logger.getLogger(loginClass.class);
	private static final int MAX_TIMEOUT = 20;
	private static IAgileSession m_session;

	/**
	 * 创建SESSION 会话
	 * 
	 * @return
	 * @throws Exception
	 */
	public IAgileSession login() throws Exception {
		HashMap<Integer, String> params = new HashMap<Integer, String>();
		params.put(AgileSessionFactory.USERNAME, ConfigFile.getInstance().getUserID());
		params.put(AgileSessionFactory.PASSWORD, ConfigFile.getInstance().getPassword());

		IAgileSession session;
		String url = "http://" + ConfigFile.getInstance().getHost() + ":"
				+ ConfigFile.getInstance().getPort() + "/Agile";
		System.out.println(url);
		//String url = "http://10.10.10.114:7001/Agile";
		try {
			logger.info("Agile Login URL:" + url + " username: "
					+ ConfigFile.getInstance().getUserID() + " password:"
					+ ConfigFile.getInstance().getPassword());
			
			AgileSessionFactory instance = AgileSessionFactory.getInstance(url);
			session = instance.createSession(params);
			m_session = session;
			session.setTimeout(MAX_TIMEOUT);
			
			logger.info(ConfigFile.getInstance().getUserID()
					+ " has logon successfully");
			
		} catch (APIException apie) {
			logger.error("", apie);
			disconnect();
			if (Integer.parseInt(apie.getErrorCode().toString()) == 60086) {
				Throwable rootCause = apie.getRootCause();
				throw new Exception(rootCause.getMessage()+ "; login()方法APIException异常");
			}
			throw new Exception(apie.getMessage()
					+ "; login()方法Exception异常");
		} catch (Exception e) {
			disconnect();
			logger.error("Login fail!");
			logger.error("", e);
			throw new Exception(e.getMessage() + "; login()方法异常");
			// return null;
		}

		AgileSessionFactory factory = AgileSessionFactory.getInstance(url);
		session = factory.createSession(params);
		System.out.println("session = " + session.toString());
		return session;
	}

	public void disconnect() {
		if (m_session != null) {
			m_session.close();
			logger.info("loggeron out successfully");
		}
		m_session = null;
	}

	public static void main(String[] args) throws Exception {
		loginClass test1 = new loginClass();
		IAgileSession session = test1.login();
		System.out.println("mession = " + session);
	}
}
