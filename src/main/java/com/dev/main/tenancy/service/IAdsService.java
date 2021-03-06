package com.dev.main.tenancy.service;

import com.dev.main.common.util.Page;
import com.dev.main.common.util.QueryObject;
import com.dev.main.common.util.ResultMap;
import com.dev.main.tenancy.domain.TncAds;

/**
 *  * Description: catr_tenancy_admin
 *  * Created by sf on 2018/9/14 9:07
 *  
 */
public interface IAdsService {

    /**
     * 查询一页数据
     * @param queryObject
     * @return
     */
    Page queryByPage(QueryObject queryObject);
    /**
     * 增加一条记录
     * @param tncAds
     * @return
     */
    ResultMap addAds(TncAds tncAds);
    /**
     * 修改一条记录
     * @param tncAds
     * @return
     */
    ResultMap updateAds(TncAds tncAds);
    /**
     * 删除一条记录
     * @param tncAds
     * @return
     */
    ResultMap deleteAds(TncAds tncAds);
    /**
     * 查询一条记录
     * @param id
     * @return
     */
    ResultMap selectById(Long id);
}
