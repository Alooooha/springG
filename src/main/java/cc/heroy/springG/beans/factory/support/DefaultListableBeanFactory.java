package cc.heroy.springG.beans.factory.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.config.BeanDefinition;

import cc.heroy.springG.beans.factory.config.ConfigurableListableBeanFactory;

/** 构造bean的核心类 */
public class DefaultListableBeanFactory implements ConfigurableListableBeanFactory{
	/**
	 * 这是beanFactory的几个主要属性，有些属性在源码里是其他类或接口的属性。
	 */
	//本类   存储BeanDefinition对象  这里指已经加载的Bean定义
	private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String,BeanDefinition>();
	
	//本类   存储Bean定义名称对象  使用了volatile关键字，保证了参数的可见性
	private volatile List<String> beanDefinitionNames = new ArrayList<String>(256);
	
	//SimpleAliasRegistry  存储Bean名称和别名
	private final Map<String, String> aliasMap = new ConcurrentHashMap<String, String>(16);
	
	//DefaultSingletonBeanRegistry   存储单例Bean名称-->bean实现
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(256);
	
	//DefaultSingletonBeanRegistry  存储bean名称-->预加载bean实现
	private final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>(16);
	
	//DefaultSingletonBeanRegistry   存储注册过的Bean名
	private final Set<String> registeredSingletons = new LinkedHashSet<String>(256);
	
	
	
}
