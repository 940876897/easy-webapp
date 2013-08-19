/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.manager.common.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easyUI.domain.common.Permission;
import net.easyUI.domain.query.PermissionQuery;
import net.easyUI.dto.common.query.DwzPage;
import net.easyUI.manager.common.PermissionManager;
import net.easyUI.mapper.common.PermissionMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("permissionManager")
public class PermissionManagerImpl implements PermissionManager {
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	protected static Log logger= LogFactory.getLog(PermissionManagerImpl.class);
	
	public Permission queryOne(Long id) {
		return permissionMapper.getByKey(id);
	}
	public Permission queryOneFullFK(Long id) {
		return permissionMapper.getByKeyFullFK(id);
	}

	/**
	 * 根据唯一字段{column.columnName}查询单条记录
	 */
	public Permission getByUkPermissionKey(String v) {
		return permissionMapper.getByUkPermissionKey(v);
	}
	
	
	/**
	 * 根据唯一字段{column.columnName}查询单条记录
	 */
	public Permission getByUkPermissionKeyFullFK(String v) {
		return permissionMapper.getByUkPermissionKeyFullFK(v);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DwzPage<Permission> pageQuery(PermissionQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		long count = permissionMapper.countQueryMap(map);
		DwzPage<Permission> page = new DwzPage<Permission>(query, count);
		map.put("filterQuery", query);
		// (offset,limit) 从偏移量offset(从0开始)开始取数据，最多取limit条
		RowBounds rowBounds = new RowBounds(page.getFirstResult(),
				page.getPageSize());
		List<Permission> list = permissionMapper.listQueryMap(map, rowBounds);
		page.setRows(list);
		return page;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DwzPage<Permission> pageQueryFullFK(PermissionQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		long count = permissionMapper.countQueryMap(map);
		DwzPage<Permission> page = new DwzPage<Permission>(query, count);
		map.put("filterQuery", query);
		// (offset,limit) 从偏移量offset(从0开始)开始取数据，最多取limit条
		RowBounds rowBounds = new RowBounds(page.getFirstResult(),
				page.getPageSize());
		List<Permission> list = permissionMapper.listQueryMapFullFK(map, rowBounds);
		page.setRows(list);
		return page;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Permission> listQuery(PermissionQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		List<Permission> list = permissionMapper.listQueryMap(map);
		return list;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Permission> listQueryFullFK(PermissionQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		List<Permission> list = permissionMapper.listQueryMapFullFK(map);
		return list;
	}

	public Permission save(Permission obj) {
		permissionMapper.insert(obj);
		return obj;
	}
	
	public int saveBatch(List<Permission> listObjs) {
		int count = 0;
		SqlSession sqlSessionBatch = sqlSessionFactory.openSession(
				ExecutorType.BATCH, false);
		try {
			sqlSessionBatch.getConnection().setAutoCommit(false);
			PermissionMapper mapper = sqlSessionBatch
					.getMapper(PermissionMapper.class);
			for (Permission obj : listObjs) {
				mapper.insert(obj);
				count ++;
			}
			sqlSessionBatch.commit();
		} catch (Exception e) {
		} finally {
			sqlSessionBatch.close();
		}
		return count;
	}

	public Integer update(Permission obj) {
		return permissionMapper.update(obj);
	}

	public int updateBatch(List<Permission> listObjs) {
		int count = 0;
		SqlSession sqlSessionBatch = sqlSessionFactory.openSession(
				ExecutorType.BATCH, false);
		try {
			sqlSessionBatch.getConnection().setAutoCommit(false);
			PermissionMapper mapper = sqlSessionBatch
					.getMapper(PermissionMapper.class);
			for (Permission obj : listObjs) {
				mapper.update(obj);
				count ++;
			}
			sqlSessionBatch.commit();
		} catch (Exception e) {
		} finally {
			sqlSessionBatch.close();
		}
		return count;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Integer updateByQuery(Permission entity, PermissionQuery query) {
		Map map = new HashMap();
		map.put("entity", entity);
		map.put("filterQuery", query);
		return permissionMapper.updateByQueryMap(map);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer remove(PermissionQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		return permissionMapper.deleteByQueryMap(map);
	}

	public Integer remove(Permission obj) {
		return permissionMapper.delete(obj);
	}

	public int removeBatch(List<Permission> listObjs) {
		int count = 0;
		SqlSession sqlSessionBatch = sqlSessionFactory.openSession(
				ExecutorType.BATCH, false);
		try {
			sqlSessionBatch.getConnection().setAutoCommit(false);
			PermissionMapper mapper = sqlSessionBatch
					.getMapper(PermissionMapper.class);
			for (Permission obj : listObjs) {
				mapper.delete(obj);
				count ++;
			}
			sqlSessionBatch.commit();
		} catch (Exception e) {
		} finally {
			sqlSessionBatch.close();
		}
		return count;
	}

}
