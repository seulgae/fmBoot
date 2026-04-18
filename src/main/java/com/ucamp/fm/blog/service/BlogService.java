package com.ucamp.fm.blog.service;

import com.ucamp.fm.blog.dto.BlogDto;
import com.ucamp.fm.cmt.dto.CmentDto;

import java.util.HashMap;
import java.util.List;

public interface BlogService {

    List<BlogDto> bloglist(HashMap<String, Object> map);

    int countBlogs(HashMap<String, Object> map);

    List<BlogDto> bloglistajax(BlogDto blogDto);

    BlogDto blogone(String td_no);

    void bloginsert(BlogDto blogDto);

    void blogdelete(String tb_no);

    void blogupdate(BlogDto blogDto);

    String getFile(String tb_no);

}
