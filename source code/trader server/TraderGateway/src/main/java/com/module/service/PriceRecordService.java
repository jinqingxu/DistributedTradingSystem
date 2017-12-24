package com.module.service;

import com.module.bean.PriceRecord;

/**
 * Created by siqi.lou on 2017/6/5.
 */
public interface PriceRecordService {
    PriceRecord getNewPriceRecord(String code,String broker);
    void insert(PriceRecord pr);

}
