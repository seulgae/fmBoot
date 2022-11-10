package com.ucamp.fm.service;

import com.ucamp.fm.dto.BlogDto;
import com.ucamp.fm.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    BlogMapper blogMapper;


    @Override
    public List<BlogDto> bloglist(BlogDto blogDto) {
        return blogMapper.bloglist(blogDto);
    }

    @Override
    public List<BlogDto> bloglistajax(BlogDto blogDto) {
        return blogMapper.bloglistajax(blogDto);
    }

    @Override
    public void bloginsert(BlogDto blogDto) {
        blogMapper.bloginsert(blogDto);
    }

    @Override
    public void blogdelete() {

    }
}
