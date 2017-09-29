package org.imooc.dao;

import java.util.List;

import org.imooc.bean.Member;

public interface MemberDao {
	/**
	 * 根据条件查询
	 */
	List<Member> select(Member member);
}
