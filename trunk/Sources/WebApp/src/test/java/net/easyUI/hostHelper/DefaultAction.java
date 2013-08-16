package net.easyUI.hostHelper;

import java.util.List;

public class DefaultAction {
	private HostManager hostManager;

	public DefaultAction() {
		this.setHostManager(new HostManager());
	}

	public void show() {
		String hostText = hostManager.showDefaultHost();
		System.out.println(hostText);
	}

	public void save() {
		// Window
		String fileName = "C:/Windows/System32/drivers/etc/hosts";
		// Linux
		// String fileName = "/etc/hosts";
		List<HostLine> hostLines = hostManager.readHostFile(fileName);
		hostManager.writeHostFile("d:/host.txt", hostLines);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DefaultAction defaultAction = new DefaultAction();
		defaultAction.save();
//		defaultAction.show();

	}

	public HostManager getHostManager() {
		return hostManager;
	}

	public void setHostManager(HostManager hostManager) {
		this.hostManager = hostManager;
	}

}
