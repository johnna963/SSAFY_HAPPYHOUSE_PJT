package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.MemberDto;

@Mapper
public interface MemberMapper {

	public MemberDto login(MemberDto memberDto) throws SQLException;
	public MemberDto userInfo(String userid) throws SQLException;
	public int join(MemberDto memberDto) throws Exception;
	public List<MemberDto> findAll(String userId) throws Exception;
    public int setMember(MemberDto requestDto) throws Exception;
    public int delete(String userId) throws Exception;
    public String findPw(String userId) throws Exception;
	public List<MemberDto> listMember();
	
}
