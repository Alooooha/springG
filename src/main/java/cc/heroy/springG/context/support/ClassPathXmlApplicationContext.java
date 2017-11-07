package cc.heroy.springG.context.support;

import cc.heroy.springG.util.Assert;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{

	//源码有 ApplicationContext parent 参数，目的是调用父类的构造方法，此项目以理解spring源码为主，细枝末节暂时不考虑
	public ClassPathXmlApplicationContext(String configLocation) {
		this(new String[] {configLocation} , true );
	}
	
	//spring 加载方法，其中refresh是容器初始化的入口
	public ClassPathXmlApplicationContext(String[] configLocations,boolean refresh ) {
		this.setDefualtConfigLocation(configLocations);
		if(refresh)
			refresh();
	}

	@Override
	public Object getBean(String name) {
		Assert.notNull(name);
		return this.getBeanFactory().getBean(name);
	}
	
	
}
