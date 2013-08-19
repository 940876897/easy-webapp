package net.easyUI.common.util.html.format;


public interface HTMLFormater {
	/**
	 * 格式化指定HTML代码,只保留配置过的,并且修正错误的标签
	 * 
	 * @param origHTML
	 * @return
	 */
	public String formatHTML(String origHTML);
}
