package cc.heroy.springG.beans.factory.config;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.heroy.springG.util.Assert;

/**
 * BeanDefinition中存放构造方法参数的类
 * @author BeiwEi
 */
public class ConstructorArgumentValues {

	private final Map<Integer , ValueHolder>  indexedArgumentValues = new LinkedHashMap<Integer,ValueHolder>();
	
	private final List<ValueHolder> genericArgumentValues = new LinkedList<ValueHolder>();
	
	/**
	 * 存值
	 * @param index
	 * @param newValue
	 */
	public void addIndexedArgumentValue(int index , ValueHolder newValue) {
		indexedArgumentValues.put(index, newValue);
	}
	
	/**
	 * 取值，这里用到了Collections的一个方法，即返回容器无法修改
	 */
	public Map<Integer,ValueHolder> getIndexedArgumentValues(){
		return Collections.unmodifiableMap(this.indexedArgumentValues);
	}
	
	/**
	 * 添加值
	 */
	public void addGenericArgumentValue(ValueHolder newValue) {
		if(!this.genericArgumentValues.contains(newValue)) {
			this.genericArgumentValues.add(newValue);
		}
	}
	
	/**
	 * 取值
	 */
	public List<ValueHolder> getGenericArgumentValue(){
		return Collections.unmodifiableList(this.genericArgumentValues);
	}
	
	//在生成Bean阶段会用到下列的方法
	
	
	
	/**
	 * 静态内部类，用于存储配置文件中 “constructor-arg” 标签下value、type、name等信息
	 * @author BeiwEi
	 */
	public static class ValueHolder {
		private Object value;
		
		private String type;
		
		private String name;
		
		public ValueHolder(Object value) {
			//value不能为空
			Assert.notNull(value);
			this.value = value;
		}
		
		public ValueHolder(Object value,String type) {
			this.value = value;
			this.type = type;
		}
		
		public ValueHolder(Object value,String type ,String name) {
			this.value = value;
			this.type = type;
			this.name = name;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	
	
}
