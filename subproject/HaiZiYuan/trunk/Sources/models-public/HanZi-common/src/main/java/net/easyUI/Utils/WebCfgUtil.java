package net.easyUI.Utils;

import java.util.List;

import net.easyUI.domain.common.WebConfig;

import org.apache.commons.lang.StringUtils;

/**
 * 一个公共的util工具类，系统中公用的工具方法可以放到此类中
 * 
 */
public class WebCfgUtil {

	/**
	 * 模板支持的变量名有: ${cfg_id} ${cfg_cfgName} ,${cfg_cfgNameLastShort},
	 * ${cfg_filePath}
	 * 
	 * @param vmFileList
	 *            要求List一定是要按照cfgName排序好.
	 * @param treeRootPath
	 *            根目录,此根目录不会显示在Tree上,以减少层级,方便显示.
	 * @param preParHtml
	 *            父节点开始模板
	 * @param endParHtml
	 *            父节点结束模板
	 * @param nodeHtml
	 *            当前节点模板.
	 * @return
	 */
	public static String makeVmFilePathDwz(List<WebConfig> vmFileList,
			String treeRootPath, String preParHtml, String endParHtml,
			String nodeHtml) {
		return vmFilePathDwz(vmFileList, treeRootPath, 0, null, preParHtml,
				endParHtml, nodeHtml);
	}

	/**
	 * 支持的变量名有: ${cfg_id} ${cfg_cfgName} ,${cfg_cfgNameLastShort},
	 * ${cfg_filePath}
	 * 
	 * @param tl
	 * @param webCfg
	 *            包括:${cfg_id} ${cfg_cfgName}
	 * @param pars
	 *            当前Path(${cfg_filePath}),
	 * @return
	 */
	private static String formatWebCfgStr(String tl, WebConfig webCfg,
			String... pars) {
		String tmpStr = tl
				.replace("${cfg_id}", webCfg.getId().toString())
				.replace("${cfg_cfgName}", webCfg.getCfgName())
				.replace(
						"${cfg_cfgNameLastShort}",
						webCfg.getCfgName().substring(
								webCfg.getCfgName().lastIndexOf("/") + 1));
		if (pars != null && pars.length > 0)
			return tmpStr.replace("${cfg_filePath}", pars[0]);
		else
			return tmpStr;
	}

	/**
	 * 要求List一定是要按照cfgName排序好.
	 * 
	 * @param param
	 * @return
	 */
	private static String vmFilePathDwz(List<WebConfig> vmFileList,
			String treeRootPath, Integer index, String[] parCfgPathNames,
			String preParHtml, String endParHtml, String nodeHtml) {
		if (vmFileList == null || vmFileList.size() == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		if (index == null)
			index = 0;
		WebConfig webCfg = vmFileList.get(index);
		String cfgName = webCfg.getCfgName();
		// 如果不是以treeRootPath开头的VM路径,直接跳过本节点,返回下一个.
		if (!cfgName.startsWith(treeRootPath))
			return vmFilePathDwz(vmFileList, treeRootPath, index + 1,
					parCfgPathNames, preParHtml, endParHtml, nodeHtml);
		// 文件夹目录
		String[] webCfgNames = StringUtils.splitByWholeSeparator(
				cfgName.replaceFirst(treeRootPath, ""), "/");
		// ## 与父节点重合的目录级别
		int pIndex = 0;
		if (parCfgPathNames != null && parCfgPathNames.length > 0) {
			// ## 是否已结束判断与父节点重合目录级别
			int pIndexEnd = 0;
			for (int deeps = 0; deeps < parCfgPathNames.length; deeps++) {
				// ## 如果当前节点组长度大于于父节点组,并且,当前节点与父类节点相同.当继续标记相同.
				if (pIndexEnd == 0 && webCfgNames.length > deeps
						&& webCfgNames[deeps].equals(parCfgPathNames[deeps])) {
					pIndex++;
				} else {
					pIndexEnd = 1;
					// ## 闭合父节点
					sb.append(formatWebCfgStr(endParHtml, webCfg));
				}
			}
		}

		// 创建 当前文件夹目录比共同目录多的子目录
		if (webCfgNames.length > pIndex) {
			for (int deeps = pIndex; deeps < webCfgNames.length - 1; deeps++) {
				// ## 创建父节点
				sb.append(formatWebCfgStr(preParHtml, webCfg,
						webCfgNames[deeps]));
			}
		}

		// ## 显示本叶子节点
		sb.append(formatWebCfgStr(nodeHtml, webCfg));
		// ## 如果List还有数据,回归调用本方法组装下一个节点.
		int nextIndex = index + 1;
		String[] nextParCfgPathNames = new String[webCfgNames.length - 1];
		for (int iTmp = 0; iTmp < (webCfgNames.length - 1); iTmp++) {
			nextParCfgPathNames[iTmp] = webCfgNames[iTmp];
		}
		if (nextIndex < vmFileList.size())
			sb.append(vmFilePathDwz(vmFileList, treeRootPath, nextIndex,
					nextParCfgPathNames, preParHtml, endParHtml, nodeHtml));
		// 如果是最后一个文件,再将剩余没闭合的,闭合掉
		if (nextIndex == vmFileList.size()) {
			for (int i = 0; i < webCfgNames.length-1; i++) {
				sb.append(formatWebCfgStr(endParHtml, webCfg));
			}
		}

		return sb.toString();
	}
}
