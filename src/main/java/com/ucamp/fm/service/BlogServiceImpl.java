package com.ucamp.fm.service;

import com.ucamp.fm.dto.BlogDto;
import com.ucamp.fm.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    BlogMapper blogMapper;


    @Override
    public void bloginsert(BlogDto blogDto) {
        blogMapper.bloginsert(blogDto);
    }
}
