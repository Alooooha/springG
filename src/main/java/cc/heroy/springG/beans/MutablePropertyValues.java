package cc.heroy.springG.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * BeanDefinition 中存放property属性的类
 * @author BeiwEi
 */
public class MutablePropertyValues {
	private final List<PropertyValue> propertyValueList;
	
	public MutablePropertyValues() {
		this.propertyValueList = new ArrayList<PropertyValue>();
	}
	
	public List<PropertyValue> getPropertyValueList(){
		return this.propertyValueList;
	}
	
	public int size() {
		return this.propertyValueList.size();
	}
	
	public MutablePropertyValues addPropertyValue(PropertyValue pv) {
		//遍历propertyValueList，如果出现name相同的，替换
		for(int i = 0; i < this.propertyValueList.size(); i++) {
			PropertyValue currentPv = this.propertyValueList.get(i);
			if(currentPv.getName().equals(pv.getName())){
				//替换
				setPropertyValueAt(pv, i);
				return this;
			}
		}
		this.propertyValueList.add(pv);
		return this;
	}
	
	public void setPropertyValueAt(PropertyValue pv, int index) {
		propertyValueList.add(index, pv);
	}
	
	public boolean contains(String name) {
		for(int i = 0; i < propertyValueList.size(); i++) {
			if(propertyValueList.get(i).getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
}
