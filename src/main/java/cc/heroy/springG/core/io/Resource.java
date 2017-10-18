package cc.heroy.springG.core.io;

import java.io.IOException;

/*
 * 	提供获取资源信息的方法（部分方法）
 *	 
 **/

public interface Resource extends InputStreamSource{
	
	boolean isReadable();

	boolean isOpen();

	long contentLength() throws IOException;
	
	String getFilename();
	
}
