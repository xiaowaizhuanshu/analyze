package com.ruyicai.analyze.service;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.rubyeye.xmemcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruyicai.analyze.domain.StatisticsData;

@Service("statisticsDataService")
@Transactional
@Cacheable
public class StatisticsDataService {

	private static Logger logger = LoggerFactory.getLogger(StatisticsDataService.class);
	@PersistenceContext
	private EntityManager em;

	@Autowired
	MemcachedClient memcachedClient;

	/**
	 * 插入或者更新一个StatisticsData
	 * @param statisticsData
	 */
	public void persist(StatisticsData statisticsData) {
		em.persist(statisticsData);
	}


	@Transactional(readOnly = true)
	public StatisticsData findLatest(String lotno, String key) {
		StatisticsData statisticsData = em
				.createQuery(
						"select o from StatisticsData o where o.id.lotno=? and o.id.name=? order by batchcode desc",
						StatisticsData.class).setParameter(1, lotno)
				.setParameter(2, key).setFirstResult(0).setMaxResults(1)
				.getSingleResult();
		return statisticsData;
	}
	
	
	@Transactional(readOnly = true)
	public StatisticsData findLatestOrByBatchcode(String lotno, String key,String batchcode) {
		StatisticsData statisticsData;
		if(batchcode==null) {
			statisticsData = findLatest(lotno,key);
		}else {
			statisticsData = findByLotnoAndBatchcodeAndKey(lotno,batchcode,key);
		}
		
		return statisticsData;
	}
	

	public void merge(StatisticsData statisticsData) {
		em.merge(statisticsData);
	}

	


	
	@Transactional(readOnly = true)
	public StatisticsData findByLotnoAndBatchcodeAndKey(String lotno,
			String batchcode, String key) {
		List<StatisticsData> list = em
				.createQuery(
						"select o from StatisticsData o where o.id.lotno=? and o.id.name=? and o.id.batchcode=?",StatisticsData.class)
				.setParameter(1, lotno).setParameter(2, key)
				.setParameter(3, batchcode).getResultList();
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	
	
	@Transactional(readOnly = true)
	public StatisticsData findByLotnoAndBatchcodeAndKeyThroughCache(String lotno,
			String batchcode, String key) {
		
		StatisticsData stadata;
		
		try {
			stadata = memcachedClient.get(lotno+key+"_"+batchcode);
			if(stadata==null) {
				stadata = findByLotnoAndBatchcodeAndKey(lotno, batchcode, key);
				if(stadata!=null) {
					memcachedClient.add(lotno+key+"_"+batchcode, 86400, stadata);
				}
			}
			return stadata;
			
		} catch (Exception e) {
			logger.info("findByLotnoAndBatchcodeAndKeyThroughCache err",e);
			return null;
		}

	}
	
	
	@Transactional(readOnly = true)
	public List<StatisticsData> findListByLotnoAndKey(String lotno,String key,int length) {
		List<StatisticsData> list = em
		.createQuery(
				"select o from StatisticsData o where o.id.lotno=? and o.id.name=? order by batchcode desc",StatisticsData.class)
		.setParameter(1, lotno).setParameter(2, key)
		.setFirstResult(0).setMaxResults(length).getResultList();

		return list;
	}
	
	
	

}
