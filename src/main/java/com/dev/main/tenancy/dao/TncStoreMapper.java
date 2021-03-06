package com.dev.main.tenancy.dao;

import com.dev.main.tenancy.domain.TncStore;

import java.util.List;

public interface TncStoreMapper extends BaseMapper<TncStore> {
    int deleteByPrimaryKey(Long id);

    int insert(TncStore record);

    int insertSelective(TncStore record);

    TncStore selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TncStore record);

    int updateByPrimaryKey(TncStore record);

    List<TncStore> selectStoreByAreaId(Integer areaId);
}
