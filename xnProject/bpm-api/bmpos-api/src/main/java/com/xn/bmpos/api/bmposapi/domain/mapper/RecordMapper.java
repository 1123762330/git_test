package com.xn.bmpos.api.bmposapi.domain.mapper;

import com.xn.bmpos.api.bmposapi.domain.model.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RecordMapper {

    List<Record> findCollectionAllRecord (String address);

    List<Record> findPayRecord ();
}
