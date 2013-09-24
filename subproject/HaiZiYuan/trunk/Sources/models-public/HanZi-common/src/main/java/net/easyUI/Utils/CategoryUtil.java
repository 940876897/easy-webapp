package net.easyUI.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * Post的多级分类 的工具类.比如将List转成树结构.
 * 
 */
public class CategoryUtil {
    /**
     * 将多级分类List转成树结构
     * @param categoryMapList 分类List, 如果没有传此参数(NULL或者长度为0的List),则会自动取缓存中的.
     * @param parCode 父节点Code,可以为空或Null.
     * @param showLev 显示父节点的几级子节点,-1表示显示全部子孙节点,0表示只显示父节点,1表示显示到子1级,以此类推.
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List list2tree(final List categoryMapList, String parCode,
			Integer showLev) {
		List<Map> listTree = new ArrayList<Map>();
		// 1.遍历一次,根据Code(下划线分隔)计算一下是第几级(扩展到原Map上),并统计出最小级min和最大级max
		int minLev = Integer.MAX_VALUE; // 最小级数
		for (Object categoryObj : categoryMapList) {
			Map categoryMap = (Map) categoryObj;
			String code = categoryMap.get("code") + "";
			int levLen = code.split("_").length;
			categoryMap.put("lev", levLen);

			// 如果父Code传递过来非空,则只显示此父Code及其下的节点.
			int levPar = 0;
			if (StringUtils.isNotBlank(parCode)) {
				levPar = parCode.split("_").length;
				// 如果不是这个父Code开头的,忽略.
				if (!code.startsWith(parCode))
					continue;
			}
			// 控制显示孙节点及以下节点.
			if (!showLev.equals(-1) && levLen - levPar > showLev)
				continue;

			if (levLen < minLev) {
				minLev = levLen;
				listTree.clear();
				listTree.add(categoryMap);
			} else if (levLen == minLev) {
				listTree.add(categoryMap);
			}
			((Map) categoryObj).put("lev", levLen);// 初始化节点的层级
		}

		// 2.从最小级开始组装,再循环查询组装每级下的子树.
		for (Map treeNodeMap : listTree) {
			// 找出子节点放入当前节点的子节点中.
			putChilds(treeNodeMap, categoryMapList, parCode, showLev);
		}
		return listTree;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void putChilds(Map treeNodeMap, List categoryMapList,
			String parCode, Integer showLev) {
		int levNode = (Integer) treeNodeMap.get("lev");
		for (Object categoryObj : categoryMapList) {
			Map categoryChildMap = (Map) categoryObj;
			String code = categoryChildMap.get("code") + "";
			int levChild = (Integer) categoryChildMap.get("lev");

			// 如果父Code传递过来非空,则只显示此父Code及其下的节点.
			int levPar = 0;
			if (StringUtils.isNotBlank(parCode)) {
				levPar = parCode.split("_").length;
				// 如果不是这个父Code开头的,忽略.
				if (!code.startsWith(parCode))
					continue;
			}
			// 控制显示孙节点及以下节点.
			if (!showLev.equals(-1) && levChild - levPar > showLev)
				continue;

			// 是子节点
			if (levChild == (levNode + 1)
					&& code.startsWith(treeNodeMap.get("code") + "")) {
				// 设置本节点为其子节点.
				if (((List) treeNodeMap.get("nodes")) == null)
					treeNodeMap.put("nodes", new ArrayList<Map>());
				((List) treeNodeMap.get("nodes")).add(categoryChildMap);
				// 再设置子节点的子节点.
				putChilds(categoryChildMap, categoryMapList, parCode, showLev);
			}
		}
	}

}
