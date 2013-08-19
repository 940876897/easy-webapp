/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.mapper.common;

import java.util.List;
import java.util.Map;

import net.easyUI.domain.common.WebConfig;

import org.apache.ibatis.session.RowBounds;

public interface WebConfigMapper {

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param key
	 * @return
	 */
	public WebConfig getByKey(Long id);
	public WebConfig getByKeyFullFK(Long id);
	

	/**
	 * 根据唯一字段{column.columnName}查询单条记录
	 */
	public WebConfig getByUkCfgName(java.lang.String v) ;
	
	
	
	/**
	 * 根据唯一字段{column.columnName}查询单条记录
	 */
	public WebConfig getByUkCfgNameFullFK(java.lang.String v) ;
	
	
	

	/**
	 * 添加保存单条记录
	 * 
	 * @param Object
	 * @return 返回影响行数
	 */
	public int insert(WebConfig obj);

	/**
	 * 根据主键修改单条记录
	 * 
	 * @param Object
	 * @return
	 */
	public int update(WebConfig obj);

	/**
	 * 根据条件更新非空字段,建议使用子类自己的方法(将参数改成对应的Query对象)
	 * 
	 * @author busfly 2010-09-25
	 * @param queryMap
	 *            由两个内容组成:1,对象(不更新的请为空)map.put("entity", entity);
	 *            2,条件(各对象的query)map.put("filterQuery", query);
	 */
	@SuppressWarnings("rawtypes")
	public int updateByQueryMap(Map queryMap);

	/**
	 * 查询记录总数量,忽略分页条件
	 * 
	 * @param queryMap
	 *            条件(各对象的query)map.put("filterQuery", query);
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public long countQueryMap(Map queryMap);

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
	public List<WebConfig> listQueryMap(Map queryMap, RowBounds rowBounds);
	@SuppressWarnings("rawtypes")
	public List<WebConfig> listQueryMapFullFK(Map queryMap, RowBounds rowBounds);

	/**
	 * 根据条件查询全部记录
	 * 
	 * @author busfly 2010-09-25
	 * @param queryMap
	 *            条件(各对象的query)map.put("filterQuery", query);
	 */
	@SuppressWarnings("rawtypes")
	public List<WebConfig> listQueryMap(Map queryMap);
	@SuppressWarnings("rawtypes")
	public List<WebConfig> listQueryMapFullFK(Map queryMap);

	/**
	 * 删除单条记录
	 * 
	 * @param Object
	 * @return
	 */
	public int deleteBykey(Long id);

	/**
	 * 根据唯一字段{column.columnName}查询单条记录
	 */
	public int deleteByUkCfgName(java.lang.String v) ;
	
	
	/**
	 * 删除单条记录
	 * 
	 * @param Object
	 * @return
	 */
	public int delete(WebConfig obj);

	/**
	 * 删除符合条件的记录
	 * 
	 * @param queryMap
	 *            条件(各对象的query)map.put("filterQuery", query);
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int deleteByQueryMap(Map queryMap);

}
