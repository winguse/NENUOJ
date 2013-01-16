package cn.edu.nenu.acm.oj.util;

import java.io.Serializable;
import java.util.TreeMap;


/**
 * @author winguse
 * Remark Object is a bean support dynamic getter and setter for specific fields.
 * Focusing on provided a solution of adding none index column of a table.
 */
public class Remark implements Serializable {

	private static final long serialVersionUID = 7529229355590025674L;
	
	private TreeMap<String, Object> data = new TreeMap<String, Object>();

	/**
	 * return the object mapped of specify key
	 * @param key
	 * @return the object of specify key, a "Undefined" String is returned if not found;
	 */
	public Object get(String key) {
		Object ret = data.get(key);
		if (ret == null)
			ret = new String("Undefined");
		return ret;
	}

	/**
	 * setup a key-value mapping
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {
		data.put(key, value);
	}
	
	/**
	 * remove a key mapping
	 * @param key
	 */
	public void remove(String key){
		data.remove(key);
	}
	
	/**
	 * Retrieve or new instance of Remark Object
	 * @param source
	 * @return
	 */
	public static Remark getInstance(Object source){
		if(source==null||!(source instanceof Remark)){
			source=new Remark();
		}
		return (Remark)source;
	}
}
