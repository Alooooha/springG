package cc.heroy.springG.beans.factory.config;

public interface ConfigurableListableBeanFactory {

	//初始化所有单例bean
	public void preInstantiateSingletons();
}
