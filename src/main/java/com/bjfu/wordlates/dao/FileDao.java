package com.bjfu.wordlates.dao;

import com.bjfu.wordlates.entity.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface FileDao {
    @Insert({ "insert into file(name, md5, path, upload_time) values(#{name}, #{md5}, #{path}, #{uploadTime, jdbcType=TIMESTAMP})" })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(File file);
}
