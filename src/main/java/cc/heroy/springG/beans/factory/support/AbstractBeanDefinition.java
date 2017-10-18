package cc.heroy.springG.beans.factory.support;

import cc.heroy.springG.beans.factory.config.BeanDefinition;
import cc.heroy.springG.util.Assert;

/**
 * 储存Bean的属性信息（很重要的类），更多的用于Bean的加载阶段
 */
public abstract class AbstractBeanDefinition implements BeanDefinition{
	
	//暂时只有这么几个，后面加吧
	
	private volatile Object beanClass;
	
	private String scope = "";
	
	private String[] dependsOn;
	
	private boolean lazyInit = false;
	
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
		return Assert.isEqual(this.scope, "singleton");
	}
	
	
}
