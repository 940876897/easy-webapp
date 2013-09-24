package net.easyUI.dto.common;

import java.util.List;
import java.util.Map;

import net.easyUI.common.dto.BaseDTO;

public class EuModel extends BaseDTO {

	private static final long serialVersionUID = 2296997254389424191L;
	// 模块group
	private String groupId;
	// 模块id
	private String artifactId;
	// 模块名称
	private String modelName;
	// 模块版本号(建议格式为: 客户号.一级版本号(产品级).二级版本(发布级).三级版本(测试级))
	private String version;
	// 模块说明
	private String modelDesc;
	// 依赖的模块列表.
	// <entry key="groupId" />
	// <entry key="artifactId" value="common" />
	// <entry key="version" />
	private List<Map<String, String>> dependencies;

	/**
	 * 初始化及设置属性完成时, 将本对象放入Ec2pModelCfg中缓存起来,以便其它代码加载使用,
	 * 
	 * @throws Exception
	 * @author hs-yuancong TODO 如果其它代码是在启动时要用Ec2pModelCfg,是否会存在启动顺序问题
	 * 
	 */

	public void afterPropertiesSet() throws Exception {
		EuModelCfg.putModel(this);
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelDesc() {
		return modelDesc;
	}

	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<Map<String, String>> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Map<String, String>> dependencies) {
		this.dependencies = dependencies;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

}
