package com.module.dao;

import com.module.bean.PriceRecord;

import java.util.List;

/**
 * Created by siqi.lou on 2017/6/5.
 */
public interface PriceRecordDao {
    void insert(PriceRecord or);
    List<PriceRecord> getAllPriceRecord(String code);
}
