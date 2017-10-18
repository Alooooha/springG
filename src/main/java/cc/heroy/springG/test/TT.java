package cc.heroy.springG.test;

import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.heroy.springG.core.io.DefaultResourceLoader;
import cc.heroy.springG.core.io.Resource;
import cc.heroy.springG.core.io.ResourceLoader;


public class TT {
	
	/**
	 * 测试ResourceLoader实现 (OK)
	 */
	@Test
	public void testResourceLoader() {
		//
		ClassPathXmlApplicationContext c = new ClassPathXmlApplicationContext();
		
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		try {
			Resource resource = resourceLoader.getResource("classpath:spring.xml");
			InputStream is = resource.getInputStream();
//			readInputStream(is);			
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 用来处理InputStream
	 * @param is
	 */
	private void readInputStream(InputStream is) {
		byte[] b = new byte[1024];
		try {
			while(is.read(b) != -1) {
				System.out.println(new String(b));
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				is.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void jsoupTest() {
		
	}
	
}
