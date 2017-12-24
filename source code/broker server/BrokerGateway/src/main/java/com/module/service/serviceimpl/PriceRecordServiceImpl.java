package com.module.service.serviceimpl;

import com.module.bean.PriceRecord;
import com.module.dao.PriceRecordDao;
import com.module.service.PriceRecordService;
import com.module.util.RedisServiceImpl;

import java.util.List;

/**
 * Created by siqi.lou on 2017/6/5.
 */
public class PriceRecordServiceImpl implements PriceRecordService {

    private PriceRecordDao priceRecordDao;

    public PriceRecordDao getPriceRecordDao() {
        return priceRecordDao;
    }

    public void setPriceRecordDao(PriceRecordDao priceRecordDao) {
        this.priceRecordDao = priceRecordDao;
    }

    public PriceRecord getNewPriceRecord(String code){
        RedisServiceImpl redisService = new RedisServiceImpl();
        PriceRecord p = redisService.getPriceRecord(code);
        if(p==null) {
            List<PriceRecord> list = priceRecordDao.getAllPriceRecord(code);
            if (list == null || list.size() == 0) {
                return null;
            }
            return list.get(0);
        }else {
            return p;
        }
    }

    @Override
    public void insert(PriceRecord pr) {
        priceRecordDao.insert(pr);
    }
}
