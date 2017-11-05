package cc.heroy.springG.beans.factory.support;

import java.util.Map;

import cc.heroy.springG.beans.BeanWrapper;
import cc.heroy.springG.beans.factory.config.BeanDefinition;

public abstract class AbstractBeanFactory {
	
	protected abstract Map<String,BeanDefinition> getBeanDefinitionMap();
	
	protected abstract Object createBean(String beanName , BeanDefinition bd);
	
	protected abstract BeanWrapper createBeanInstance(String beanName , BeanDefinition bd);
	/**
	 * 将几个getBean()重载方法整合在一起
	 */
	public Object getBean(String name) throws Exception{
		//这里牵扯出spring关于循环引用的处理，我不是很理解
		//Object sharedInstance = getSingleton(name);
		
		//获取BeanDefinition
		BeanDefinition bd = getBeanDefinitionMap().get(name);
		String[] dependsOn = bd.getDependsOn();
		
		//如果有依赖Bean，先实例依赖Bean
		if(dependsOn != null) {
			for(String dependsOnBean :dependsOn) {
				//注册依赖bean
				
				getBean(dependsOnBean);
			}
		}
		
		//创建Bean(这里应该分scope、单例和原型)
		Object bean = createBean(name,bd);
		
		return bean;
	}
}
