package net.easyUI.common.domain.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easyUI.common.dto.BaseDTO;
import net.easyUI.common.util.StringUtil;

public abstract class BaseQuery<T> extends BaseDTO {
	private static final long serialVersionUID = -360860474471966681L;
	public static final int DEFAULT_PAGE_SIZE = 10; // 默认每页显示多少条记录
	protected int page; // 当前所在页码(从1开始) <jqGrid>
	protected int rows; // 每页显示多少条记录,SQL中可能使用(Mysql) <jqGrid>
	protected int startRow; // 当前页开始为第几条,SQL中使用
	protected int endRow; // 当前页结束为第几条,SQL中使用
	protected List<Map<String, String>> orderByList = new ArrayList<Map<String, String>>(); // 排序方式集
	protected String mySqlLimit; // Mysql 中分页代码
	protected String sidx; // 排序字段名 <jqGrid>
	/**
	 * <pre>
	 * sordMap.put(&quot;sidx&quot;, sidx);
	 * sordMap.put(&quot;sord&quot;, this.getSord());
	 * </pre>
	 */
	protected Map<String, String> sordMap = new HashMap<String, String>();
	protected String sord; // 排序方式asc/desc <jqGrid>
	protected String searchField; // 过滤字段名 <jqGrid>
	/**
	 * 过滤类型
	 * 
	 * <pre>
	 * "eq"=等于    , "ne"!=不等
	 * "lt"<小于    , "le"<=小于等于
	 * "gt">大于    , "ge">=大于等于
	 * "bw" 开始于  , "bn" 不开始于
	 * "in" 属于    , "ni" 不属于
	 * "ew" 结束于  , "en" 不结束于
	 * "cn" 包含    , "nc" 不包含
	 * </pre>
	 */
	protected String searchOper; // <jqGrid>
	protected String searchString;// 过滤条件值 <jqGrid>
	/**
	 * 最终生效的查询条件 额外过滤条件Map(key=>查询字段名,就是searchField+"_"+searchOper ,
	 * value==>searchString)
	 */
	protected Map<String, Object> filterMap = new HashMap<String, Object>();
	/**
	 * 操作的POJO对象,一般用来带查询的修改(updateSome)操作中,将修改的新属性放在此对象中
	 */
	protected T entity;

	protected Map<String, String> lastSortMap = new HashMap<String, String>();

	// private Long id;

	/**
	 * 默认的最后一种排序方式
	 * 
	 * @return
	 */
	public abstract Map<String, String> getLastSortMap();

	/**
	 * TODO 判断此查询条件内是否为空条件
	 * 
	 * @return
	 */
	public boolean isWhereNull() {
		return false;
	}

	// public Long getId() {
	// return id;
	// }
	//
	// public void setId(Long id) {
	// this.id = id;
	// }

	public BaseQuery() {
		this(0, DEFAULT_PAGE_SIZE);
	}

	/**
	 * 
	 * @param pageNumber
	 *            当前是第几页
	 * @param pageSize
	 *            每页显示多少条
	 */
	public BaseQuery(int pageNumber, int pageSize) {
		this.page = pageNumber;
		this.rows = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		if (StringUtil.isNotBlank(sidx)) {
			sordMap.put("sidx", sidx);
			sordMap.put("sord", this.getSord());
		}
		this.sidx = sidx;
	}

	public String getSord() {
		if (StringUtil.isBlank(this.sord) || !this.sord.equalsIgnoreCase("asc"))
			this.sord = "desc";
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
		sordMap.put("sord", this.getSord());
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public int getStartRow() {
		return (this.getPage() - 1) * this.getRows();
		// return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	/**
	 * 获取Mysql里,分页SQL代码,如 limit 6,2 中的(6,2)部分
	 * 
	 * @return 类似 limit 6,2 中的(6,2)部分
	 */
	public String getMySqlLimit() {
		this.mySqlLimit = this.getStartRow() < 0 ? null : (this.getStartRow()
				+ "," + this.getRows());
		return mySqlLimit;
	}

	/**
	 * 获取排序方式,可以有多个排序方式
	 * 
	 * @return
	 */
	public List<Map<String, String>> getOrderByList() {
		List<Map<String, String>> orderByListTmp = new ArrayList<Map<String, String>>();
		// 添加第一排序
		if (this.getSordMap() != null)
			orderByListTmp.add(sordMap);
		// 添加其它排序
		orderByListTmp.addAll(this.orderByList);
		if (this.getLastSortMap() != null) {
			// 添加默认排序在最后
			orderByListTmp.add(this.getLastSortMap());
		}
		return orderByListTmp;
	}

	public void setOrderByList(List<Map<String, String>> orderByList) {
		this.orderByList = orderByList;
	}

	public Map<String, String> getSordMap() {
		return sordMap;
	}

	public void setLastSortMap(Map<String, String> lastSortMap) {
		this.lastSortMap = lastSortMap;
	}

	/**
	 * 获取查询过滤条件Map,Map的Key为条件名,Value为条件值
	 * 
	 * @return
	 */
	public Map<String, Object> getFilterMap() {
		String filterKey = null;

		if (!(StringUtil.isBlank(this.getSearchField()) || StringUtil
				.isBlank(this.getSearchOper()))) {
			filterKey = this.getSearchField() + "_" + this.getSearchOper();
			if (!this.filterMap.containsKey(filterKey))
				this.filterMap.put(filterKey, this.getSearchString());
		}
		return filterMap;
	}

	/**
	 * 添加查询条件，这里的查询条件全部会自动导入到查询条件中。
	 * 
	 * @param moreFilterMap
	 */
	public void setFilter(Map<String, Object> moreFilterMap) {
		filterMap.putAll(moreFilterMap);
	}

	/**
	 * 操作的POJO对象,一般用来带查询的修改(updateSome)操作中,将修改的新属性放在此对象中
	 * 
	 * @return
	 */
	public T getEntity() {
		return entity;
	}

	/**
	 * 操作的POJO对象,一般用来带查询的修改(updateSome)操作中,将修改的新属性放在此对象中
	 * 
	 * @param entity
	 */
	public void setEntity(T entity) {
		this.entity = entity;
	}

	/**
	 * 设置扩展查询条件，由此可直接保存查询条件，而不必为各种查询都创建一个Query类型
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value) {
		this.filterMap.put(key, value);
	}

	/**
	 * 获取查询条件的值
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return this.filterMap.get(key);
	}
}