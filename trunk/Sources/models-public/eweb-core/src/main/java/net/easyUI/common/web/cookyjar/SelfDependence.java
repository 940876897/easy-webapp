package net.easyUI.common.web.cookyjar;


/**
 * 自力更生，能把自己持久成String对象以及从String对象重现建立起来
 * 
 */
public interface SelfDependence {
	public String lieDown();

	public SelfDependence riseUp(String value);
}
