package com.dev.main.tenancy.dao;

import com.dev.main.tenancy.domain.TncAddress;
import org.apache.ibatis.annotations.Param;

public interface TncAddressMapper extends BaseMapper<TncAddress> {
    int deleteByPrimaryKey(Long id);

    int insert(TncAddress record);

    int insertSelective(TncAddress tncAddress);

    TncAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TncAddress record);

    int updateByPrimaryKey(TncAddress record);
}
