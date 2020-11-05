package com.bjfu.wordlates.dao;

import com.bjfu.wordlates.entity.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReportDao {
    @Select({"SELECT id, " +
            "food_name, " +
            "inspection_institute_name, " +
            "company_name, " +
            "testing_institute_name" +
            " FROM report WHERE id = #{id}"})
    Report selectById(@Param("id") Long id);

}
