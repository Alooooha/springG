package cc.heroy.springG.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * String 的工具类
 * @author BeiwEi
 */
public class StringUtils {
	public static String[] toStringArray(Collection<String> collection) {
		String[] array = new String[collection.size()];
		Iterator<String> it = collection.iterator();
		int index = 0;
		while(it.hasNext()) {
			array[index] = it.next();
			index++;
		}
		return array;
	}
}
