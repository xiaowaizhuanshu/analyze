package com.ruyicai.analyze.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruyicai.analyze.domain.Tlot;

@Service("tlotService")
@Transactional
public class TlotService {

	private static Logger logger = LoggerFactory.getLogger(TlotService.class);

	@PersistenceContext
	EntityManager em;

	public void merge(Tlot tlot) {
		em.merge(tlot);
	}

	
	@Transactional(readOnly = true)
	public List<Tlot> find(String lotno, String batchcode, int start, int step) {
		List<Tlot> list = em
				.createQuery(
						"select o from Tlot o where o.lotno=? and o.batchcode=?",
						Tlot.class).setParameter(1, lotno).setParameter(2, batchcode)
				.setFirstResult(start).setMaxResults(step).getResultList();
		return list;
	}
	

}
