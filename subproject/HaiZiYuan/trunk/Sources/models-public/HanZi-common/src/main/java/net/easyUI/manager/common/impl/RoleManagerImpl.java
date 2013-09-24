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

import net.easyUI.domain.common.Role;
import net.easyUI.domain.query.RoleQuery;
import net.easyUI.dto.common.query.DwzPage;
import net.easyUI.manager.common.RoleManager;
import net.easyUI.mapper.common.RoleMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleManager")
public class RoleManagerImpl implements RoleManager {
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	protected static Log logger = LogFactory.getLog(RoleManagerImpl.class);

	public Role queryOne(Long id) {
		return roleMapper.getByKey(id);
	}

	public Role queryOneFullFK(Long id) {
		return roleMapper.getByKeyFullFK(id);
	}

	/**
	 * 根据唯一字段{column.columnName}查询单条记录
	 */

	public Role getByUkRoleKey(String v) {
		return roleMapper.getByUkRoleKey(v);
	}

	/**
	 * 根据唯一字段{column.columnName}查询单条记录
	 */

	public Role getByUkRoleKeyFullFK(String v) {
		return roleMapper.getByUkRoleKeyFullFK(v);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DwzPage<Role> pageQuery(RoleQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		long count = roleMapper.countQueryMap(map);
		DwzPage<Role> page = new DwzPage<Role>(query, count);
		map.put("filterQuery", query);
		// (offset,limit) 从偏移量offset(从0开始)开始取数据，最多取limit条
		RowBounds rowBounds = new RowBounds(page.getFirstResult(),
				page.getPageSize());
		List<Role> list = roleMapper.listQueryMap(map, rowBounds);
		page.setRows(list);
		return page;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DwzPage<Role> pageQueryFullFK(RoleQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		long count = roleMapper.countQueryMap(map);
		DwzPage<Role> page = new DwzPage<Role>(query, count);
		map.put("filterQuery", query);
		// (offset,limit) 从偏移量offset(从0开始)开始取数据，最多取limit条
		RowBounds rowBounds = new RowBounds(page.getFirstResult(),
				page.getPageSize());
		List<Role> list = roleMapper.listQueryMapFullFK(map, rowBounds);
		page.setRows(list);
		return page;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Role> listQuery(RoleQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		List<Role> list = roleMapper.listQueryMap(map);
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Role> listQueryFullFK(RoleQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		List<Role> list = roleMapper.listQueryMapFullFK(map);
		return list;
	}

	public Role save(Role obj) {
		roleMapper.insert(obj);
		return obj;
	}

	public int saveBatch(List<Role> listObjs) {
		int count = 0;
		SqlSession sqlSessionBatch = sqlSessionFactory.openSession(
				ExecutorType.BATCH, false);
		try {
			sqlSessionBatch.getConnection().setAutoCommit(false);
			RoleMapper mapper = sqlSessionBatch.getMapper(RoleMapper.class);
			for (Role obj : listObjs) {
				count++;
				mapper.insert(obj);
			}
			sqlSessionBatch.commit();
		} catch (Exception e) {
		} finally {
			sqlSessionBatch.close();
		}
		return count;
	}

	public Integer update(Role obj) {
		return roleMapper.update(obj);
	}

	public int updateBatch(List<Role> listObjs) {
		int count = 0;
		SqlSession sqlSessionBatch = sqlSessionFactory.openSession(
				ExecutorType.BATCH, false);
		try {
			sqlSessionBatch.getConnection().setAutoCommit(false);
			RoleMapper mapper = sqlSessionBatch.getMapper(RoleMapper.class);
			for (Role obj : listObjs) {
				count++;
				mapper.update(obj);
			}
			sqlSessionBatch.commit();
		} catch (Exception e) {
		} finally {
			sqlSessionBatch.close();
		}
		return count;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Integer updateByQuery(Role entity, RoleQuery query) {
		Map map = new HashMap();
		map.put("entity", entity);
		map.put("filterQuery", query);
		return roleMapper.updateByQueryMap(map);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer remove(RoleQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		return roleMapper.deleteByQueryMap(map);
	}

	public Integer remove(Role obj) {
		return roleMapper.delete(obj);
	}

	public int removeBatch(List<Role> listObjs) {
		int count = 0;
		SqlSession sqlSessionBatch = sqlSessionFactory.openSession(
				ExecutorType.BATCH, false);
		try {
			sqlSessionBatch.getConnection().setAutoCommit(false);
			RoleMapper mapper = sqlSessionBatch.getMapper(RoleMapper.class);
			for (Role obj : listObjs) {
				count++;
				mapper.delete(obj);
			}
			sqlSessionBatch.commit();
		} catch (Exception e) {
		} finally {
			sqlSessionBatch.close();
		}
		return count;
	}

}
