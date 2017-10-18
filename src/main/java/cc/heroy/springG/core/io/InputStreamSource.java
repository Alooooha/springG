package cc.heroy.springG.core.io;

import java.io.IOException;
import java.io.InputStream;

/*
 * 提供getInputStream方法 
 **/
public interface InputStreamSource {

	InputStream getInputStream() throws IOException;
}
