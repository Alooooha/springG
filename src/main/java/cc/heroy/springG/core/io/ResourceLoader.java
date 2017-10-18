package cc.heroy.springG.core.io;

import cc.heroy.springG.util.ResourceUtils.ResourceUtils;

/**
 * 作用：ResourceLoader用于返回Resource对象，属于Resource的工厂类
 * 		 其子类可以返回不同的Resource对象
 * @author BeiwEi
 */
public interface ResourceLoader {

	//默认的加载方式：
	String CLASSPATH_URL_PREFIX = ResourceUtils.CLASSPATH_URL_PREFIX;
	
	Resource getResource(String location) throws Throwable;
	
	ClassLoader getClassLoader();
	
}
