package com.ucamp.fm.mapper;

import com.ucamp.fm.dto.BlogDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogMapper {

    void bloginsert(BlogDto blogDto);

}
