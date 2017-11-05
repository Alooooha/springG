package cc.heroy.springG.beans.factory.config;

import cc.heroy.springG.beans.MutablePropertyValues;

public interface BeanDefinition {

//	String getBeanClassName();
	
	String getScope();
	
	void setScope(String scope);
	
	boolean isLazyInit();
	
	void setLazyInit(boolean lazyInit);
	
	String[] getDependsOn();
	
	void setDependsOn(String... dependsOn);
	
	boolean isSingleton();
	
	boolean isAbstract();
	
	public Class<?> getBeanClass();
	
	ConstructorArgumentValues getConstructorArgumentValues();
	
	MutablePropertyValues getPropertyValues();
	
	void setPropertyValues(MutablePropertyValues propertyValues);
}
