package cc.heroy.springG.beans.factory.support;

import java.util.Map;

import cc.heroy.springG.beans.BeanWrapper;
import cc.heroy.springG.beans.factory.config.BeanDefinition;

public abstract class AbstractBeanFactory{
	
	protected abstract Map<String,BeanDefinition> getBeanDefinitionMap();
	
	protected abstract Object createBean(String beanName , BeanDefinition bd);
	
	protected abstract BeanWrapper createBeanInstance(String beanName , BeanDefinition bd);
}
