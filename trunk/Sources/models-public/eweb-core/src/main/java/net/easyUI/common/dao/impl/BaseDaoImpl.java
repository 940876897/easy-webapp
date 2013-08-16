package net.easyUI.common.dao.impl;

import java.io.Serializable;
import java.util.Map;

import net.easyUI.common.dao.BaseDao;
import net.easyUI.common.domain.query.Page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * @author busfly www.busfly.net
 * 
 */
public abstract class BaseDaoImpl<E, PK extends Serializable> extends
		SqlSessionDaoSupport implements BaseDao<E, PK> {
	public Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 添加记录insert
	 */
	public static String INSERT = "insert";
	/**
	 * 修改单条记录(包括空值,根据主键修改)update
	 */
	public static String UPDATE = "update";
	/**
	 * 修改符合条件的记录,且只修改不为空的属性updateByQuery
	 */
	public static String UPDATEBYQUERY = "updateByQuery";
	/**
	 * 删除单条记录(根据主键,一般是ID)delete
	 */
	public static String DELETE = "delete";
	/**
	 * 删除符合条件的记录deleteQuery
	 */
	public static String DELETEQUERY = "deleteQuery";
	/**
	 * 根据主键ID查询单个记录getById
	 */
	public static String GETBYID = "getById";
	/**
	 * 分页查询中,查询总记录数量findPage_count
	 */
	public static String FINDPAGE_COUNT = "findPage_count";
	/**
	 * 分页查询findPage
	 */
	public static String FINDPAGE = "findPage";

	public abstract String getIbatisMapperNameSapce();

	public String getIbatisStatement(String funName) {
		return this.getIbatisMapperNameSapce() + "." + funName;
	}

	public Long save(E obj) {
		return (long) getSqlSessionTemplate().insert(
				this.getIbatisStatement(INSERT), obj);
	}

	@SuppressWarnings("unchecked")
	public E queryOne(PK key) {
		return (E) getSqlSessionTemplate().selectOne(
				this.getIbatisStatement("getBy" + this.getPkName()), key);
	}

	abstract public String getPkName();

	public int update(E obj) {
		return getSqlSessionTemplate().update(this.getIbatisStatement(UPDATE),
				obj);
	}

	/**
	 * 根据条件更新非空字段,建议使用子类自己的方法(将参数改成对应的Query对象)
	 * 
	 * @author busfly 2010-09-25
	 * @param map
	 *            由两个内容组成:1,对象(不更新的请为空)map.put("entity", entity);
	 *            2,条件(各对象的query)map.put("filterMap", query);
	 */
	@SuppressWarnings("rawtypes")
	public int updateByQuery(Map queryMap) {
		int affectCount = getSqlSessionTemplate().update(
				this.getIbatisStatement(UPDATEBYQUERY), queryMap);
		return affectCount;
	}

	/**
	 * 根据条件删除,建议使用子类自己的方法(将参数改成对应的Query对象)
	 * 
	 * @author busfly 2010-09-25
	 * @param map
	 *            由两个内容组成:1,对象(不更新的请为空)map.put("entity", entity);
	 *            2,条件(各对象的query)map.put("filterMap", query);
	 */
	@SuppressWarnings("rawtypes")
	public int deleteByQuery(Map queryMap) {
		int affectCount = getSqlSessionTemplate().delete(
				this.getIbatisStatement(DELETEQUERY), queryMap);
		return affectCount;
	}

	@SuppressWarnings("rawtypes")
	public Page<E> pageQuery(Map queryMap) {
		throw new UnsupportedOperationException();
	}
}
