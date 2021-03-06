package com.dev.main.tenancy.service.impl;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.tenancy.dao.TncCarItemMapper;
import com.dev.main.tenancy.dao.TncCarMapper;
import com.dev.main.tenancy.domain.TncCar;
import com.dev.main.tenancy.service.ICarService;
import com.dev.main.tenancy.vo.TncCarVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CarServiceImpl implements ICarService {

    @Autowired
    private TncCarMapper tncCarMapper;

    @Override
    public Page getCarList(QueryObject queryObject) {
        if(queryObject.get("orderField") == null ){
            queryObject.put("orderField","tnc_car.gmt_modified");
            queryObject.put("orderType","desc");
        }
        String ob = queryObject.get("orderField")+" "+queryObject.get("orderType");
        PageHelper.startPage((int)queryObject.get("page"),(int)queryObject.get("limit"),ob);
//        PageHelper.startPage((int)queryObject.get("page"),(int)queryObject.get("limit"),true);
        List<TncCarVo> list =tncCarMapper.getCarList(queryObject);
        PageInfo pageInfo = new PageInfo(list);
        return new Page(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public int addCar(TncCar tncCar) {
        tncCar.setStatus(new Byte("1"));
        tncCar.setGmtCreate(new Date());
        tncCar.setGmtModified(new Date());
        tncCar.setIsDeleted(new Byte("0"));
        tncCar.setQuantity(0);
        tncCar.setResidual(0);
        tncCar.setAccessTimes(new Long(0));
        return tncCarMapper.insert(tncCar);
    }

    @Override
    public TncCar getCarByCarId(Long carId) {
        return tncCarMapper.selectByPrimaryKey(carId);
    }

    @Override
    public int deleteCarByCarId(Long carId) {
        TncCar car = new TncCar();
        car.setGmtModified(new Date());
        car.setGmtCreate(null);
        car.setId(carId);
        car.setIsDeleted(new Byte("1"));

        return tncCarMapper.updateByPrimaryKeySelective(car);
    }

    @Override
    public int updateCar(TncCar tncCar) {

        tncCar.setGmtModified(new Date());
        return tncCarMapper.updateByPrimaryKeySelective(tncCar);
    }
}
