/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.manager.common;

import java.util.List;

import net.easyUI.domain.common.Permission;
import net.easyUI.domain.query.PermissionQuery;
import net.easyUI.dto.common.query.DwzPage;

public interface PermissionManager {
	/**
	 * 根据主键查询一条记录
	 */
	public Permission queryOne(Long id);
	/** 根据主键查询一条记录,查询结果中,包括外键对象 */
	public Permission queryOneFullFK(Long id);
	
	/**
	 * 根据唯一字段{column.columnName}查询单条记录
	 */
	public Permission getByUkPermissionKey(String v) ;
	
	
	/**
	 * 根据唯一字段{column.columnName}查询单条记录
	 */
	public Permission getByUkPermissionKeyFullFK(String v) ;
	
	
	
	/**
	 * 分页查询
	 */
	public DwzPage<Permission> pageQuery(PermissionQuery query);
	/** 分页查询,查询结果中,包括外键对象 */
	public DwzPage<Permission> pageQueryFullFK(PermissionQuery query);
	
	/**
	 * 查询全部
	 */
	public List<Permission> listQuery(PermissionQuery query);
	/** 查询全部,查询结果中,包括外键对象 */
	public List<Permission> listQueryFullFK(PermissionQuery query);

	/**
	 * 保存添加
	 */
	public Permission save(Permission obj);

	/**
	 * 批量添加
	 * 
	 * @param listObjs
	 *            要添加的对象List
	 * @return
	 */
	public int saveBatch(List<Permission> listObjs);

	/**
	 * 根据主键来修改单条记录
	 * @return
	 */
	public Integer update(Permission obj);

	/**
	 * 批量修改(根据主键来修改)
	 * 
	 * @param listObjs
	 *            要修改的对象List
	 * @return
	 */
	public int updateBatch(List<Permission> listObjs);

	/**
	 * 根据条件更新非空字段
	 * 
	 * @author busfly 2010-09-25
	 * @param entity
	 *            ,对象(不更新的请为空)
	 * @param query
	 *            ,条件(各对象的query)
	 */
	public Integer updateByQuery(Permission entity, PermissionQuery query);

	/**
	 * 根据条件删除
	 * 
	 * @author busfly 2010-09-25
	 * @param query
	 *            ,条件(各对象的query)
	 */
	public Integer remove(PermissionQuery query);

	/**
	 * 删除单个对象(根据主键来删除)
	 */
	public Integer remove(Permission obj);

	/**
	 * 批量删除(根据主键来删除)
	 * 
	 * @param listObjs
	 *            要删除的对象List
	 * @return
	 */
	public int removeBatch(List<Permission> listObjs);

}
