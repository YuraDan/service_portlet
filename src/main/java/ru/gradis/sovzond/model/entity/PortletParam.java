package ru.gradis.sovzond.model.entity;

/**
 * Created by donchenko-y on 7/15/16.
 */
public class PortletParam {

	private String PORTLET_ID;
	private String LAYOUT_ID;
	private long CURRENT_PORTAL_USER_ID;

	public String getPORTLET_ID() {
		return PORTLET_ID;
	}

	public void setPORTLET_ID(String PORTLET_ID) {
		this.PORTLET_ID = PORTLET_ID;
	}

	public String getLAYOUT_ID() {
		return LAYOUT_ID;
	}

	public void setLAYOUT_ID(String LAYOUT_ID) {
		this.LAYOUT_ID = LAYOUT_ID;
	}

	public long getCURRENT_PORTAL_USER_ID() {
		return CURRENT_PORTAL_USER_ID;
	}

	public void setCURRENT_PORTAL_USER_ID(long CURRENT_PORTAL_USER_ID) {
		this.CURRENT_PORTAL_USER_ID = CURRENT_PORTAL_USER_ID;
	}

	public PortletParam() {
		this.PORTLET_ID = "UNDEFIND";
		this.LAYOUT_ID = "UNDEFIND";
		this.CURRENT_PORTAL_USER_ID = -1;
	}
}
