package com.ssafy.vue.model.service;

import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.mapper.BoardMapper;
import com.ssafy.vue.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		if(memberDto.getUserid() == null || memberDto.getUserpwd() == null)
			return null;
		return sqlSession.getMapper(MemberMapper.class).login(memberDto);
	}

	@Override
	public MemberDto userInfo(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).userInfo(userid);
	}

	@Override
	public List<MemberDto> findAll(String userid) throws Exception {
		List<MemberDto> users = sqlSession.getMapper(MemberMapper.class).findAll(userid);
    	Collections.sort(users);
    	return users;
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean setMember(MemberDto requestDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).setMember(requestDto) == 1;
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(String userid) throws Exception {
        return sqlSession.getMapper(MemberMapper.class).delete(userid) == 1;
    }

	@Override
    public String findPw(String userid) throws Exception {
    	if(userid == null)
    		return null;
    	
        return sqlSession.getMapper(MemberMapper.class).findPw(userid);
    }


	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean join(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).join(memberDto) == 1;
	}

	@Override
	public List<MemberDto> listMember() {
		return sqlSession.getMapper(MemberMapper.class).listMember();
	}

}
