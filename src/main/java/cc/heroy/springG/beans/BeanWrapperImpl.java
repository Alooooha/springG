package cc.heroy.springG.beans;

import java.lang.reflect.Method;
import java.util.List;

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

	/**
	 * 设置Bean实例属性
	 * 
	 * 		 这里应该就是BeanWrapper作为bean包装类的一个主要作用之一，我自己觉得，spring
	 * 这点可能是考虑了JAVA面向对象的思想，对于beanWrapper对象，它具有修改自身属性的特性，
	 * 如下面的setPropertyValues（），在整个Spring容器初始化过程中容器具有生成BeanWrapper
	 * 的能力，而BeanWrapper又有对自身bean属性进行设置值的能力
	 * 
	 * 有个问题：
	 * 		java反射调用method注入参数时，如何对参数进行自动转化
	 * 这里我用的方法参数名与之匹配的形式，而spring用的converter来完成
	 * 
	 */
	public void setPropertyValues(MutablePropertyValues pvs) {
		if(pvs != null) {
			List<PropertyValue> propertyValueList =pvs.getPropertyValueList();
			for(PropertyValue pv : propertyValueList) {
				if(pv != null) {
					String name = pv.getName();
					Object val = pv.getValue();
					String methodToken = "set" + name.substring(0, 1).toUpperCase() + name.substring(1,name.length());
					//构造方法名
					Method[] methods = this.getWrappedClass().getDeclaredMethods();
					Method method = null;
					for(Method m : methods) {
						String methodName = m.getName();
						//还要看method方法参数多少
						if(methodName.equals(methodToken)&&m.getParameterCount()==1) {
							method = m;
							break;
						}
					}
					if(method == null) {
						return;
					}
					
					//反射：获取类的具体Method对象，然后调用invoke方法，传入该类的实例和参数
					try {
						//取得参数类型的Class
						Class<?> clazz = method.getParameterTypes()[0];
						String type = clazz.getName();
						//注入属性，这里暂时只有基本类型和List类型
					if(type.equals("java.lang.String")) {
						method.invoke(object,(String)val);
					}
					else if(type.equals("int")) {
						method.invoke(object, Integer.parseInt((String)val));
					}
					else if(type.equals("java.util.List")) {
						method.invoke(object, (List<?>)val);
					}
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		else {
			return ;
		}
	}
}

