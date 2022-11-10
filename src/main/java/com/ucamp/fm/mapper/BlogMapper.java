package com.ucamp.fm.mapper;

import com.ucamp.fm.dto.BlogDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper {

    List<BlogDto> bloglist(BlogDto blogDto);

    List<BlogDto> bloglistajax(BlogDto blogDto);

    void bloginsert(BlogDto blogDto);

    void blogdelete();

}
