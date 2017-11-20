package cc.heroy.springG.core.io;

/**
 * ResourceLoader 的一个实现类，默认资源加载器，
 * 该类默认加载  类路径资源（classpath）
 * 它的构造器有两个：
 * 		1、 无参：对classLoader进行赋值，对于如何选择类加载器，请看getDefaultClassLoader()方法及其描述
 * 		2、 有参：自定义类加载器
 * 
 * @author BeiwEi
 */
public class DefaultResourceLoader implements ResourceLoader{
	
	private ClassLoader classLoader;

	public DefaultResourceLoader() {
		this.classLoader =  getDefaultClassLoader();
	}
	
	public DefaultResourceLoader(ClassLoader classLoader) {
		this.classLoader = classLoader ;
	}
	
	public Resource getResource(String location) throws Throwable {
		//判断是否为空
		if(location == null)
			throw new NullPointerException("location 为空!") ;
		//解析location（classpath:xxx/xxx/xx.xml）
		Boolean b = location.startsWith(CLASSPATH_URL_PREFIX);
		if(!b)
			throw new Throwable("location 未按规定使用，无法解析地址！");
		//获取路径
		String path = location.substring(CLASSPATH_URL_PREFIX.length());
		//加载classLoader
		Resource resource = new ClassPathResource(path,classLoader);
		return resource;
	}

	public ClassLoader getClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 该方法是org.springframework.util.ClassUtils 下的方法，简单起见，我将其拷贝过来
	 * Thread.currentThread().getContextClassLoader() ---> 线程上下文类加载器
	 * 			线程可以设置上下文类加载器，当全局范围都没有设置线程加载器时，它将返回Application ClassLoader
	 * ClassUtils.class.getClassLoader() ---> Application ClassLoader(?)
	 * ClassLoader.getSystemClassLoader() ---> 可能是bootstrap ClassLoader
	 * 
	 */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		}
		catch (Throwable ex) {
			// Cannot access thread context ClassLoader - falling back...
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = Resource.class.getClassLoader();
			if (cl == null) {
				// getClassLoader() returning null indicates the bootstrap ClassLoader
				try {
					cl = ClassLoader.getSystemClassLoader();
				}
				catch (Throwable ex) {
					// Cannot access system ClassLoader - oh well, maybe the caller can live with null...
				}
			}
		}
		return cl;
	}

}
