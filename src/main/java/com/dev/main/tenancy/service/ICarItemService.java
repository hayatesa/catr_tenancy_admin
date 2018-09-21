package com.dev.main.tenancy.service;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.domain.TncCarItem;

public interface ICarItemService {
    Page findCarItemListByCarId(QueryObject queryObject);

    int addCarItem(TncCarItem tncCarItem);

    int deleteCarItem(Integer id);

    int updateCarItem(Integer id, Byte status);

    int updateCarItem(TncCarItem tncCarItem);

    Page findCarItemListBySearch(QueryObject queryObject);

    int addCarItemList(String dataList);

    int repairCarItemList(String dataList);
}