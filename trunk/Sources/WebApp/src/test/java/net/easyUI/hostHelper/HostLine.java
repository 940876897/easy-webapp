package net.easyUI.hostHelper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class HostLine {
	// 行类型: 禁用# , 分组## , 启用空白或Null
	private String lineType = "";
	// 分组名里,以英文下划线( _ )分隔父子关系. ##开头
	private String groupName;
	// 分组说明, 第二个##后面的内容为分组说明.
	private String groupDesc;
	private String ip;
	// 一个IP如对应多个Host域名,之间以英文空格分隔
	// private String hosts;
	// 一个IP如对应多个Host域名,这些域名被拆分保存为List,每行一个域名.
	private List<String> hostList = new ArrayList<String>();
	// 单件Host的说明.
	private String hostDesc;
	// 原Host一行内容
	private String hostSrcTxt;

	public HostLine() {
	}

	public HostLine(String hostLineTxt) {
		// 如果是空白行,返回Null.
		if (StringUtils.isBlank(hostLineTxt))
			return;
		// 先过滤前后空格
		hostLineTxt = StringUtils.trimToEmpty(hostLineTxt);
		// 再判断第一个字符是不是注释符号#
		// 如果是## 则开始分析分组
		if (hostLineTxt.startsWith("##")) {
			lineToGroup(hostLineTxt);
		}
		// 否则就开始分析Host设置, 如果是一个#开头,则表示禁用了.
		else {
			lineToHost(hostLineTxt);
		}
	}

	/**
	 * 开始分析Host设置, 如果是以一个#号开头,表示这一行被注释掉了.即表示被禁用的Host设置.
	 * 
	 * @param hostLineTxt
	 */
	private void lineToHost(String hostLineTxt) {
		// TODO Auto-generated method stub
		this.setHostSrcTxt(hostLineTxt);
		if (hostLineTxt.startsWith("#")) {
			this.lineType = "#";
			hostLineTxt = hostLineTxt.substring(1); // 禁用
		}
		if (hostLineTxt != null && hostLineTxt.indexOf("#") > -1) {
			this.hostDesc = hostLineTxt.substring(hostLineTxt.indexOf("#") + 1); // Host备注
			hostLineTxt = hostLineTxt.substring(1, hostLineTxt.indexOf("#"));
		}
		if (StringUtils.isBlank(hostLineTxt))
			return;
		// TODO 先将分组信息保存一下:
		// 根据空格分隔.
		String[] hostIpStrs = StringUtils.splitByWholeSeparator(hostLineTxt,
				null);
		if (hostIpStrs.length > 0)
			this.ip = hostIpStrs[0];
		if (hostIpStrs.length >= 2) {
			for (int index = 1; index < hostIpStrs.length; index++) {
				this.hostList.add(hostIpStrs[index]);
				// this.hosts = this.hosts + " " + hostIpStrs[index];
			}
		}
	}

	/**
	 * 是## 则开始分析分组
	 * 
	 * @param hostLineTxt
	 */
	private void lineToGroup(String hostLineTxt) {
		this.setHostSrcTxt(hostLineTxt);
		this.lineType = "##"; // 分组信息行
		String[] groupDescStrs = StringUtils.splitByWholeSeparator(hostLineTxt,
				"##", 2);
		if (groupDescStrs.length >= 1)
			this.groupName = groupDescStrs[0].trim(); // 分组名称
		if (groupDescStrs.length >= 2)
			this.groupDesc = groupDescStrs[1].trim(); // 分组备注
	}

	public String toString() {
		return toLineTxt();
	}

	public String toLineTxt() {
		// 分组信息.
		if ("##".equals(this.getLineType())) {
			return this.getLineType() + this.getGroupName() + " ## "
					+ this.getGroupDesc();
		}
		// Host设置信息
		else
			return this.getLineType() + this.getIp() + " " + this.getHosts();
		// + " #" + this.getHostDesc();
	}

	public String getHosts() {
		if (this.getHostList() == null || this.getHostList().size() == 0)
			return "";
		else {
			StringBuilder sb = new StringBuilder();
			for (String host : this.getHostList()) {
				sb.append(host);
				sb.append(" ");
			}
			return sb.toString();
		}
	}

	// public void setHosts(String hosts) {
	// this.hosts = hosts;
	// }
	public String getGroupName() {
		return StringUtils.defaultIfBlank(groupName, "");
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return StringUtils.defaultIfBlank(groupDesc, "");
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getIp() {
		return StringUtils.defaultIfBlank(ip, "");
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public List<String> getHostList() {
		return hostList;
	}

	public void setHostList(List<String> hostList) {
		this.hostList = hostList;
	}

	public String getHostDesc() {
		return StringUtils.defaultIfBlank(hostDesc, "");
	}

	public void setHostDesc(String hostDesc) {
		this.hostDesc = hostDesc;
	}

	public String getLineType() {
		return StringUtils.defaultIfBlank(lineType, "");
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public String getHostSrcTxt() {
		return StringUtils.defaultIfBlank(hostSrcTxt, "");
	}

	public void setHostSrcTxt(String hostSrcTxt) {
		this.hostSrcTxt = hostSrcTxt;
	}

}
