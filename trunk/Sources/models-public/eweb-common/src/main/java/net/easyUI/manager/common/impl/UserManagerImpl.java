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

import net.easyUI.domain.common.User;
import net.easyUI.domain.query.UserQuery;
import net.easyUI.dto.common.query.DwzPage;
import net.easyUI.manager.common.UserManager;
import net.easyUI.mapper.common.UserMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userManager")
public class UserManagerImpl implements UserManager {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	protected static Log logger = LogFactory.getLog(UserManagerImpl.class);

	public User queryOne(Long id) {
		return userMapper.getByKey(id);
	}

	public User queryOneFullFK(Long id) {
		return userMapper.getByKeyFullFK(id);
	}

	/**
	 * 根据唯一字段{column.columnName}查询单条记录
	 */

	public User getByUkLoginName(String v) {
		return userMapper.getByUkLoginName(v);
	}

	/**
	 * 根据唯一字段{column.columnName}查询单条记录
	 */

	public User getByUkLoginNameFullFK(String v) {
		return userMapper.getByUkLoginNameFullFK(v);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DwzPage<User> pageQuery(UserQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		long count = userMapper.countQueryMap(map);
		DwzPage<User> page = new DwzPage<User>(query, count);
		map.put("filterQuery", query);
		// (offset,limit) 从偏移量offset(从0开始)开始取数据，最多取limit条
		RowBounds rowBounds = new RowBounds(page.getFirstResult(),
				page.getPageSize());
		List<User> list = userMapper.listQueryMap(map, rowBounds);
		page.setRows(list);
		return page;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DwzPage<User> pageQueryFullFK(UserQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		long count = userMapper.countQueryMap(map);
		DwzPage<User> page = new DwzPage<User>(query, count);
		map.put("filterQuery", query);
		// (offset,limit) 从偏移量offset(从0开始)开始取数据，最多取limit条
		RowBounds rowBounds = new RowBounds(page.getFirstResult(),
				page.getPageSize());
		List<User> list = userMapper.listQueryMapFullFK(map, rowBounds);
		page.setRows(list);
		return page;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<User> listQuery(UserQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		List<User> list = userMapper.listQueryMap(map);
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<User> listQueryFullFK(UserQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		List<User> list = userMapper.listQueryMapFullFK(map);
		return list;
	}

	public User save(User obj) {
		userMapper.insert(obj);
		return obj;
	}

	public int saveBatch(List<User> listObjs) {
		int count = 0;
		SqlSession sqlSessionBatch = sqlSessionFactory.openSession(
				ExecutorType.BATCH, false);
		try {
			sqlSessionBatch.getConnection().setAutoCommit(false);
			UserMapper mapper = sqlSessionBatch.getMapper(UserMapper.class);
			for (User obj : listObjs) {
				mapper.insert(obj);
				count++;
			}
			sqlSessionBatch.commit();
		} catch (Exception e) {
		} finally {
			sqlSessionBatch.close();
		}
		return count;
	}

	public Integer update(User obj) {
		return userMapper.update(obj);
	}

	public int updateBatch(List<User> listObjs) {
		int count = 0;
		SqlSession sqlSessionBatch = sqlSessionFactory.openSession(
				ExecutorType.BATCH, false);
		try {
			sqlSessionBatch.getConnection().setAutoCommit(false);
			UserMapper mapper = sqlSessionBatch.getMapper(UserMapper.class);
			for (User obj : listObjs) {
				mapper.update(obj);
				count++;
			}
			sqlSessionBatch.commit();
		} catch (Exception e) {
		} finally {
			sqlSessionBatch.close();
		}
		return count;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Integer updateByQuery(User entity, UserQuery query) {
		Map map = new HashMap();
		map.put("entity", entity);
		map.put("filterQuery", query);
		return userMapper.updateByQueryMap(map);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer remove(UserQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		return userMapper.deleteByQueryMap(map);
	}

	public Integer remove(User obj) {
		return userMapper.delete(obj);
	}

	public int removeBatch(List<User> listObjs) {
		int count = 0;
		SqlSession sqlSessionBatch = sqlSessionFactory.openSession(
				ExecutorType.BATCH, false);
		try {
			sqlSessionBatch.getConnection().setAutoCommit(false);
			UserMapper mapper = sqlSessionBatch.getMapper(UserMapper.class);
			for (User obj : listObjs) {
				mapper.delete(obj);
				count++;
			}
			sqlSessionBatch.commit();
		} catch (Exception e) {
		} finally {
			sqlSessionBatch.close();
		}
		return count;
	}

}
