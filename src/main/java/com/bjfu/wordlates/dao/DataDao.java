package com.bjfu.wordlates.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface DataDao {
    @Update({"CREATE TABLE ${tableName} (" +
                    "  id bigint(0) NOT NULL AUTO_INCREMENT," +
                    "  cydd2 varchar(255) NULL," +
                    "  sheng varchar(255) NULL," +
                    "  diqu varchar(255) NULL," +
                    "  xiang varchar(255) NULL," +
                    "  qylx varchar(255) NULL," +
                    "  ypmc varchar(255) NULL," +
                    "  spdl varchar(255) NULL," +
                    "  spyl varchar(255) NULL," +
                    "  spcyl varchar(255) NULL," +
                    "  spxl varchar(255) NULL," +
                    "  ypxt varchar(255) NULL," +
                    "  scrq varchar(255) NULL," +
                    "  jyxm varchar(255) NULL," +
                    "  jgpd varchar(255) NULL," +
                    "  cydd1 varchar(255) NULL," +
                    "  PRIMARY KEY (`id`)" +
                    ")"})
    boolean creatTable(@Param("tableName") String name);

}
