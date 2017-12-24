package com.module.service.serviceimpl;

import com.module.bean.PriceRecord;
import com.module.dao.PriceRecordDao;
import com.module.service.PriceRecordService;
import com.module.util.RedisService;
import com.module.util.RedisServiceImpl;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by siqi.lou on 2017/6/5.
 */
public class PriceRecordServiceImpl implements PriceRecordService{

    private PriceRecordDao priceRecordDao;

    public PriceRecordDao getPriceRecordDao() {
        return priceRecordDao;
    }

    public void setPriceRecordDao(PriceRecordDao priceRecordDao) {
        this.priceRecordDao = priceRecordDao;
    }

    public PriceRecord getNewPriceRecord(String code,String broler){
        RedisServiceImpl redisService = new RedisServiceImpl();
        PriceRecord p = redisService.getPriceRecord(code,broler);
        if(p==null) {
            List<PriceRecord> list = priceRecordDao.getAllPriceRecord(code,broler);
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
