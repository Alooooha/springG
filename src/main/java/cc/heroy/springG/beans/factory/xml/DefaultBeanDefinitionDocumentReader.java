package cc.heroy.springG.beans.factory.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cc.heroy.springG.beans.factory.config.BeanDefinitionHolder;

public class DefaultBeanDefinitionDocumentReader {

	public static final String BEAN_ELEMENT = "bean";
	public static final String ALIAS_ELEMENT = "alias";
	public static final String IMPORT_ELEMENT = "import";
	public static final String NESTED_BEANS_ELEMENT = "beans";
	
	public static final String NAME_ATTRIBUTE = "name";

	public static final String META_ELEMENT = "meta";

	public static final String ID_ATTRIBUTE = "id";

	public static final String PARENT_ATTRIBUTE = "parent";

	public static final String CLASS_ATTRIBUTE = "class";

	public static final String ABSTRACT_ATTRIBUTE = "abstract";

	public static final String SCOPE_ATTRIBUTE = "scope";

	private static final String SINGLETON_ATTRIBUTE = "singleton";

	
	public void registerBeanDefinitions(Document doc) {
		Element root = doc.getDocumentElement();
		doRegisterBeanDefinitions(root);
	}
	
	protected void doRegisterBeanDefinitions(Element root) {
		parseBeanDefinitions(root);
	}
	
	/**
	 * 循环遍历每个子节点，将每个例如“<bean></bean>”的节点解析成BeanDefinition
	 * 不同：
	 * 		源码这里可以判断，当Namespace为默认的时候，使用默认方法解析，但如果不是默认时
	 * 		调用了 parseCustomElement(ele),自定义解析策略
	 */
	protected void parseBeanDefinitions(Element root) {
		//遍历子节点
		NodeList n1 = root.getChildNodes();
		for(int i = 0; i<n1.getLength(); i++) {
			Node node = n1.item(i);
			if(node instanceof Element) {
				Element ele =(Element) node;
				parseDefualtElement(ele);
			}
			
		}
	}
	
	/**
	 * 通过标签名执行不同的解析策略，分别有bean、alias、import、beans等
	 * 这里我只实现bean标签的解析
	 */
	private void parseDefualtElement(Element ele) {
		if(ele.getNodeName().equals(IMPORT_ELEMENT)) {
			
		}
		if(ele.getNodeName().equals(ALIAS_ELEMENT)) {
			
		}
		if(ele.getNodeName().equals(BEAN_ELEMENT)) {
			processBeanDefinition(ele);
		}
		if(ele.getNodeName().equals(NESTED_BEANS_ELEMENT)) {
			
		}
	}
	
	/**
	 * 解析Bean标签方法实现 
	 */
	protected void processBeanDefinition(Element ele) {
		//解析Bean标签，并返回BeanDefinitionHolder对象
		BeanDefinitionHolder bdHolder = parseBeanDefinitionElement(ele);
		
		
	}
	
	/**
	 * 解析Bean标签具体实现方法（核心）
	 */
	public BeanDefinitionHolder parseBeanDefinitionElement(Element ele) {
		
		
		return null;
	}
}
