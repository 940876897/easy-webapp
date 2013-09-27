package net.easyUI.dto.common.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.easyUI.common.domain.query.BaseQuery;

import org.apache.commons.lang.StringUtils;

/**
 * 前端UI使用DWZ的页面，使用这个BaseQuery，这里已经绑定好了对应的分页，排序支持。 其中泛型“T”对象是指要保存的对象，不是查询条件。
 * <p>
 * 查询条件 Map(key=>查询字段名searchField+"_"+searchOper , value==>searchString),
 * 多条件时，采用的是and 方式组合。 <br>
 * 过滤类型searchOper如下：
 * 
 * <pre>
 * "eq"=等于    , "ne"!=不等
 * "lt"<小于    , "le"<=小于等于
 * "gt">大于    , "ge">=大于等于
 * "bw" 开始于(右模糊)  , "bn" 不开始于
 * "in" 属于    , "ni" 不属于
 * "ew" 结束于(左模糊)  , "en" 不结束于
 * "cn" 包含    , "nc" 不包含
 * </pre>
 * 
 * @author hs-yuancong
 * 
 * @param <T>
 */
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
//	/**
//	 * DWZ中打开面板的ID，主要用于UI端标记操作来源Tab或Dialog，以便后续操作完成时，通知或刷新源页面。如果是lookup，
//	 * 表示打开的是弹出选择页面。
//	 */
//	protected String dwzId="";
//	/**
//	 * 操作成功后,要重新加载的Tab或者dialog的 ID号。
//	 * [把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的. 否则Tab重新载入当前navTab页面
//	 */
//	private String navTabId = "";
//	/**
//	 * EnumDwzJsonCallbackType . “forward”在当前面板中跳转到【forwardUrl】，
//	 * “closeCurrent”关闭当前面板Tab或者dialog
//	 */
//	private String callbackType = "";
//	/** callbackType=“forward”时在当前面板中跳转到forwardUrl */
//	private String forwardUrl = "";

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

//	/** DWZ中打开面板的ID */
//	public String getDwzId() {
//		int index = dwzId.indexOf(",");
//		if(index>0)
//			dwzId= dwzId.substring(0, index);
//		return dwzId;
//	}
//
//	/** DWZ中打开面板的ID */
//	public void setDwzId(String dwzId) {
//		this.dwzId = dwzId;
//	}
//
//	public String getNavTabId() {
//		int index = navTabId.indexOf(",");
//		if(index>0)
//			navTabId= navTabId.substring(0, index);
//		return navTabId;
//	}
//
//	public void setNavTabId(String navTabId) {
//		this.navTabId = navTabId;
//	}
//
//	public String getCallbackType() {
//		int index = callbackType.indexOf(",");
//		if(index>0)
//			callbackType= callbackType.substring(0, index);
//		return callbackType;
//	}
//
//	public void setCallbackType(String callbackType) {
//		this.callbackType = callbackType;
//	}
//
//	public String getForwardUrl() {
//		int index = forwardUrl.indexOf(",");
//		if(index>0)
//			forwardUrl= forwardUrl.substring(0, index);
//		return forwardUrl;
//	}
//
//	public void setForwardUrl(String forwardUrl) {
//		this.forwardUrl = forwardUrl;
//	}
}