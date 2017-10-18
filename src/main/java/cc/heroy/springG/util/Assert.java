package cc.heroy.springG.util;

/**
 * 工具包，对参数进行判断
 */
public abstract class Assert {

	public static void notNull(Object object) {
		if(object == null)
			throw new IllegalArgumentException();
	}
	
	public static void notNull(Object object ,String message) {
		if(object == null)
			throw new IllegalArgumentException(message);
	}
	
	public static boolean isEqual(String str1,String str2) {
		try {
			notNull(str1);
			notNull(str2);
			return str1.equals(str2);
		}catch(IllegalArgumentException e) {
			throw new IllegalArgumentException();
		}
	}
}
