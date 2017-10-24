package cc.heroy.springG.beans.factory.support;

public class GenericBeanDefinition extends AbstractBeanDefinition{
	private String parentName;
	
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public String getParentName() {
		return parentName;
	}
}
