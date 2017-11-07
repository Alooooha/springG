package cc.heroy.springG.context.support;


import cc.heroy.springG.beans.factory.config.ConfigurableListableBeanFactory;

public abstract class AbstractApplicationContext {

	/**beanFactory 在源码中是 AbstractApplicationContext 继承的DefaultResourceLoader抽象类中属性，简便起见写在这 */
//	private DefaultListableBeanFactory beanFactory;
	
	/** 同步监听器 ， 用于对refresh() 和 destory() 同步*/
	private final Object startupShutdownMonitor = new Object();
	
	public void refresh() {
		synchronized (this.startupShutdownMonitor) {
			//源码中有多个方法，我只写出其中几个核心方法
			
			//刷新spring上下文的Bean工厂
			//这里完成了： XML 读取为Resource ， Resource 解析出 BeanDefinition ， BeanDefinition 注册在 BeanFactory
			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
			
			//完成所有对非懒加载的Bean初始化（lazy-init = false）
			finishBeanFactoryInitialization(beanFactory);
			
System.out.println("THE WORLD !");
		}
	}
	
	/** 
	 * 我在找方法里refreshBeanFactory() 和 getBeanFactory()的时候，在抽象类AbstractRefreshableApplicationContext中找到具体实现，
	 * 而本方法只是定义了获取BeanFactory的步骤，具体怎么实现交给了子类，如果让我来写，可能这就是个抽象方法，而没有具体流程，因此不
	 * 同子类实现的步骤也会各不相同，这是在开发中不规范的。*/
	protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
		//加载BeanFactory ，核心方法
		refreshBeanFactory();
		
		ConfigurableListableBeanFactory beanFactory = getBeanFactory();
		return beanFactory;
	}
	
	
	protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
		//这里有一些对beanFactory的设置，关系不大
		//核心方法
		beanFactory.preInstantiateSingletons();
	}
	
	
	
	protected abstract void refreshBeanFactory();
	
	public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
	
	public abstract Object getBean(String beanName);
	
}
