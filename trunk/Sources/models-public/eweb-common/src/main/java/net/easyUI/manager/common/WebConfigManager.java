/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.manager.common;

import java.util.List;

import net.easyUI.domain.common.WebConfig;
import net.easyUI.domain.query.WebConfigQuery;
import net.easyUI.dto.common.query.DwzPage;

public interface WebConfigManager {
	/**
	 * 根据主键查询一条记录
	 */
	public WebConfig queryOne(Long id);
	/** 根据主键查询一条记录,查询结果中,包括外键对象 */
	public WebConfig queryOneFullFK(Long id);
	
	/**
	 * 根据唯一字段{column.columnName}查询单条记录
	 */
	public WebConfig getByUkCfgName(java.lang.String v) ;
	
	
	/**
	 * 根据唯一字段{column.columnName}查询单条记录
	 */
	public WebConfig getByUkCfgNameFullFK(java.lang.String v) ;
	
	
	
	/**
	 * 分页查询
	 */
	public DwzPage<WebConfig> pageQuery(WebConfigQuery query);
	/** 分页查询,查询结果中,包括外键对象 */
	public DwzPage<WebConfig> pageQueryFullFK(WebConfigQuery query);
	
	/**
	 * 查询全部
	 */
	public List<WebConfig> listQuery(WebConfigQuery query);
	/** 查询全部,查询结果中,包括外键对象 */
	public List<WebConfig> listQueryFullFK(WebConfigQuery query);

	/**
	 * 保存添加
	 */
	public WebConfig save(WebConfig obj);

	/**
	 * 批量添加
	 * 
	 * @param listObjs
	 *            要添加的对象List
	 * @return
	 */
	public int saveBatch(List<WebConfig> listObjs);

	/**
	 * 根据主键来修改单条记录
	 * @return
	 */
	public Integer update(WebConfig obj);

	/**
	 * 批量修改(根据主键来修改)
	 * 
	 * @param listObjs
	 *            要修改的对象List
	 * @return
	 */
	public int updateBatch(List<WebConfig> listObjs);

	/**
	 * 根据条件更新非空字段
	 * 
	 * @author busfly 2010-09-25
	 * @param entity
	 *            ,对象(不更新的请为空)
	 * @param query
	 *            ,条件(各对象的query)
	 */
	public Integer updateByQuery(WebConfig entity, WebConfigQuery query);

	/**
	 * 根据条件删除
	 * 
	 * @author busfly 2010-09-25
	 * @param query
	 *            ,条件(各对象的query)
	 */
	public Integer remove(WebConfigQuery query);

	/**
	 * 删除单个对象(根据主键来删除)
	 */
	public Integer remove(WebConfig obj);

	/**
	 * 批量删除(根据主键来删除)
	 * 
	 * @param listObjs
	 *            要删除的对象List
	 * @return
	 */
	public int removeBatch(List<WebConfig> listObjs);

}
