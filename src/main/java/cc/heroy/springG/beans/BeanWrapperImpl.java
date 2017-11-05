package cc.heroy.springG.beans;

/**
 * Bean的包装类实现
 * @author BeiwEi
 */
public class BeanWrapperImpl implements BeanWrapper{

	private Object object;
	
	public BeanWrapperImpl(Object object) {
		this.object = object;
	}
	
	public Object getWrapperedInstance() {
		return this.object;
	}

	public Class<?> getWrappedClass() {
		//返回bean的Class类
		return (this.object != null ? this.object.getClass() : null);
	}

	public void setWrappedInstance(Object object) {
		this.object = object ;
	}
	
	
}
