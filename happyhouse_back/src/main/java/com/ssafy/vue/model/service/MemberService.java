package com.ssafy.vue.model.service;

import java.util.List;

import com.ssafy.vue.model.MemberDto;

public interface MemberService {

	public boolean join(MemberDto memberDto) throws Exception;
	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
    public List<MemberDto> findAll(String userId) throws Exception;
    public boolean setMember(MemberDto requestDto) throws Exception;
    public boolean delete(String userId) throws Exception;
    public String findPw(String userId) throws Exception;
	public List<MemberDto> listMember();
	
}
