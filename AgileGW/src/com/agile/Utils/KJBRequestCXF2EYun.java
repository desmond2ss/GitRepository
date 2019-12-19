package com.agile.Utils;
//package Utils;
//
//import javax.xml.namespace.QName;
//
//import org.apache.cxf.endpoint.Client;
//import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
//
//public class KJBRequestCXF2EYun {
//	
//	private static String url = "http://cms.egyun.com.cn/wcf/BizlinkService.svc?wsdl";
//	private static String namespace = "http://tempuri.org/";
//	private static String methodName = "SendImpData";
//	
//	public static void main(String[] args) {
//		String msg = callWebservice();
//		System.out.println(msg);
//	}
//	public static String callWebservice() {
//		JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
//		Client client = clientFactory.createClient(url);
//		QName name = new QName(namespace,methodName);
//		Object[] objects = null;
//		try {
//			objects = client.invoke(name, "222");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return objects.toString();
//		
//	}
//
//}
