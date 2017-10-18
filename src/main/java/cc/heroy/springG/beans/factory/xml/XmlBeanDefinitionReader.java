package cc.heroy.springG.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.util.Assert;
import org.w3c.dom.Document;

import cc.heroy.springG.core.io.Resource;

public class XmlBeanDefinitionReader {

	
	/**
	 *	解析Resource的方法入口，源码将Resource包装成。。。。
	 *	
	 *	不同：
	 *		源码中使用InputResource作为doLoadBeanDefinitions的参数。
	 *		InputResource是InputStream的包装类，具有更多的信息，例如编码，SystemId等信息
	 * @throws Exception 
	 */
	public int loadBeanDefinitions(Resource resource) throws Exception {
		//判断resource是否为空(第一次使用这种判断方法，值得学习)
		Assert.notNull(resource , "resource 必须不能为空！");
		
		InputStream inputStream = null;
		try {
			//获取InputStream
			inputStream = resource.getInputStream();
			//核心方法
			return doLoadBeanDefinitions(inputStream);
		}catch(IOException e) {
			throw new Exception("解析Resource失败!");
		}finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 执行解析bean定义的方法
	 */
	protected int doLoadBeanDefinitions(InputStream inputStream) throws Exception{
		//生成Document对象
		Document doc = doLoadDocument(inputStream);
		
		return registerBeanDefinitions(doc);
	}
	
	/**
	 * 
	 */
	public int registerBeanDefinitions(Document doc) throws Exception {
//		BeanDefinitionDocumentReader documentReader = createBeanDefinitionDocumentReader();
		
//		int countBefore = getRegistry().getBeanDefinitionCount();
//		documentReader.registerBeanDefinitions(doc, createReaderContext(resource));
		return 0;
	}
	
	
	/**
	 * 工具类，通过InputStream生成Document 
	 */
	protected Document doLoadDocument(InputStream inputStream) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(inputStream);
		return doc;
	}
}
