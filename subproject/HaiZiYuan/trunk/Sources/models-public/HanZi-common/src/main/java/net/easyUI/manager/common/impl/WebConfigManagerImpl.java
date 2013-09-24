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

import net.easyUI.domain.common.WebConfig;
import net.easyUI.domain.query.WebConfigQuery;
import net.easyUI.dto.common.query.DwzPage;
import net.easyUI.manager.common.WebConfigManager;
import net.easyUI.mapper.common.WebConfigMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("webConfigManager")
public class WebConfigManagerImpl implements WebConfigManager {
	@Autowired
	private WebConfigMapper webConfigMapper;
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	protected static Log logger = LogFactory.getLog(WebConfigManagerImpl.class);

	public WebConfig queryOne(Long id) {
		return webConfigMapper.getByKey(id);
	}

	public WebConfig queryOneFullFK(Long id) {
		return webConfigMapper.getByKeyFullFK(id);
	}

	/**
	 * 根据唯一字段{column.columnName}查询单条记录
	 */
	public WebConfig getByUkCfgName(java.lang.String v) {
		return webConfigMapper.getByUkCfgName(v);
	}

	/**
	 * 根据唯一字段{column.columnName}查询单条记录
	 */
	public WebConfig getByUkCfgNameFullFK(java.lang.String v) {
		return webConfigMapper.getByUkCfgNameFullFK(v);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DwzPage<WebConfig> pageQuery(WebConfigQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		long count = webConfigMapper.countQueryMap(map);
		DwzPage<WebConfig> page = new DwzPage<WebConfig>(query, count);
		map.put("filterQuery", query);
		// (offset,limit) 从偏移量offset(从0开始)开始取数据，最多取limit条
		RowBounds rowBounds = new RowBounds(page.getFirstResult(),
				page.getPageSize());
		List<WebConfig> list = webConfigMapper.listQueryMap(map, rowBounds);
		page.setRows(list);
		return page;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DwzPage<WebConfig> pageQueryFullFK(WebConfigQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		long count = webConfigMapper.countQueryMap(map);
		DwzPage<WebConfig> page = new DwzPage<WebConfig>(query, count);
		map.put("filterQuery", query);
		// (offset,limit) 从偏移量offset(从0开始)开始取数据，最多取limit条
		RowBounds rowBounds = new RowBounds(page.getFirstResult(),
				page.getPageSize());
		List<WebConfig> list = webConfigMapper.listQueryMapFullFK(map,
				rowBounds);
		page.setRows(list);
		return page;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<WebConfig> listQuery(WebConfigQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		List<WebConfig> list = webConfigMapper.listQueryMap(map);
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<WebConfig> listQueryFullFK(WebConfigQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		List<WebConfig> list = webConfigMapper.listQueryMapFullFK(map);
		return list;
	}

	public WebConfig save(WebConfig obj) {
		webConfigMapper.insert(obj);
		return obj;
	}

	public int saveBatch(List<WebConfig> listObjs) {
		int count = 0;
		SqlSession sqlSessionBatch = sqlSessionFactory.openSession(
				ExecutorType.BATCH, false);
		try {
			sqlSessionBatch.getConnection().setAutoCommit(false);
			WebConfigMapper mapper = sqlSessionBatch
					.getMapper(WebConfigMapper.class);
			for (WebConfig obj : listObjs) {
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

	public Integer update(WebConfig obj) {
		return webConfigMapper.update(obj);
	}

	public int updateBatch(List<WebConfig> listObjs) {
		int count = 0;
		SqlSession sqlSessionBatch = sqlSessionFactory.openSession(
				ExecutorType.BATCH, false);
		try {
			sqlSessionBatch.getConnection().setAutoCommit(false);
			WebConfigMapper mapper = sqlSessionBatch
					.getMapper(WebConfigMapper.class);
			for (WebConfig obj : listObjs) {
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
	public Integer updateByQuery(WebConfig entity, WebConfigQuery query) {
		Map map = new HashMap();
		map.put("entity", entity);
		map.put("filterQuery", query);
		return webConfigMapper.updateByQueryMap(map);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer remove(WebConfigQuery query) {
		Map map = new HashMap();
		map.put("filterQuery", query);
		return webConfigMapper.deleteByQueryMap(map);
	}

	public Integer remove(WebConfig obj) {
		return webConfigMapper.delete(obj);
	}

	public int removeBatch(List<WebConfig> listObjs) {
		int count = 0;
		SqlSession sqlSessionBatch = sqlSessionFactory.openSession(
				ExecutorType.BATCH, false);
		try {
			sqlSessionBatch.getConnection().setAutoCommit(false);
			WebConfigMapper mapper = sqlSessionBatch
					.getMapper(WebConfigMapper.class);
			for (WebConfig obj : listObjs) {
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
