/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Since 2010 - 2012
 */

package net.easyUI.mapper.common;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

public interface ReportMapper {

	/**
	 * 查询记录总数量,忽略分页条件
	 * 
	 * @param queryMap
	 *            条件(各对象的query)map.put("filterQuery", query);
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public long countQueryMapYyrbb(Map queryMap);

	/**
	 * 根据条件分页查询
	 * 
	 * @author busfly 2010-09-25
	 * @param queryMap
	 *            条件(各对象的query)map.put("filterQuery", query);
	 * @param rowBounds
	 *            (offset,limit) 从偏移量offset开始取数据，最多取limit条
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> listQueryMapYyrbb(Map queryMap, RowBounds rowBounds);

	/**
	 * 根据条件查询全部记录
	 * 
	 * @author busfly 2010-09-25
	 * @param queryMap
	 *            条件(各对象的query)map.put("filterQuery", query);
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> listQueryMapYyrbb(Map queryMap);

	/**
	 * @param map
	 * @return
	 */
	public Map<String, Object> queryTotalMapYyrbb(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> listQueryCzmxbb(Map<String, Object> map);
	
	public List<Map<String, Object>> listQueryCzmxhz(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> listQueryLbtjbb(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> yyjbbbItems_1(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	public Map<String, Object> yyjbbbItems_zk(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	public Map<String, Object> yyjbbbItems_zs(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	public Map<String, Object> yyjbbbItems_rs(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	public Map<String, Object> yyjbbbItems_wj(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> yyjbbbItems_shishou(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	public Map<String, Object> yyjbbbItems_yuanzheng(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> yyjbbbItems_congzhi(Map<String, Object> map);

	public List<Map<String, Object>> listQueryCzxfbb(Map<String, Object> map);

	public List<Map<String, Object>> listQueryCzxfhz(Map<String, Object> map);

	public List<Map<String, Object>> listQueryCzjfbb(Map<String, Object> map);

	public List<Map<String, Object>> listQueryCzjfhz(Map<String, Object> map);

	public List<Map<String, Object>> listQueryXfjfbb(Map<String, Object> map);

	public List<Map<String, Object>> listQueryXfjfhz(Map<String, Object> map);

}
