package com.ucamp.fm.mapper;

import com.ucamp.fm.dto.ImageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PlaceMapper {

    public void insertImage(ImageDto imageDto);

    public String getSeq();
    @Select("select i_fname from image where i_no = #{i_no}")
    public String getFname(String i_no);

}
