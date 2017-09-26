package org.imooc.bean;

import java.util.List;

public class BusinessList {
	
	private boolean hasMore;
	private List<Business> data;

	public List<Business> getBusinessList() {
		return data;
	}

	public void setBusinessList(List<Business> data) {
		this.data = data;
	}

	public boolean isHasMore() {
		return hasMore;
	}

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
	
}
