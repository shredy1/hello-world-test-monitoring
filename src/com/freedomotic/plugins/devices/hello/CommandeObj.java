package com.freedomotic.plugins.devices.hello;

import java.io.Serializable;

public class CommandeObj implements Serializable{


	private static final long serialVersionUID = 1L;
	public String tc ;
	public String mode;
	
	public String getTc() {
		return tc;
	}
	public void setTc(String tc) {
		this.tc = tc;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
}
