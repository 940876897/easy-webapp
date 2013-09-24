package net.easyUI.common.util.cache;


/**
 * cache最基本接口实现
 * 
 */
public interface CompactCache<K,V> {
	/**
	 * 把一对key - value 放入cache
	 * 
	 * @param key
	 *            不能为null
	 * @param value
	 *            如果为null，则相当于 remove(key)
	 */
	public void put(K key, V value);

	/**
	 * 从cache中得到key对应的值
	 * 
	 * @param key
	 *            不能为null
	 * @return 如果cache不存在，或者已经过期，则返回null
	 */
	public V get(K key);

	/**
	 * 清除cache中的所有数据
	 * 
	 */
	public void clean();
}
