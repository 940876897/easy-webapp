package net.easyUI.dto.common;

import java.util.HashMap;
import java.util.Map;

import net.easyUI.common.dto.BaseDTO;

import org.apache.commons.lang.StringUtils;

public class EuModelCfg extends BaseDTO {

	private static final long serialVersionUID = 6374269031785646403L;

	public static Map<String, EuModel> Ec2pModelMap = new HashMap<String, EuModel>();

	public static EuModel getModel(String getArtifactId) {
		return Ec2pModelMap.get(getArtifactId);
	}

	public static void putModel(EuModel model) {
		if (model != null && StringUtils.isNotBlank(model.getArtifactId()))
			Ec2pModelMap.put(model.getArtifactId().trim(), model);
	}
}
