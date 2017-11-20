package cc.heroy.springG.context.support;

import java.io.IOException;

import cc.heroy.springG.beans.factory.config.ConfigurableListableBeanFactory;
import cc.heroy.springG.beans.factory.support.DefaultListableBeanFactory;

/**
 * 用于实现 "refreshBeanFactory" "getBeanFactory" 方法
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

	//用于存储配置文件路径
	private String[] configLocations;
	
	private DefaultListableBeanFactory beanFactory;
	
	private final Object beanFactoryMonitor = new Object();
	
	@Override
	protected void refreshBeanFactory() {
		// 判断BeanFactory是否存在，若存在就调用destroyBeans() 和 closeBeanFactory() 方法，暂时未实现
		
		try {
			//创建BeanFactory实例
			DefaultListableBeanFactory beanFactory = createBeanFactory();
			//加载BeanDefinitions
			loadBeanDefinitions(beanFactory);
			synchronized (this.beanFactoryMonitor) {
				this.beanFactory = beanFactory;
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public ConfigurableListableBeanFactory getBeanFactory() {
		synchronized (this.beanFactoryMonitor) {
			//判断beanFactory是否为空
			if(this.beanFactory == null)
				throw new IllegalStateException("获取beanFactory失败");
			return this.beanFactory;
		}
	}

	protected DefaultListableBeanFactory createBeanFactory() {
		//自己实现的DeaultListableBeanFactory
		return new DefaultListableBeanFactory();
	}
	
	protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory)
			throws IOException;
	
	protected String[] getConfigLocations() {
		return (this.configLocations != null ? this.configLocations : getDefaultConfigLocations());
	}
	
	protected String[] getDefaultConfigLocations() {
		return null;
	}
	
	protected void setDefualtConfigLocation(String[] configLocations) {
		this.configLocations = configLocations;
	}
	
}
