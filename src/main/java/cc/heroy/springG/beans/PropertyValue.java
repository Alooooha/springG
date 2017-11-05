package cc.heroy.springG.beans;

/**
 * 存储Property标签属性的类
 * 包括 name、value
 * @author BeiwEi
 */
public class PropertyValue {

	private final String name;
	private final Object value;
	
	public PropertyValue(String name,Object value) {
		this.name = name ;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}
	
}
