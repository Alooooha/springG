package cc.heroy.springG.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 *	功能 ：Resource接口的抽象实现类，实现部分子类共有的方法
 * @author BeiwEi
 */
public abstract class AbstractResource implements Resource {


	/**
	 *	默认返回true 
	 */
	public boolean isReadable() {
		return true;
	}

	/**
	 *	默认返回false 
	 */
	public boolean isOpen() {
		return false;
	}


	/**
	 *	通过getInputStream方法获取字节流，读取长度，关闭字节流 
	 */
	public long contentLength() throws IOException {
		InputStream is = getInputStream();
		if(is == null)
			throw new IOException("获取InputStream失败");
		try {
			int size = 0;
			byte[] b = new byte[255];
			int read ;
			while(( read = is.read(b)) != -1) {
				size += read ;
			}
			return size;
		}catch(IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				is.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public String getFilename() {
		return null;
	}

}
