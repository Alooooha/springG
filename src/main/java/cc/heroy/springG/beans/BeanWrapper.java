package cc.heroy.springG.beans;

/**
 * Bean的包装类
 * @author BeiwEi
 */
public interface BeanWrapper {

	Object getWrapperedInstance();
	
	Class<?> getWrappedClass();
	
	void setPropertyValues(MutablePropertyValues pvs);
	
}
