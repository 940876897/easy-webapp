package net.easyUI.common.domain.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.easyUI.common.util.PageUtils;

@SuppressWarnings("rawtypes")
public class Page<T> implements Serializable, Iterable {
	private static final long serialVersionUID = -3817589156255361407L;
	protected List<T> rows;
	protected int pageSize;
	protected int pageNumber;
	protected long totalCount;

	/**
	 * 给jqGrid用,获取当前在第几页
	 * 
	 * @return
	 */
	public int getPage() {
		return this.getThisPageNumber();
	}

	/**
	 * 给jqGrid用,获取总共有几页
	 * 
	 * @return
	 */
	public int getTotal() {
		return this.getLastPageNumber();
	}

	/**
	 * 给jqGrid用,获取总共有几条记录
	 * 
	 * @return
	 */
	public long getRecords() {
		return this.getTotalCount();
	}

	public Page(BaseQuery query, long totalCount) {
		this(query.getPage(), query.getRows(), totalCount);
		// 供页面显示分页数据
		query.setPage(this.getThisPageNumber());
		query.setStartRow(this.getFirstResult());
		query.setEndRow(this.getFirstResult() + this.getPageSize());
	}

	public Page(int pageNumber, int pageSize, long totalCount) {
		this(pageNumber, pageSize, totalCount, new ArrayList<T>(0));
	}

	public Page(int pageNumber, int pageSize, long totalCount, List<T> result) {
		this.totalCount = 0;
		if (pageSize <= 0) {
			throw new IllegalArgumentException(
					"[pageSize] must great than zero");
		} else {
			this.pageSize = pageSize;
			this.pageNumber = PageUtils.computePageNumber(pageNumber, pageSize,
					totalCount);
			this.totalCount = totalCount;
			setRows(result);
			return;
		}
	}

	public void setRows(List<T> elements) {
		if (elements == null) {
			elements=Collections.emptyList();
//			throw new IllegalArgumentException("'result' must be not null");
		}
		rows = elements;
		return;
	}

	public List<T> getRows() {
		return rows;
	}

	/**
	 * 是否是第一页
	 * 
	 * @return
	 */
	public boolean isFirstPage() {
		return getThisPageNumber() == 1;
	}

	/**
	 * 是否是最后一页
	 * 
	 * @return
	 */
	public boolean isLastPage() {
		return getThisPageNumber() >= getLastPageNumber();
	}

	/**
	 * 是否有下一页
	 * 
	 * @return
	 */
	public boolean isHasNextPage() {
		return getLastPageNumber() > getThisPageNumber();
	}

	/**
	 * 是否有上一页
	 * 
	 * @return
	 */
	public boolean isHasPreviousPage() {
		return getThisPageNumber() > 1;
	}

	/**
	 * 最后一页页码
	 * 
	 * @return
	 */
	public int getLastPageNumber() {
		return PageUtils.computeLastPageNumber(totalCount, pageSize);
	}

	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 当前页里,第一条记录占全部记录的第几条 (从1开始计数)
	 * 
	 * @return
	 */
	public long getThisPageFirstElementNumber() {
		return (getThisPageNumber() - 1) * getPageSize() + 1;
	}

	/**
	 * 当前页里,最后一条记录占全部记录的第几条 (从1开始计数)
	 * 
	 * @return
	 */
	public long getThisPageLastElementNumber() {
		long fullPage = (getThisPageFirstElementNumber() + getPageSize()) - 1;
		return getTotalCount() >= fullPage ? fullPage : getTotalCount();
	}

	/**
	 * 下一页的页码
	 * 
	 * @return
	 */
	public int getNextPageNumber() {
		return getThisPageNumber() + 1;
	}

	/**
	 * 前一页的页码
	 * 
	 * @return
	 */
	public int getPreviousPageNumber() {
		return getThisPageNumber() - 1;
	}

	/**
	 * 获取每页显示几条
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 获取当前是第几页
	 * 
	 * @return
	 */
	public int getThisPageNumber() {
		return pageNumber;
	}

	@SuppressWarnings("unchecked")
	public List<T> getLinkPageNumbers() {
		return (List<T>) PageUtils.generateLinkPageNumbers(getThisPageNumber(),
				getLastPageNumber(), 10);
	}

	/**
	 * 表格里第一条记录是所有记录中第几条,从0开始
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return PageUtils.getFirstResult(pageNumber, pageSize);
	}

	public Iterator iterator() {
		return rows != null ? rows.iterator() : Collections.emptyList()
				.iterator();
	}

}
