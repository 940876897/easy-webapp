package net.easyUI.hostHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class HostManager {
	private String encoding = "utf-8";

	public String showDefaultHost() {
		// Window
		String fileName = "C:/Windows/System32/drivers/etc/hosts";
		// Linux
		// String fileName = "/etc/hosts";
		List<HostLine> hostLines = readHostFile(fileName);
		StringBuilder sb = new StringBuilder();
		for (HostLine hostLine : hostLines) {
			if (hostLine.getLineType().equals("##"))
				sb.append("\n\n");
			sb.append(hostLine.toLineTxt());
			sb.append("\n");
		}
		return sb.toString();
	}

	public String writeHostFile(String fileName, List<HostLine> hostLines) {
		try {
			FileUtils.writeLines(new File(fileName), encoding, hostLines);
		} catch (IOException e) {
			e.printStackTrace();
			return "writeHostFile error!";
		}
		return "";
	}

	public List<HostLine> readHostFile(String fileName) {
		List<HostLine> hostLines = new ArrayList<HostLine>();
		try {
			List<String> lineList = FileUtils.readLines(new File(fileName),
					encoding);
			if (CollectionUtils.isNotEmpty(lineList)) {
				for (String lineStr : lineList) {
					if (!StringUtils.isBlank(lineStr)) {
						// 将本行转换成Host对象并放到对应分组.
						HostLine hostLine = new HostLine(lineStr);
						hostLines.add(hostLine);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hostLines;
	}
}
