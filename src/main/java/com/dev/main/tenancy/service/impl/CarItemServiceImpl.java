package com.dev.main.tenancy.service.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dev.main.common.util.JsonUtils;
import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.dao.TncCarItemMapper;
import com.dev.main.tenancy.domain.TncCarItem;
import com.dev.main.tenancy.service.ICarItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
public class CarItemServiceImpl implements ICarItemService {
    @Autowired
    private TncCarItemMapper tncCarItemMapper;

    @Override
    public int deleteCarItem(Integer id) {
        return tncCarItemMapper.updateDeleteFieldByPrimaryKey(id);
    }

    @Override
    public Page findCarItemListByCarId(QueryObject queryObject) {
        PageHelper.startPage((int)queryObject.get("page"),(int)queryObject.get("limit"),true);
        List<TncCarItem> list =tncCarItemMapper.getCarItemListByCarId(queryObject);
        PageInfo pageInfo = new PageInfo(list);

        return new Page(pageInfo.getTotal(), list);
    }

    @Override
    public int addCarItem(TncCarItem tncCarItem) {

        tncCarItem.setStatus((byte) 0);
        tncCarItem.setIsDeleted((byte) 0);
        tncCarItem.setGmtCreate(new Date());
        tncCarItem.setGmtModified(new Date());
        return tncCarItemMapper.insertSelective(tncCarItem);
    }

    @Override
    public int updateCarItem(TncCarItem tncCarItem) {
        tncCarItem.setGmtModified(new Date());
        return tncCarItemMapper.updateByPrimaryKeySelective(tncCarItem);
    }

    @Override
    public int updateCarItem(Integer id, Byte status) {
//        TncCarItem tncCarItem = new TncCarItem();
//        tncCarItem.setNumber(null);
//        tncCarItem.setCarId(Long.valueOf(id));
//        tncCarItem.setStatus(status);
//        tncCarItem.setGmtModified(null);
//        tncCarItem.setGmtCreate(null);

        //return tncCarItemMapper.updateByPrimaryKeySelective(tncCarItem);

        return tncCarItemMapper.updateCarItemStatus(id,status);
    }

    @Override
    public Page findCarItemListBySearch(QueryObject queryObject) {
        PageHelper.startPage((int)queryObject.get("page"),(int)queryObject.get("limit"),true);
        List<TncCarItem> list =tncCarItemMapper.getCarItemListBySearch(queryObject);
        PageInfo pageInfo = new PageInfo(list);

        return new Page(pageInfo.getTotal(), list);
    }

    @Transactional
    @Override
    public int addCarItemList(String dataList) {
        JSONArray json = JSONArray.parseArray(dataList);
        for(int i=0;i<json.size();i++){
            JSONObject job = json.getJSONObject(i);
            TncCarItem tncCarItem =JSONObject.toJavaObject(job,TncCarItem.class);
            tncCarItem.setStatus((byte) 0);
            tncCarItem.setIsDeleted((byte) 0);
            tncCarItem.setGmtCreate(new Date());
            tncCarItem.setGmtModified(new Date());
            tncCarItemMapper.insertSelective(tncCarItem);
        }
        return 0;
    }
    @Transactional
    @Override
    public int repairCarItemList(String dataList) {
        JSONArray json = JSONArray.parseArray(dataList);
        for(int i=0;i<json.size();i++){
            JSONObject job = json.getJSONObject(i);
            TncCarItem tncCarItem =JSONObject.toJavaObject(job,TncCarItem.class);
            tncCarItem.setGmtModified(new Date());
            tncCarItemMapper.updateCarItemStatus(new Integer(job.get("id").toString()) ,new Byte(job.get("status").toString()));
        }
        return 0;
    }
}