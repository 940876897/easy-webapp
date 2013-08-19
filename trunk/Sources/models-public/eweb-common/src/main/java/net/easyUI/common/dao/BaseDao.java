package net.easyUI.common.dao;

import java.io.Serializable;
import java.util.Map;

import net.easyUI.common.domain.query.Page;

import org.mybatis.spring.SqlSessionTemplate;

public interface BaseDao <T,PK extends Serializable> {
	public SqlSessionTemplate getSqlSessionTemplate() ;
	public String getPkName();
	/**
	 * 添加保存单条记录
	 * 
	 * @param Object
	 * @return
	 */
	public Long save(T obj);

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param key
	 * @return
	 */
	public T queryOne(PK key);

	/**
	 * 根据主键修改单条记录
	 * 
	 * @param Object
	 * @return
	 */
	public int update(T obj);

	/**
	 * 根据条件更新非空字段,建议使用子类自己的方法(将参数改成对应的Query对象)
	 * 
	 * @author busfly 2010-09-25
	 * @param queryMap
	 *            由两个内容组成:1,对象(不更新的请为空)map.put("entity", entity);
	 *            2,条件(各对象的query)map.put("filterMap", query);
	 */
	@SuppressWarnings("rawtypes")
	public int updateByQuery(Map queryMap);

	/**
	 * 根据条件删除,建议使用子类自己的方法(将参数改成对应的Query对象)
	 * 
	 * @author busfly 2010-09-25
	 * @param queryMap
	 *            由两个内容组成:1,对象(不更新的请为空)map.put("entity", entity);
	 *            2,条件(各对象的query)map.put("filterMap", query);
	 */
	@SuppressWarnings("rawtypes")
	public int deleteByQuery(Map queryMap);

	/**
	 * 根据条件分页查询,建议使用子类自己的方法(将参数改成对应的Query对象). (暂无开发完成)
	 * 
	 * @author busfly 2010-09-25
	 * @param queryMap
	 *            由两个内容组成:1,对象(不更新的请为空)map.put("entity", entity);
	 *            2,条件(各对象的query)map.put("filterMap", query);
	 */
	@SuppressWarnings("rawtypes")
	public Page<T> pageQuery(Map queryMap);

	/**
	 * 获取其表对象在Ibatis Mapper里的 NameSapce空间名
	 * 
	 * @return
	 */
	public String getIbatisMapperNameSapce();

	/**
	 * 根据功能名,组装Statement名称
	 * 
	 * @param funName
	 *            可使用父类里BaseDaoImpl的静态变量
	 * @return
	 */
	public String getIbatisStatement(String funName);
}
