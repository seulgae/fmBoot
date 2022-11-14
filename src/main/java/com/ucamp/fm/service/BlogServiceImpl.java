package com.ucamp.fm.service;

import com.ucamp.fm.dto.BlogDto;
import com.ucamp.fm.dto.CmentDto;
import com.ucamp.fm.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    BlogMapper blogMapper;


    @Override
    public List<BlogDto> bloglist(HashMap<String, Object> map) {
       return  blogMapper.bloglist(map);
    }

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

    @Override
    public void blogupdate(BlogDto blogDto) {
        blogMapper.blogupdate(blogDto);
    }

    @Override
    public void commentinsert(String c_c_id, String c_content) {
        blogMapper.commentinsert(c_c_id, c_content);
    }

    @Override
    public List<CmentDto> cmtlist(CmentDto cmentDto) {
        return blogMapper.cmtlist(cmentDto);
    }


}
