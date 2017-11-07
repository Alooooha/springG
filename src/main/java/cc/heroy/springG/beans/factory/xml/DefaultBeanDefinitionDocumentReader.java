package cc.heroy.springG.beans.factory.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cc.heroy.springG.beans.PropertyValue;
import cc.heroy.springG.beans.factory.config.BeanDefinition;
import cc.heroy.springG.beans.factory.config.BeanDefinitionHolder;
import cc.heroy.springG.beans.factory.config.ConstructorArgumentValues;
import cc.heroy.springG.beans.factory.support.AbstractBeanDefinition;
import cc.heroy.springG.beans.factory.support.DefaultListableBeanFactory;
import cc.heroy.springG.beans.factory.support.GenericBeanDefinition;
import cc.heroy.springG.util.StringUtils;

public class DefaultBeanDefinitionDocumentReader {

	public static final String BEAN_ELEMENT = "bean";
	public static final String ALIAS_ELEMENT = "alias";
	public static final String IMPORT_ELEMENT = "import";
	public static final String NESTED_BEANS_ELEMENT = "beans";
	public static final String TRUE_VALUE = "true";
	public static final String NAME_ATTRIBUTE = "name";
	public static final String INDEX_ATTRIBUTE = "index";
	public static final String TYPE_ATTRIBUTE = "type";
	public static final String ID_ATTRIBUTE = "id";

	public static final String PARENT_ATTRIBUTE = "parent";
	public static final String VALUE_ATTRIBUTE = "value";
	public static final String CLASS_ATTRIBUTE = "class";
	public static final String DEPENDS_ON_ATTRIBUTE = "depends-on";
	public static final String ABSTRACT_ATTRIBUTE = "abstract";
	public static final String DEFAULT_VALUE = "default";
	public static final String SCOPE_ATTRIBUTE = "scope";
	public static final String LAZY_INIT_ATTRIBUTE = "lazy-init";
	public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
	public static final String PROPERTY_ELEMENT = "property";
	public static final String LIST_ATTRIBUTE = "list";
	
//	private static final String SINGLETON_ATTRIBUTE = "singleton";
	//分隔符
	private static final String MULTI_VALUE_ATTRIBUTE_DELIMITERS = ",";
//	private static final String BeanDefinitionHolder = null;
	//存放被使用的beanName
	private final Set<String> usedNames = new HashSet<String>();
	private DefaultListableBeanFactory beanFactory ;
	public void registerBeanDefinitions(Document doc) throws Exception {
		Element root = doc.getDocumentElement();
		doRegisterBeanDefinitions(root);
	}
	
	protected void doRegisterBeanDefinitions(Element root) throws Exception {
		parseBeanDefinitions(root);
System.out.println("解析配置文件完成!");
	}
	
	/**
	 * 循环遍历每个子节点，将每个例如“<bean></bean>”的节点解析成BeanDefinition
	 * 不同：
	 * 		源码这里可以判断，当Namespace为默认的时候，使用默认方法解析，但如果不是默认时
	 * 		调用了 parseCustomElement(ele),自定义解析策略
	 * @throws Exception 
	 */
	protected void parseBeanDefinitions(Element root) throws Exception {
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
	 * @throws Exception 
	 */
	private void parseDefualtElement(Element ele) throws Exception {
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
	 * 解析Bean标签方法流程
	 * @throws Exception 
	 */
	protected void processBeanDefinition(Element ele) throws Exception {
		//解析Bean标签，并返回BeanDefinitionHolder对象
		BeanDefinitionHolder bdHolder = parseBeanDefinitionElement(ele);
		
		//这里有个decoreteBeanDefinitionIfRequired() 方法
		//用于装饰bean定义，比如AOP的场景
		//暂时没有实现
		
		//这里写注册
		registerBeanDefinition(bdHolder);
		
System.out.println("注册 ！");
	}
	
	/**
	 * 解析Bean标签实现流程方法（核心）
	 * 
	 * Bean定义规则
	 * 1、默认的beanName就是id
	 * 2、<bean>标签可以定义name属性、一个bean可以有多个别名
	 * 3、假如id未定义，将使用name属性第一个别名作为beanName
	 * @throws Exception 
	 */
	public BeanDefinitionHolder parseBeanDefinitionElement(Element ele) throws Exception {
		String id = ele.getAttribute(ID_ATTRIBUTE);
		String nameAttr = ele.getAttribute(NAME_ATTRIBUTE);
		if(id.equals("")) {
			id = null;
		}
		if(nameAttr.equals("")) {
			nameAttr = null;
		}
		
		//获取别名，如果有，则存放到list中
		List<String> aliases = new ArrayList<String>();
		if(nameAttr != null ) {
			String[] nameArr = nameAttr.split(MULTI_VALUE_ATTRIBUTE_DELIMITERS);
			aliases.addAll(Arrays.asList(nameArr));
		}
		
		//得到beanName
		String beanName = id;
		if(id == null && !aliases.isEmpty()) {
			//将别名第一个作为beanName
			beanName = aliases.get(0);
			aliases.remove(0);
		}
		
		//判断beanName和别名唯一性
		checkNameUniqueness(beanName , aliases);
		
		//解析并返回beanDefinition(重要)
		AbstractBeanDefinition beanDefinition = parseBeanDefinitionElement(ele,beanName);
		
		//判断
		if(beanDefinition != null) {
			//将list转为String数组
			String[] aliasesArray = StringUtils.toStringArray(aliases);
			return new BeanDefinitionHolder(beanDefinition,beanName,aliasesArray);
		}
		
		return null;
	}
	
	/**
	 * 功能：判断beanName和aliases是否已经被其他bean注册
	 */
	protected void checkNameUniqueness(String beanName, List<String> aliases) throws Exception {
		String foundName = null;

		if (beanName != null && this.usedNames.contains(beanName)) {
			foundName = beanName;
		}
		if (!isAliasesUniqueness(aliases) || foundName != null) {
			throw new Exception("Bean中id 或 name与其他Bean重复 !");
		}
		
		this.usedNames.add(beanName);
		this.usedNames.addAll(aliases);
	}
	
	/**
	 * 自定义方法，判断别名是否与usedNames重复
	 */
	protected boolean isAliasesUniqueness(List<String> aliases) {
		for(String str : aliases) {
			if(usedNames.contains(str)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * 解析Element元素所有信息，生成并返回BeanDefinition
	 * 
	 * parent (子bean的属性模板)
	 * 		1、子bean继承父bean中的属性，减少了重复代码的使用
	 * 		2、子bean与父bean不一定是继承关系
	 * 		3、父bean更像一个模板，子bean能够自动使用父bean中的配置
	 * 		4、父bean中属性子bean也要有
	 * 		5、父bean可以没有class属性，只是用来子bean继承
	 */
	public AbstractBeanDefinition parseBeanDefinitionElement(Element ele ,String beanName) {
		//获取Class属性
		String className = null;
		if(ele.hasAttribute(CLASS_ATTRIBUTE)) {
			className = ele.getAttribute(CLASS_ATTRIBUTE);
		}
		//获取parent属性
		String parent = null;
		if(ele.hasAttribute(PARENT_ATTRIBUTE)) {
			parent = ele.getAttribute(PARENT_ATTRIBUTE);
		}
		
			//创建DefaultBeanDefinition
			AbstractBeanDefinition bd = createBeanDefinition(parent, className, this.getClass().getClassLoader());
			
			//解析Bean节点属性，并设置bd属性值
			parseBeanDefinitionAttributes(ele,beanName,bd);
			
			//解析constructor-arg (暂时没完成，等写bean加载阶段了解BeanDefinition结构再回头写)
			parseConstructorArgElements(ele,bd);
			
			//解析property等
			parsePropertyElements(ele,bd);
			
			//设置resource
			
		return bd;
			
	}
	
	/**
	 * 1、创建beanDefinition
	 * 2、设置parent属性
	 * 3、判断className是否为空，如果有classLoader，生成class类传入bd、否则传className
	 * 4、返回beanDefinition
	 * 
	 * 这里同时校验了类是否存在
	 */
	public AbstractBeanDefinition createBeanDefinition( String parentName, String className, ClassLoader classLoader){
		GenericBeanDefinition bd = new GenericBeanDefinition();
		//设置parentName
		bd.setParentName(parentName);
		
		if(className != null) {
			if(classLoader != null) {
				try {
					bd.setBeanClass(classLoader.loadClass(className));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			else {
				bd.setBeanClassName(className);
			}
		}
		return bd;
	}
	
	/**
	 * 解析bean标签具体实现
	 * 1、scope属性解析    有3个判断
	 * 		首先判断是否为老版本配置：以singleton为属性名，如果是，则出错
	 * 		再判断是否以为现有版本bean配置：以scope为属性名，如果有，则添加
	 * 		最后如果没有“scope”，则设置为""
	 * 2、lazy-init
	 * 		延迟加载，在容器初始化阶段默认加载所有单例bean
	 * 		默认：false  即容器启动时加载
	 * 
	 * 3、depends-on
	 * 		依赖bean，可以添加多个，即在加载当前bean时必须先加载依赖bean
	 */
	public AbstractBeanDefinition parseBeanDefinitionAttributes(Element ele, String beanName,
		AbstractBeanDefinition bd) {
		//scope 属性 
		if(ele.hasAttribute(SCOPE_ATTRIBUTE)) {
			bd.setScope(ele.getAttribute(SCOPE_ATTRIBUTE));
		}else {
			bd.setScope("");
		}
		
		//abstract (boolean)
		if(ele.hasAttribute(ABSTRACT_ATTRIBUTE)) {
			bd.setAbstract(TRUE_VALUE.equals(ele.getAttribute(ABSTRACT_ATTRIBUTE)));
		}
		
		//lazy-init (defualt , true ,false)
		String lazyInit = ele.getAttribute(LAZY_INIT_ATTRIBUTE);
		
		if(DEFAULT_VALUE.equals(lazyInit)) {
			lazyInit = TRUE_VALUE;
		}
		bd.setLazyInit(TRUE_VALUE.equals(lazyInit));
		
		//autowire 未实现
		
		//depends-on
		if(ele.hasAttribute(DEPENDS_ON_ATTRIBUTE)) {
			String dependsOn = ele.getAttribute(DEPENDS_ON_ATTRIBUTE);
			//生成数组
			String[] depends = dependsOn.split(MULTI_VALUE_ATTRIBUTE_DELIMITERS);
			bd.setDependsOn(depends);
		}
		
		//primary 未实现
		
		//init-method 未实现
		
		//destory-method 未实现
		
		//factory-method 未实现
		
		//factory-bean 未实现
		
		return bd;
	}
	
	/**
	 * 解析constructor-arg节点流程方法
	 * 		index ：
	 * 		type ：
	 * 		value ：
	 */
	public void parseConstructorArgElements(Element beanEle, BeanDefinition bd) {
		//获得bean的子节点集合,遍历每个子节点
		NodeList nl = beanEle.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (nodeNameEquals(node, CONSTRUCTOR_ARG_ELEMENT)) {
				//解析constructor-arg节点，将数据存到BeanDefinition中
				parseConstructorArgElement((Element) node, bd);
			}
		}
	}
	
	/**
	 * 解析property节点
	 */
	public void parsePropertyElements(Element beanEle ,BeanDefinition bd) {
		NodeList n1 = beanEle.getChildNodes();
		for(int i = 0;i < n1.getLength() ; i++) {
			Node node = n1.item(i);
			if(nodeNameEquals(node, PROPERTY_ELEMENT)) {
				try {
					if(node instanceof Element)
						parsePropertyElement((Element)node, bd);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 具体解析property标签
	 * @throws Exception 
	 */
	protected void parsePropertyElement(Element ele, BeanDefinition bd) throws Exception {
		//获取property的name属性
		String propertyName = ele.getAttribute(NAME_ATTRIBUTE);
		//判断name是否为空
		if(propertyName == null || "".equals(propertyName)) {
			throw new Exception("property 中name不能为空！");
		}
		
		//判断是否重复定义
		if(bd.getPropertyValues().contains(propertyName)) {
			throw new Exception("重复定义property ："+ propertyName);
		}
		
		//解析propertyValue的value
		Object obj = parsePropertyValue(ele,bd,propertyName);
		
		PropertyValue pv = new PropertyValue(propertyName,obj);
		bd.getPropertyValues().addPropertyValue(pv);
	}

	/**
	 * 	解析constructor-arg的具体实现：
	 * 	1、解析出 index、type、name属性
	 *  2、判断index是否为空
	 *  	一：不为空
	 *  		1、判断index是否合法
	 *  		2、获取value，并封装成ValueHolder对象
	 *  		3、添加type、name属性
	 *  		4、在BeanDefinition的ConstructorArgumentValue对象中添加属性
	 *  	
	 *  	二：index为空
	 *  		执行2、3、4(存储位置不同)
	 * 
	 */
	protected void parseConstructorArgElement(Element ele, BeanDefinition bd) {
		String indexAttr = ele.getAttribute(INDEX_ATTRIBUTE);
		String typeAttr = ele.getAttribute(TYPE_ATTRIBUTE);
		String nameAttr = ele.getAttribute(NAME_ATTRIBUTE);
		
		if(indexAttr != null && !"".equals(indexAttr)) {
			int index = Integer.parseInt(indexAttr);
			//判断index是否合法
			if(index < 0 ) {
				try {
					throw new Exception("index 不能为负数");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//这里有个parsePropertyValue()方法，用于解析各种参数值，暂时不用
			//现在默认把值放在constructor-arg标签下
			Object value = ele.getAttribute("value");
			//生成ConstructorArgumentValues.ValueHolder信息类
			ConstructorArgumentValues.ValueHolder valueHolder = new ConstructorArgumentValues.ValueHolder(value);
			
			//添加type、name属性
			if(!"".equals(typeAttr)) {
				valueHolder.setType(typeAttr);
			}
			if(!"".equals(nameAttr)) {
				valueHolder.setName(nameAttr);
			}
			
			//在BeanDefinition的ConstructorArgumentValues属性下添加值
			bd.getConstructorArgumentValues().addIndexedArgumentValue(index, valueHolder);
		}
		else {
			Object value = ele.getAttribute("value");
			ConstructorArgumentValues.ValueHolder valueHolder = new ConstructorArgumentValues.ValueHolder(value);
			if(!"".equals(typeAttr)) {
				valueHolder.setType(typeAttr);
			}
			if(!"".equals(nameAttr)) {
				valueHolder.setName(nameAttr);
			}
			bd.getConstructorArgumentValues().addGenericArgumentValue(valueHolder);
		}
	}
	
	/**
	 * 	解析property节点中的value值
	 * 	目前只能解析两种形式的属性
	 * 1、value
	 * 		value包含两种写法：<value>18</value> (在property节点下构建新的节点)
	 * 						  <property name="age" value="18"></property>(在property中直接定义value)
	 * 2 、list
	 * 		<property name = "books">
	 * 			<list>
	 * 				<value>线性代数</value>
	 * 				<value>高等数学</value>
	 * 				<value>概率论</value>
	 * 			</list>
	 * 		</property>
	 */
	protected Object parsePropertyValue(Element ele, BeanDefinition bd, String propertyName) {
		//判断是否为value解析的第二种情况
		String value = ele.getAttribute(VALUE_ATTRIBUTE);
		if(value != null && !value.equals("")) {
			return value;
		}
		//得到子节点
		NodeList n1 = ele.getChildNodes();
		Element subElement = null;
		for(int i = 0; i < n1.getLength(); i++) {
			Node node = n1.item(i);
			if(node instanceof Element) {
				subElement = (Element) node;
			}
		}
		//解析value、list
		boolean hasValueAttribute = subElement.getTagName().equals(VALUE_ATTRIBUTE);
		boolean hasListAttribute = subElement.getTagName().equals(LIST_ATTRIBUTE);
		if( (hasValueAttribute && hasListAttribute) || (!hasValueAttribute && !hasListAttribute) || subElement == null) {
			try {
			throw new Exception("value 和 list标签只能有一个");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		//解析value标签
		if(hasValueAttribute) {
			String v = subElement.getTextContent();
			return v;
		}else if(hasListAttribute) {
			NodeList nodeList = ele.getChildNodes();
			List<String> list = new ArrayList<String>();
			for(int i = 0; i < nodeList.getLength(); i++) {
				Node n = nodeList.item(i);
				String v = n.getTextContent();
				list.add(v);
			}
			return list;
		}
		return null;
	}

	/**
	 * 通过BeanDefinitionHolder注册到BeanFactory
	 * 两步：
	 * 	1.注册BeanDefinition到BeanFactory的beanDefinitionMap、beanName注册到beanDefinitonNames
	 *  2.注册aliases到aliasMap
	 * 
	 * @param bdHolder
	 */
	public void registerBeanDefinition(BeanDefinitionHolder bdHolder){
		DefaultListableBeanFactory beanFactory = getBeanFactory();
		String beanName = bdHolder.getBeanName();
		//注册bean定义
		beanFactory.registerBeanDefinition(beanName, bdHolder.getBeanDefinition());
		//注册别名
		String[] aliases = bdHolder.getAliases();
		for(String alias : aliases) {
			try {
				beanFactory.registerAlias(beanName, alias);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 工具方法，比较节点名
	 */
	protected boolean nodeNameEquals(Node node ,String nodeName) {
		String n = node.getNodeName();
		if(n != null)
			return n.equals(nodeName);
		return false;
	}

	public DefaultListableBeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(DefaultListableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
}
