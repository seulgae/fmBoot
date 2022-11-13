package com.ucamp.fm.service;

import com.ucamp.fm.dto.BlogDto;

import java.util.List;

public interface BlogService {

    List<BlogDto> bloglist(int pageNum);

    List<BlogDto> bloglistajax(BlogDto blogDto);

    BlogDto blogone(String td_no);

    void bloginsert(BlogDto blogDto);

    void blogdelete(String tb_no);

    void blogupdate(BlogDto blogDto);

}
