package com.ruyicai.analyze.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruyicai.analyze.domain.WinData;

@Service("windataService")
@Transactional
public class WindataService {

	private static Logger logger = LoggerFactory
			.getLogger(WindataService.class);

	@PersistenceContext
	EntityManager em;

	public void merge(WinData windata) {
		em.merge(windata);
	}

	
	@Transactional(readOnly = true)
	public List<WinData> find(String lotno,int start,int end) {
		List<WinData> list = em
				.createQuery(
						"select o from WinData o where o.lotno=? order by id desc",
						WinData.class).setParameter(1, lotno).setFirstResult(start)
				.setMaxResults(end).getResultList();
		return list;
	}

}
