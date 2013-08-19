package net.easyUI.dto.common.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.easyUI.common.domain.query.BaseQuery;

import org.apache.commons.lang.StringUtils;

public class DwzBaseQuery<T> extends BaseQuery<T> {
	private static final long serialVersionUID = -360860474471966721L;

	public static final int DEFAULT_PAGE_SIZE = 20; // 默认每页显示多少条记录
	/** 每页显示多少条 */
	protected int numPerPage = 20;
	/** 当前是第几页 */
	protected int pageNum = 1;
	/** 查询排序 */
	protected String orderField;
	/** 排序方式desc,asc */
	protected String orderDirection = "desc";
	/** DWZ中打开面板的ID */
	protected String dwzId;

	protected String ids; // 主要给批量操作时,多个主键之间用英文逗号(,)分隔

	// 主要给批量操作时,多个主键之间用英文逗号(,)分隔
	public String getIds() {
		return ids;
	}

	@Override
	// 根据实际情况设置一个默认的排序方式
	public Map<String, String> getLastSortMap() {
		return super.lastSortMap;
	}

	public List<Long> getIdsList() {
		List<Long> id_in = new ArrayList<Long>();
		if (StringUtils.isNotBlank(this.getIds())) {
			String[] idsLs = this.getIds().split(",");

			for (String idStr : idsLs) {
				try {
					Long id_t = Long.parseLong(idStr.trim());
					id_in.add(id_t);
				} catch (Exception e) {
					logger.error("将ids字条串拆成id的Long列表时,idStr=[" + idStr
							+ "],出错,此ID将被忽略", e);
				}
			}
		}
		return id_in;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	// ////////////////////////////////////////////////////////////////////////////////

	public DwzBaseQuery() {
		this(0, DEFAULT_PAGE_SIZE);
	}

	/**
	 * @param pageNumber
	 *            当前是第几页
	 * @param pageSize
	 *            每页显示多少条
	 */
	public DwzBaseQuery(int pageNumber, int pageSize) {
		super(pageNumber, pageSize);
		this.pageNum = pageNumber;
		this.numPerPage = pageSize;
	}

	/** 每页显示多少条 */
	public int getNumPerPage() {
		this.numPerPage = super.getRows();
		return numPerPage;
	}

	/** 每页显示多少条 */
	public void setNumPerPage(int numPerPage) {
		super.setRows(numPerPage);
		this.numPerPage = numPerPage;
	}

	/** 当前是第几页 */
	public int getPageNum() {
		this.pageNum = super.getPage();
		return pageNum;
	}

	/** 当前是第几页 */
	public void setPageNum(int pageNum) {
		super.setPage(pageNum);
		this.pageNum = pageNum;
	}

	/** 查询排序 */
	public String getOrderField() {
		if (StringUtils.isBlank(orderField)) {
			this.orderField = super.getSidx();
		}
		return orderField;
	}

	/** 查询排序 */
	public void setOrderField(String orderField) {
		this.orderField = orderField;
		super.setSidx(this.orderField);
	}

	/**
	 * DWZ排序方式desc,asc
	 * 
	 * @return
	 */
	public String getOrderDirection() {
		if (StringUtils.isBlank(orderField)) {
			this.orderField = super.getSord();
		}
		if (!"asc".equalsIgnoreCase(this.orderDirection))
			this.setOrderDirection("desc");
		return orderDirection;
	}

	/**
	 * DWZ排序方式desc,asc
	 * 
	 * @param orderDirection
	 */
	public void setOrderDirection(String orderDirection) {
		if ("asc".equalsIgnoreCase(orderDirection))
			this.orderDirection = "asc";
		else
			this.orderDirection = "desc";
		super.setSord(this.orderDirection);
	}

	/** DWZ中打开面板的ID */
	public String getDwzId() {
		return dwzId;
	}

	/** DWZ中打开面板的ID */
	public void setDwzId(String dwzId) {
		this.dwzId = dwzId;
	}
}