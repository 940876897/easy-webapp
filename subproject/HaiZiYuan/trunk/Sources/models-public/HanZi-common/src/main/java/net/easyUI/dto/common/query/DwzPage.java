package net.easyUI.dto.common.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.easyUI.common.domain.query.Page;

@SuppressWarnings("rawtypes")
public class DwzPage<T> extends Page<T> implements Serializable, Iterable {
	private static final long serialVersionUID = -3817589156255782407L;

	/** navTab或dialog，用来标记是navTab上的分页还是dialog上的分页 */
	protected String targetType;
	/** 每页显示多少条 */
	protected int numPerPage;
	/** 页标数字多少个 */
	protected int pageNumShown=10;
	/** 当前是第几页 */
	protected int currentPage;
	// ===========================
	/** 总条数 */
//	protected long totalCount;

	// ===========================

	public DwzPage(DwzBaseQuery query, long totalCount) {
		this(query.getPage(), query.getRows(), totalCount);
		// 供页面显示分页数据
		query.setPage(this.getThisPageNumber());
		query.setStartRow(this.getFirstResult());
		query.setEndRow(this.getFirstResult() + this.getPageSize());
	}

	public DwzPage(int pageNumber, int pageSize, long totalCount) {
		this(pageNumber, pageSize, totalCount, new ArrayList<T>(0));
	}

	public DwzPage(int pageNumber, int pageSize, long totalCount, List<T> result) {
		super(pageNumber, pageSize, totalCount, result);
	}

	// ==========================================
	/** navTab或dialog，用来标记是navTab上的分页还是dialog上的分页 */
	public String getTargetType() {
		return targetType;
	}

	/** navTab或dialog，用来标记是navTab上的分页还是dialog上的分页 */
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	/** 每页显示多少条 */
	public int getNumPerPage() {
		this.numPerPage = super.getPageSize();
		return numPerPage;
	}

	/** 每页显示多少条 */
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
		super.pageSize =this.numPerPage;
	}

	/** 页标数字多少个 */
	public int getPageNumShown() {
		return pageNumShown;
	}

	/** 页标数字多少个 */
	public void setPageNumShown(int pageNumShown) {
		this.pageNumShown = pageNumShown;
	}

	/** 当前是第几页 */
	public int getCurrentPage() {
		this.currentPage = super.getThisPageNumber();
		return currentPage;
	}

	/** 当前是第几页 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
