package cc.heroy.springG.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import cc.heroy.springG.beans.factory.support.DefaultListableBeanFactory;
import cc.heroy.springG.core.io.DefaultResourceLoader;
import cc.heroy.springG.core.io.Resource;
import cc.heroy.springG.core.io.ResourceLoader;
import cc.heroy.springG.util.Assert;

public class XmlBeanDefinitionReader extends DefaultBeanDefinitionDocumentReader{

	DefaultListableBeanFactory beanFactory ;
	
	public XmlBeanDefinitionReader(DefaultListableBeanFactory beanFactory) {
		this.beanFactory = beanFactory ;
	}
	
	
	public int loadBeanDefinitions(String... locations) throws Throwable {
		Assert.notNull(locations);
		//循环加载location文件
		int count = 0 ;
		for(String location : locations) {
			loadBeanDefinitions(location) ;
			count ++ ;
		}
		return count;
	}
	
	/**
	 * 通过ResourceLoader加载resource
	 */
	public void loadBeanDefinitions(String location) throws Throwable {
		Assert.notNull(location);
		//加载ResoureLoader , DefualtResourceLoader只加载ClassPath文件
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource(location);
		loadBeanDefinitions(resource);
	}
	
	/**
	 *	解析Resource的方法入口，源码将Resource包装成。。。。
	 *	
	 *	不同：
	 *		源码中使用InputResource作为doLoadBeanDefinitions的参数。
	 *		InputResource是InputStream的包装类，具有更多的信息，例如编码，SystemId等信息
	 * @throws Exception 
	 */
	public void loadBeanDefinitions(Resource resource) throws Exception {
		//判断resource是否为空(第一次使用这种判断方法，值得学习)
		Assert.notNull(resource , "resource 必须不能为空！");
		InputStream inputStream = null;
		try {
			//获取InputStream
			inputStream = resource.getInputStream();
			//核心方法
			doLoadBeanDefinitions(inputStream);
		}catch(IOException e) {
			throw new Exception("加载Resource失败!");
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
	protected void doLoadBeanDefinitions(InputStream inputStream) throws Exception{
		//生成Document对象
		Document doc = doLoadDocument(inputStream);
		
		registerBeanDefinitions(doc);
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
