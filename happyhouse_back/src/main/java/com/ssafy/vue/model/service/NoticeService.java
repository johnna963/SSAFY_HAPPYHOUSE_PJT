package com.ssafy.vue.model.service;

import java.util.List;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.NoticeDto;
import com.ssafy.vue.model.NoticeParameterDto;

public interface NoticeService {
	public boolean writeArticle(NoticeDto noticeDto) throws Exception;
	public List<NoticeDto> listArticle(NoticeParameterDto noticeParameterDto) throws Exception;
	public PageNavigation makePageNavigation(NoticeParameterDto noticeParameterDto) throws Exception;
	
	public NoticeDto getArticle(int articleno) throws Exception;
	public void updateHit(int articleno) throws Exception;
	public boolean modifyArticle(NoticeDto noticeDto) throws Exception;
	public boolean deleteArticle(int articleno) throws Exception;
}
