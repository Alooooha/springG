package cc.heroy.springG.test;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cc.heroy.springG.context.support.ClassPathXmlApplicationContext;
import cc.heroy.springG.core.io.DefaultResourceLoader;
import cc.heroy.springG.core.io.Resource;
import cc.heroy.springG.core.io.ResourceLoader;


public class TT {
	
	/**
	 * 测试ResourceLoader实现 (OK)
	 */
	@Test
	public void testResourceLoader() {
		//
		ClassPathXmlApplicationContext c = new ClassPathXmlApplicationContext("classpath:spring.xml");
		User user = (User)c.getBean("user2");
		System.out.println(user.getId());
		System.out.println(user.getName());
		System.out.println(user.getBooks().size());
	}
	
	/**
	 * 用来处理InputStream
	 * @param is
	 */
	private void readInputStream(InputStream is) {
		byte[] b = new byte[1024];
		try {
			while(is.read(b) != -1) {
				System.out.println(new String(b));
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				is.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void jsoupTest() throws Throwable {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
			Resource resource = resourceLoader.getResource("classpath:spring.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(resource.getInputStream());
			Element root = doc.getDocumentElement();
//			System.out.println(root.getChildNodes().item(1).getChildNodes().item(1).getAttributes().item(1).getNodeValue());
//			System.out.println(root.getChildNodes().item(1).getNodeName());
			
			
//			System.out.println(doc.getDocumentElement());
	}
}