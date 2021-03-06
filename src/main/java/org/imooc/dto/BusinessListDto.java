package org.imooc.dto;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class BusinessListDto {
	
	private boolean hasMore;
	private List<BusinessDto> data;
	
	public BusinessListDto(){
		this.data=new ArrayList<>();
	}
	
	public boolean isHasMore() {
		return hasMore;
	}
	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
	public List<BusinessDto> getData() {
		return data;
	}
	public void setData(List<BusinessDto> data) {
		this.data = data;
	}
	
	
}
