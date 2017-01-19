package com.revature.sms.domain.dto;

public class bc {
	private boolean update;

	public bc(boolean update) {
		super();
		this.update = update;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	@Override
	public String toString() {
		return "bc [update=" + update + "]";
	}
	
}
