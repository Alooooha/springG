package cc.heroy.springG.beans.factory.config;

public interface BeanDefinition {

//	String getBeanClassName();
	
	String getScope();
	
	void setScope(String scope);
	
	boolean isLazyInit();
	
	void setLazyInit(boolean lazyInit);
	
	String[] getDependsOn();
	
	void setDependsOn(String... dependsOn);
	
	boolean isSingleton();
}
