package com.agile.Utils;
//package Utils;
//
//import java.io.ByteArrayInputStream;
//import java.io.UnsupportedEncodingException;
//import java.util.HashMap;
//import java.util.List;
//
//import javax.xml.namespace.QName;
//
//import org.apache.axiom.om.OMAbstractFactory;
//import org.apache.axiom.om.OMElement;
//import org.apache.axiom.om.OMFactory;
//import org.apache.axiom.om.OMNamespace;
//import org.apache.axis2.AxisFault;
//import org.apache.axis2.addressing.EndpointReference;
//import org.apache.axis2.client.Options;
//import org.apache.axis2.client.ServiceClient;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//
//import com.agile.EventAction.KJBRequestDetail;
//
//public class KJBRequest2egYun {
//	
//	private static String soapBindingAddress = "http://cms.egyun.com.cn/wcf/BizlinkService.svc";
//	private static String namespace = "http://tempuri.org/";
//	private static String prefix = "tem";
//	private static String methodName = "SendImpData";
//	private static String resultEYun = "";
//	public static String Agile2egyun(List<KJBRequestDetail> detailList){
//		try {
//			System.out.println("KJBRequest2egYun1");
//			ServiceClient sender = new ServiceClient();
//			EndpointReference endpointReference = new EndpointReference(soapBindingAddress);
//			Options options = new Options();
//			options.setTo(endpointReference);
//			options.setSoapVersionURI(org.apache.axiom.soap.SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
//			options.setAction(namespace + "IBizlinkService/" + methodName);
//			options.setTimeOutInMilliSeconds(60 * 60 * 1000);
//			options.setExceptionToBeThrownOnSOAPFault(false); 	 	
//			sender.setOptions(options);
//			sender.engageModule("addressing");
//			OMFactory fac = OMAbstractFactory.getOMFactory();
//			// 这个和qname差不多，设置命名空间
//			OMNamespace omNs = fac.createOMNamespace(namespace, prefix);
//			System.out.println("KJBRequest2egYun2");
//			OMNamespace omNs1 = fac.createOMNamespace("http://schemas.datacontract.org/2004/07/Lgd.JG.Jobs.ErpData.Bizlink.Entities", "lgd");
//			OMNamespace omNs2 = fac.createOMNamespace("http://www.w3.org/2003/05/soap-envelope", "soap");
//			OMElement SendImpData = fac.createOMElement("SendImpData", omNs);
//			// List<Map<String, Object>> resultList = param.getAttribute5();
//			OMElement body = fac.createOMElement("Body", omNs2);
//			OMElement token = fac.createOMElement("token", omNs);
//			token.setText("5f284bb9804e475c96c11ed561e7b65b");
//			OMElement data = fac.createOMElement("data", omNs);
//			System.out.println("KJBRequest2egYun2");
//	
//			for (KJBRequestDetail kjbRequestDetail : detailList) {	
//				OMElement RvImpData = fac.createOMElement("RvImpData", omNs1);
//				OMElement AgileNo = fac.createOMElement(new QName("lgd:AgileNo"));
//				AgileNo.setText(kjbRequestDetail.getChangeNumer());
//				OMElement CopGNo = fac.createOMElement(new QName("lgd:CopGNo"));
//				CopGNo.setText(kjbRequestDetail.getItemNumber());
//				OMElement Country = fac.createOMElement(new QName("lgd:Country"));
//				Country.setText(kjbRequestDetail.getCountryCode());
//				OMElement Curr = fac.createOMElement(new QName("lgd:Curr"));
//				Curr.setText(kjbRequestDetail.getCurrencyCode());
//				OMElement DecTotal = fac.createOMElement(new QName("lgd:DecTotal"));
//				DecTotal.setText(kjbRequestDetail.getPrice());
//				OMElement ItemNo = fac.createOMElement(new QName("lgd:ItemNo"));
//				ItemNo.setText(kjbRequestDetail.getRowNumber());
//				OMElement NetWt = fac.createOMElement(new QName("lgd:NetWt"));
//				NetWt.setText(kjbRequestDetail.getWeight());
//				OMElement PoNo = fac.createOMElement(new QName("lgd:PoNo"));
//				PoNo.setText(kjbRequestDetail.getPONumber());
//				OMElement Qty = fac.createOMElement(new QName("lgd:Qty"));
//				Qty.setText(kjbRequestDetail.getPOQty());
//				OMElement SNo = fac.createOMElement(new QName("lgd:SNo"));
//				SNo.setText(kjbRequestDetail.getNumber());
//				RvImpData.addChild(AgileNo);
//				RvImpData.addChild(CopGNo);
//				RvImpData.addChild(Country);
//				RvImpData.addChild(Curr);
//				RvImpData.addChild(DecTotal);
//				RvImpData.addChild(ItemNo);
//				RvImpData.addChild(NetWt);
//				RvImpData.addChild(PoNo);
//				RvImpData.addChild(Qty);
//				RvImpData.addChild(SNo);	
//				SendImpData.addChild(token);
//				data.addChild(RvImpData);
//				SendImpData.addChild(data);
//				System.out.println(RvImpData.toString());
//			}
//
//			body.addChild(SendImpData);
//			System.out.println(SendImpData.toString());
//			// 调接口
//			OMElement result = sender.sendReceive(SendImpData);
//			String resultDate = result.toString();
//			HashMap<String, Object> resultMap = new HashMap<String, Object>();
//			SAXReader saxReader = new SAXReader();
//			Document docDom4j = null;
//			try {
//				docDom4j = saxReader.read(new ByteArrayInputStream(resultDate.getBytes("utf-8")));
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			} catch (DocumentException e) {
//				e.printStackTrace();
//			}
//			Element root = docDom4j.getRootElement();      
//	        List<Element> childElements = root.elements();        
//	        for (Element child : childElements) {
//	         List<Element> elementList = child.elements();
//	         for (Element ele : elementList) {             	             
//	             if(ele.getName().equals("Message") || ele.getName().equals("RequestId")) {
//	            	 System.out.println("result:"+ele.getName() + ": " + ele.getText());
//	            	 resultEYun += ele.getName() + "->" + ele.getText()+";";
//	             }
//	            }
//	         }
//		} catch (AxisFault e) {
//			e.printStackTrace();
//		}				
//		return resultEYun;
//	}
//
//}
