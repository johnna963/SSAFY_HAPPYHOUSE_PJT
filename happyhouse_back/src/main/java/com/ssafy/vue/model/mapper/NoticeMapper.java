package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.NoticeDto;
import com.ssafy.vue.model.NoticeParameterDto;


@Mapper
public interface NoticeMapper {
	
	public int writeArticle(NoticeDto noticeDto) throws SQLException;
	public List<NoticeDto> listArticle(NoticeParameterDto noticeParameterDto) throws SQLException;
	public int getTotalCount(NoticeParameterDto noticeParameterDto) throws SQLException;
	public NoticeDto getArticle(int articleno) throws SQLException;
	public void updateHit(int articleno) throws SQLException;
	public int modifyArticle(NoticeDto NoticeDto) throws SQLException;
	public int deleteArticle(int articleno) throws SQLException;
	
}