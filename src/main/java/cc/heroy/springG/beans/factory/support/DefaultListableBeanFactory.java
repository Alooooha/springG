package cc.heroy.springG.beans.factory.support;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import cc.heroy.springG.beans.factory.config.BeanDefinition;
import cc.heroy.springG.beans.factory.config.ConfigurableListableBeanFactory;
import cc.heroy.springG.beans.factory.config.ConstructorArgumentValues;
import cc.heroy.springG.beans.factory.config.ConstructorArgumentValues.ValueHolder;
import cc.heroy.springG.util.Assert;

/** 构造bean的核心类 */
public class DefaultListableBeanFactory extends AbstractBeanFactory implements ConfigurableListableBeanFactory{
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
	
	/**
	 * 注册bean定义
	 */
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
		Assert.notNull(beanName);
		Assert.notNull(beanDefinition);
		
		//获取beanName相同，老的bean定义
		BeanDefinition oldBeanDefinition;
		oldBeanDefinition = beanDefinitionMap.get(beanName);
		if(oldBeanDefinition != null) {
			//替换beanDefinition
			beanDefinitionMap.put(beanName, beanDefinition);
		}else {
			beanDefinitionMap.put(beanName, beanDefinition);
		}
		beanDefinitionNames.add(beanName);
	}
	
	/**
	 * 注册别名
	 */
	public void registerAlias(String name, String alias) throws Exception {
		Assert.notNull(name, "'name' 不能为空");
		Assert.notNull(alias, "'alias' 不能为空");
		if (alias.equals(name)) {
			this.aliasMap.remove(alias);
		}
		else {
			String registeredName = this.aliasMap.get(alias);
			if (registeredName != null) {
				//若注册bean为同一个beanName，不添加，返回
				if (registeredName.equals(name)) {
					return;
				}
				throw new Exception(name+" 的别名 "+alias+" 已经被注册!");
			}
			this.aliasMap.put(alias, name);
		}
	}

	/**
	 * 初始化单例bean的核心方法
	 * 
	 */
	public void preInstantiateSingletons() {
		//获取beanNames
		List<String> beanNames = new ArrayList<String>(this.beanDefinitionNames);
		for(String beanName : beanNames) {
			BeanDefinition bd = beanDefinitionMap.get(beanName);
			
			//初始化bean条件：LazyInit 为false、Singleton 为 true、Abstract 为false
			if(!bd.isLazyInit() && bd.isSingleton() && !bd.isAbstract()) {
				//这里有个判断，isFactroyBean()
				//如果true，将用工厂方法实例化Bean
				//未实现
				
				try {
					getBean(beanName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}

	@Override
	protected Map<String, BeanDefinition> getBeanDefinitionMap() {
		return beanDefinitionMap;
	}

	/**
	 * 创建Bean实例
	 * 发现一个Spring的方法名书写风格：
	 * 如：
	 * createBean():执行创建Bean的流程，其中有前期对参数的判断、赋值等准备工作，然后
	 * 				执行doCreateBean()，最后有对结果的处理
	 * doCreateBean():创建Bean的具体动作、细节等
	 * 
	 * 这种方法值得学习
	 */
	@Override
	protected Object createBean(String beanName, BeanDefinition bd) {
		//核心方法  doCreateBean(beanName,bd),在这里直接写实现
		if(bd.isSingleton()) {
			//从factoryBeanInstanceCache中找是否存在该bean
		}
		//获取Bean实例，封装成BeanWrapper
		createBeanInstance(beanName,bd);
		
		
		return null;
	}

	/**
	 * 两种创建Bean方法：
	 * 1、在配置文件中有constructor-arg参数
	 * 2、默认构造方法，没有构造参数
	 * 
	 * 返回BeanWrapper
	 */
	@Override
	protected void createBeanInstance(String beanName, BeanDefinition bd) {
		//配置文件方式
//		autoreConstructor(beanName,bd);
		//默认构造方法
		instantiateBean(beanName,bd);
		
	}
	
	/**
	 * 默认构造方法实现
	 * 	1、访问构造方法不是public的问题
	 * 		用 getDeclaredConstructors() 返回可以不是public的方法对象
	 * 		调用setAccessible(true)抑制Java访问权限的检查(即可以使用任何修饰符的方法和参数)
	 * 
	 * 
	 * 返回：BeanWrapper
	 */
	protected void instantiateBean(String beanName,BeanDefinition bd) {
		try {
		Object beanInstance ;
		
		//获取Class类
		Class<?> clazz = bd.getBeanClass();
		//判断是否为接口
		if(clazz.isInterface())
			throw new Exception(clazz+"是接口!");
		
		//取得构造方法对象数组
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		Constructor<?> constructor = null;
		for(Constructor<?> c : constructors) {
			if(c.getParameterCount() == 0) {
				//设置是否可以访问
				c.setAccessible(true);
				constructor = c;
				break;
			};
		}
		if(constructor != null) {
			beanInstance = constructor.newInstance();
		}
		//包装成BeanWrapper类
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 配置文件参数自动注入构造方法(未完成)
	 */
	protected void autoreConstructor(String beanName,BeanDefinition bd ) {
		Constructor<?> constructorToUse = null;
		Set<Class> t = new HashSet<Class>();
		List<Constructor<?>> tempList = new ArrayList<Constructor<?>>();
		//取得存储构造信息的类
		try {
		ConstructorArgumentValues cargs = bd.getConstructorArgumentValues();
		int pi = cargs.getIndexedArgumentValues().size();
		//循环取得参数内容
		Map<Integer,ValueHolder> indexArg = cargs.getIndexedArgumentValues();
		List<ValueHolder> genericArg = cargs.getGenericArgumentValue();
		Class<?> clazz = bd.getBeanClass();
		if(clazz.isInterface())
			throw new Exception(clazz+"是接口!");
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		
		//选择参数内容
		
		//寻找合适的构造器(有index型)
		for(Constructor<?> constructor : constructors) {
			if (constructor.getParameterCount() == indexArg.size()) {
				tempList.add(constructor);
			}
		}
		
		//判断参数个数相同的构造器个数
		if(tempList.size() == 0) {
			return ;
		}
		else if(tempList.size() == 1) {
			constructorToUse = tempList.get(0);
		}
		else {
			//通过参数类型选择构造器
			for(Constructor<?> c : tempList) {
				boolean b = true;
				Class<?>[] types = c.getParameterTypes();
				for(int i = 0;i<indexArg.size();i++) {
					ValueHolder valueHolder = indexArg.get(i);
					Class<?> parameter = types[i];
					if(!match(valueHolder,parameter)) {
						b = false;
						break;
					}
				}
				if(b) {
					constructorToUse = c ;
					break;
				}
			}
			
			if(constructorToUse != null) {
				//获取值
				Object[] values ;
				
				constructorToUse.newInstance();
			}
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected boolean match(ValueHolder vh,Class<?> parameter) {
		//判断类型是否相等
		String type = vh.getType();
		if(type != null) {
			if(!type.equals(parameter.getName())) {
				return false;
			}
		}
		return true;
		
	}
}
