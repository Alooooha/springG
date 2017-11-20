package cc.heroy.springG.test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
//	@Test
	public void testResourceLoader() {
		//
		ClassPathXmlApplicationContext c = new ClassPathXmlApplicationContext("classpath:spring.xml");
		User user =(User)c.getBean("user2");
		System.out.println(user.getName());
		System.out.println(user.getBooks());
		System.out.println(user.getId());
	}
	
	//	@Test
	public void jsoupTest() throws Throwable {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
			Resource resource = resourceLoader.getResource("classpath:spring.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(resource.getInputStream());
			Element root = doc.getDocumentElement();
			System.out.println(root.getChildNodes().item(1).getChildNodes().item(1).getAttributes().item(1).getNodeValue());
			System.out.println(root.getChildNodes().item(1).getNodeName());
			
			
//			System.out.println(doc.getDocumentElement());
	}
}