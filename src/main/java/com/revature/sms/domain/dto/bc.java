package com.revature.sms.domain.dto;

import java.util.List;

import com.revature.sms.domain.Status;

public class bc {
	private boolean update;
	
	private List<Status> status;

	public bc(boolean update) {
		super();
		this.update = update;
	}
	
	public bc(List<Status> l){
		this.status = l;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	
	
	public List<Status> getStatus() {
		return status;
	}

	public void setStatus(List<Status> status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "bc [update=" + update + "]";
	}
	
}
