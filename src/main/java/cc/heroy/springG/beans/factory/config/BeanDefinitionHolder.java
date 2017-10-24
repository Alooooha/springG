package cc.heroy.springG.beans.factory.config;

import cc.heroy.springG.util.Assert;

/**
 * BeanDefinition的包装类，里面储存了BeanDefinition对象、beanName、aliase属性
 */
public class BeanDefinitionHolder {
	
	private final BeanDefinition beanDefinition;
	
	private final String beanName;
	
	private final String[] aliases;
	
	public BeanDefinitionHolder(BeanDefinition beanDefinition , String beanName) {
		this(beanDefinition,beanName,null);
	}
	
	public BeanDefinitionHolder(BeanDefinition beanDefinition , String beanName ,String[] aliases) {
		//判断是否为空
		Assert.notNull(beanDefinition);
		Assert.notNull(beanName);
		
		this.beanDefinition = beanDefinition;
		this.beanName = beanName;
		this.aliases = aliases;
	}

	public BeanDefinition getBeanDefinition() {
		return beanDefinition;
	}

	public String getBeanName() {
		return beanName;
	}

	public String[] getAliases() {
		return aliases;
	}
	
}
