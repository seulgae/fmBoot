package com.ucamp.fm.service;

import com.ucamp.fm.dto.BlogDto;
import com.ucamp.fm.dto.CmentDto;

import java.util.HashMap;
import java.util.List;

public interface BlogService {

    List<BlogDto> bloglist(HashMap<String, Object> map);

    List<BlogDto> bloglistajax(BlogDto blogDto);

    BlogDto blogone(String td_no);

    void bloginsert(BlogDto blogDto);

    void blogdelete(String tb_no);

    void blogupdate(BlogDto blogDto);

}
