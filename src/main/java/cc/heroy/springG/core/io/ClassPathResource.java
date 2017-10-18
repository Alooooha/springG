package cc.heroy.springG.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource extends AbstractResource{

	private String path ;
	private ClassLoader classLoader;
	
	public ClassPathResource(String path) {
		this(path,(ClassLoader)null);
	}
	
	public ClassPathResource(String path , ClassLoader classLoader) {
		this.path = path ;
		//classLoader 不等于 null 取 传入的classLoader ， 否则用此类的classLoader
		this.classLoader = (classLoader != null ?  classLoader : this.getClass().getClassLoader());
	}
	
	/**
	 * 核心方法，获取InputStream
	 */
	public InputStream getInputStream() throws IOException {
		InputStream is ;
		if(classLoader != null) {
			is = classLoader.getResourceAsStream(path);
		}else {
			is = ClassLoader.getSystemResourceAsStream(path);
		}
		if(is == null)
			throw new FileNotFoundException("文件未找到!  getInputStream()");
		return is;
	}

	@Override
	public String getFilename() {
		return path;
	}

	
	
}
