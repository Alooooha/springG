package cc.heroy.springG.context.support;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.core.io.Resource;

import cc.heroy.springG.beans.factory.support.DefaultListableBeanFactory;
import cc.heroy.springG.beans.factory.xml.XmlBeanDefinitionReader;

/*  类结构应该是 
 *  AbstractApplicationContext --> AbstractRefreshableApplicationContext --> AbstractRefreshableConfigApplicationContext --> 本类
 *  没写 ： AbstractRefreshableConfigApplicationContext
 * 
 **/
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{

	/*
	 * 源码中此方法是加载配置文件到beanFactory的入口，其中结构层次复杂，我决定根据源码流程自己实现Bean定义加载。
	 * 自己实现Resource接口和部分相关类，在cc.heroy.springG.core.io 包
	 * 
	 * 
	 */
	@Override
	protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws IOException {
		//创建BeanDefinitionReader,并将BeanFactory传入其中
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
		loadBeanDefinitions(beanDefinitionReader);
	}
	
	protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException, IOException {
		String[] configLocations = getConfigLocations();
		if (configLocations != null) {
			try {
				reader.loadBeanDefinitions(configLocations);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
