package com.ruyicai.analyze.statisticswin;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ruyicai.analyze.domain.WinData;
import com.ruyicai.analyze.statisticswin.latest.WinLatestService;

@Service("statisticswinService")
public class StatisticswinService {

	@Resource
	private WinLatestService winLatestService;
	
	public void enCash(WinData windata) {
		winLatestService.enCash(windata);
	}
	
	public void refresh(String lotno) {
		winLatestService.refresh(lotno);
	}
	
}
