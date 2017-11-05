package cc.heroy.springG.beans.factory.support;

import cc.heroy.springG.beans.MutablePropertyValues;
import cc.heroy.springG.beans.factory.config.BeanDefinition;
import cc.heroy.springG.beans.factory.config.ConstructorArgumentValues;
import cc.heroy.springG.util.Assert;

/**
 * 储存Bean的属性信息（很重要的类），更多的用于Bean的加载阶段
 * 
 * 1、beanClass : 存放class类或stirng类，在加载beanDefinition时，若类加载器为空
 * 			     无法反射生产class类，则这里的意思是beanClassName，所以在生产bean
 * 			     对象时要对beanClass进行判断   “instanceof”
 * 
 * 2、abstract、lazyInit默认为false
 * 
 * 
 * 
 */
public abstract class AbstractBeanDefinition implements BeanDefinition{
	
	//根据需要添加的属性
	
	private volatile Object beanClass;
	
	private boolean abstractFlag = false;
	
	private String scope = "";
	
	public static final String SCOPE_DEFAULT = "";
	
	private String[] dependsOn;
	
	private boolean lazyInit = false;
	
	private ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
	
	private MutablePropertyValues propertyValues = new MutablePropertyValues();
	
	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}
	
	public Class<?> getBeanClass() throws IllegalStateException{
		Object beanClassObject = this.beanClass;
		if(beanClassObject == null) {
			throw new IllegalStateException("beanName 为空!");
		}
		if(!(beanClassObject instanceof Class)) {
			throw new IllegalStateException("beanClass error");
		}
		return (Class<?>)beanClassObject;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public boolean isLazyInit() {
		return this.lazyInit;
	}
	
	public void setLazyInit(boolean lazyInit) {
		this.lazyInit = lazyInit;
	}

	public String[] getDependsOn() {
		return dependsOn;
	}

	public void setDependsOn(String... dependsOn) {
		this.dependsOn = dependsOn;
	}

	public boolean isSingleton() {
		if(scope.equals("singleton") || Assert.isEqual(scope, SCOPE_DEFAULT))
			return true;
		return false;
	}
	
	public void setBeanClassName(String beanClassName) {
		this.beanClass = beanClassName;
	}

	public boolean isAbstract() {
		return abstractFlag;
	}

	public void setAbstract(boolean abstractFlag) {
		this.abstractFlag = abstractFlag;
	}
	
	public ConstructorArgumentValues getConstructorArgumentValues() {
		return this.constructorArgumentValues;
	}
	
	public void setPropertyValues(MutablePropertyValues propertyValues) {
		this.propertyValues = (propertyValues != null ? propertyValues : new MutablePropertyValues());
	}
	
	public MutablePropertyValues getPropertyValues() {
		return this.propertyValues;
	}

}
