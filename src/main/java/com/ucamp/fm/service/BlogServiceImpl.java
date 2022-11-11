package com.ucamp.fm.service;

import com.ucamp.fm.dto.BlogDto;
import com.ucamp.fm.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    BlogMapper blogMapper;


    @Override
    public List<BlogDto> bloglistajax(BlogDto blogDto) {
        return blogMapper.bloglistajax(blogDto);
    }

    @Override
    public BlogDto blogone(String td_no) {
        return blogMapper.blogone(td_no);
    }

    @Override
    public void bloginsert(BlogDto blogDto) {
        blogMapper.bloginsert(blogDto);
    }

    @Override
    public void blogdelete(String tb_no) {
        blogMapper.blogdelete(tb_no);
    }

}
