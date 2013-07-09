package com.ruyicai.analyze.betpartition.zucai;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruyicai.analyze.domain.Tlot;
import com.ruyicai.analyze.util.BaseMath;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class BetPartitionData {
	
	
	public static final String KEY = "BetPartition";

	List<Integer[]> list = new ArrayList<Integer[]>();

	public List<Integer[]> getList() {
		return list;
	}

	public void setList(List<Integer[]> list) {
		this.list = list;
	}
	
	
	
	
	public BetPartitionData getEmptyBetPartionData(String lotno) { 
		if (lotno.equals("T01003") || lotno.endsWith("T01004")) {
			for (int i = 0; i < 14; i++) {
				list.add(new Integer[] { 0, 0, 0 });
			}
		}
		if (lotno.equals("T01005")) {
			for (int i = 0; i < 8; i++) {
				list.add(new Integer[] { 0, 0, 0, 0 });
			}
		}
		if (lotno.equals("T01006")) {
			for (int i = 0; i < 12; i++) {
				list.add(new Integer[] { 0, 0, 0 });
			}
		}
		return this;
	}
	
	
	
	
	public void onSuccess(Tlot tlot) {
		if(tlot.getLotno().equals("T01003")) {
			betcodePartition3(tlot);
		}else if(tlot.getLotno().equals("T01004")) {
			betcodePartition4(tlot);
		}else if(tlot.getLotno().equals("T01005")) {
			betcodePartition5(tlot);
		}else if(tlot.getLotno().equals("T01006")) {
			betcodePartition6(tlot);
		}
		
	}
	
	private void betcodePartition3(Tlot tlot) {
		String[] bets = new String[14];
		char[] betChars = new char[14];
		for(String bet:tlot.getBetcode().split(";")) {
			
			if(tlot.getBetcode().contains(",")) {
				bets = bet.split(",");
				char[] betChars2 = new char[3];
				for(int i = 0;i < 14 ;i++) {
					betChars2 = bets[i].toCharArray();
					for(int j = 0;j<betChars2.length;j++) {
						if(betChars2[j]=='0') {
							list.get(i)[0] = list.get(i)[0] + tlot.getLotmulti().intValue();
						}else if(betChars2[j]=='1') {
							list.get(i)[1] = list.get(i)[1] + tlot.getLotmulti().intValue();
						}else if(betChars2[j]=='3') {
							list.get(i)[2] = list.get(i)[2] + tlot.getLotmulti().intValue();
						}
					}
					
				}
			}else {
				betChars = bet.toCharArray();
				for(int i = 0;i < 14 ;i++) {
					if(betChars[i]=='0') {
						list.get(i)[0] = list.get(i)[0] + tlot.getLotmulti().intValue();
					}else if(betChars[i]=='1') {
						list.get(i)[1] = list.get(i)[1] + tlot.getLotmulti().intValue();
					}else if(betChars[i]=='3') {
						list.get(i)[2] = list.get(i)[2] + tlot.getLotmulti().intValue();
					}
					
				}
			}
		}
	}
	
	private void betcodePartition4(Tlot tlot) {
		String betcode = tlot.getBetcode();
		int danCount = 0;
		int tuoCount = 0;
		Map<String,Integer> map = new HashMap<String,Integer>();
		boolean isDantuo = tlot.getBetcode().contains("$");
		
		//如果是胆拖玩法，计算出胆码和托码的位置，放到map里，计算出胆码和托码不为#的个数(danCount,tuoCount),将胆码托码合并到一起
		if(betcode.contains("$")) {
			String newBetcode = "";
			for(String bet:betcode.split(";")) {
				String dan,tuo;
				String[] dans,tuos;
				if(bet.contains("$")) {
					dan = betcode.split("\\$")[0];
					tuo = betcode.split("\\$")[1];
					dans = dan.split(",");
					tuos = tuo.split(",");
					for(int i = 0,j=dans.length;i<j;i++) {
						if(dans[i].equals("#")) {
							//极端托码个数
							if(!tuos[i].equals("#")) {
								tuoCount = tuoCount + 1;
							}
							//计算托码位置
							map.put(i+"", 0);
							dans[i] = tuos[i];
						}else {
							//计算胆码个数和位置
							danCount = danCount + 1; 
							map.put(i+"", 1);
						}
					}
					
					for(String s : dans) {
						newBetcode = newBetcode + s + ",";
					}
					newBetcode = newBetcode + ";";
				}else {
					newBetcode = newBetcode + ";";
				}
			}
			betcode = newBetcode;
		}
		
		
		String[] bets = new String[14];
		char[] betChars = new char[14];
		for(String bet:betcode.split(";")) {
			if(betcode.contains(",")) {
				bets = bet.split(",");
				char[] betChars2 = new char[3];
				
				int count = 0;
				for(String s:bets) {
					if(s.equals("#"))
						count++;
				}
				//单式
				if(count==5) {
					for(int i = 0;i < 14 ;i++) {
						betChars2 = bets[i].toCharArray();
						for(int j = 0;j<betChars2.length;j++) {
							if(betChars2[j]=='0') {
								list.get(i)[0] = list.get(i)[0] + tlot.getLotmulti().intValue();
							}else if(betChars2[j]=='1') {
								list.get(i)[1] = list.get(i)[1] + tlot.getLotmulti().intValue();
							}else if(betChars2[j]=='3') {
								list.get(i)[2] = list.get(i)[2] + tlot.getLotmulti().intValue();
							}
						}
					}
				}else if(count<5&&(isDantuo==false)) {//转九
					long enlarge = BaseMath.nchoosek(14-count-1, 8);
					for(int i = 0;i < 14 ;i++) {
						betChars2 = bets[i].toCharArray();
						for(int j = 0;j<betChars2.length;j++) {
							if(betChars2[j]=='0') {
								list.get(i)[0] = (int) (list.get(i)[0] + tlot.getLotmulti().intValue()*enlarge);
							}else if(betChars2[j]=='1') {
								list.get(i)[1] = (int) (list.get(i)[1] + tlot.getLotmulti().intValue()*enlarge);
							}else if(betChars2[j]=='3') {
								list.get(i)[2] = (int) (list.get(i)[2] + tlot.getLotmulti().intValue()*enlarge);
							}
						}
					}
				}else {//胆拖
					long danLarge = BaseMath.nchoosek(tuoCount, 9-danCount);
					long tuoLarge = BaseMath.nchoosek(tuoCount, 9-danCount-1);
					long large = 0;
					for(int i = 0;i < 14 ;i++) {
						betChars2 = bets[i].toCharArray();
						if(map.get(""+i)==1) {
							large = danLarge;
						}else {
							large = tuoLarge;
						}
						for(int j = 0;j<betChars2.length;j++) {
							if(betChars2[j]=='0') {
								list.get(i)[0] = (int) (list.get(i)[0] + tlot.getLotmulti().intValue()*large);
							}else if(betChars2[j]=='1') {
								list.get(i)[1] = (int) (list.get(i)[1] + tlot.getLotmulti().intValue()*large);
							}else if(betChars2[j]=='3') {
								list.get(i)[2] = (int) (list.get(i)[2] + tlot.getLotmulti().intValue()*large);
							}
						}
					}
				}
				
				
			}else {
				betChars = bet.toCharArray();
				for(int i = 0;i < 14 ;i++) {
					if(betChars[i]=='0') {
						list.get(i)[0] = list.get(i)[0] + tlot.getLotmulti().intValue();
					}else if(betChars[i]=='1') {
						list.get(i)[1] = list.get(i)[1] + tlot.getLotmulti().intValue();
					}else if(betChars[i]=='3') {
						list.get(i)[2] = list.get(i)[2] + tlot.getLotmulti().intValue();
					}
				}
			}
		}
	}
	
	private void betcodePartition5(Tlot tlot) {
		String betcode = tlot.getBetcode();
		String[] bets = new String[8];
		char[] betChars = new char[8];
		for(String bet:betcode.split(";")) {
			if(betcode.contains(",")) {
				bets = bet.split(",");
				char[] betChars2 = new char[4];
				for(int i = 0;i < 8 ;i++) {
					betChars2 = bets[i].toCharArray();
					for(int j = 0;j<betChars2.length;j++) {
						if(betChars2[j]=='0') {
							list.get(i)[0] = list.get(i)[0] + tlot.getLotmulti().intValue();
						}else if(betChars2[j]=='1') {
							list.get(i)[1] = list.get(i)[1] + tlot.getLotmulti().intValue();
						}else if(betChars2[j]=='2') {
							list.get(i)[2] = list.get(i)[2] + tlot.getLotmulti().intValue();
						}else if(betChars2[j]=='3') {
							list.get(i)[3] = list.get(i)[3] + tlot.getLotmulti().intValue();
						}
					}
				}
			}else {
				betChars = bet.toCharArray();
				for(int i = 0;i < 8 ;i++) {
					if(betChars[i]=='0') {
						list.get(i)[0] = list.get(i)[0] + tlot.getLotmulti().intValue();
					}else if(betChars[i]=='1') {
						list.get(i)[1] = list.get(i)[1] + tlot.getLotmulti().intValue();
					}else if(betChars[i]=='2') {
						list.get(i)[2] = list.get(i)[2] + tlot.getLotmulti().intValue();
					}else if(betChars[i]=='3') {
						list.get(i)[3] = list.get(i)[3] + tlot.getLotmulti().intValue();
					}
				}
			}
		}
	}
	
	private void betcodePartition6(Tlot tlot) {
		String betcode = tlot.getBetcode();
		String[] bets = new String[12];
		char[] betChars = new char[12];
		for(String bet:betcode.split(";")) {
			if(betcode.contains(",")) {
				bets = bet.split(",");
				char[] betChars2 = new char[3];
				for(int i = 0;i < 12 ;i++) {
					betChars2 = bets[i].toCharArray();
					for(int j = 0;j<betChars2.length;j++) {
						if(betChars2[j]=='0') {
							list.get(i)[0] = list.get(i)[0] + tlot.getLotmulti().intValue();
						}else if(betChars2[j]=='1') {
							list.get(i)[1] = list.get(i)[1] + tlot.getLotmulti().intValue();
						}else if(betChars2[j]=='3') {
							list.get(i)[2] = list.get(i)[2] + tlot.getLotmulti().intValue();
						}
					}
				}
			}else {
				betChars = bet.toCharArray();
				for(int i = 0;i < 12 ;i++) {
					if(betChars[i]=='0') {
						list.get(i)[0] = list.get(i)[0] + tlot.getLotmulti().intValue();
					}else if(betChars[i]=='1') {
						list.get(i)[1] = list.get(i)[1] + tlot.getLotmulti().intValue();
					}else if(betChars[i]=='3') {
						list.get(i)[2] = list.get(i)[2] + tlot.getLotmulti().intValue();
					}
				}
			}
		}
	}
	
	

	public String toJson() {
		return new JSONSerializer().exclude("*.class").deepSerialize(this);
	}

	public static String toJsonArray(Collection<BetPartitionData> collection) {
		return new JSONSerializer().exclude("*.class")
				.deepSerialize(collection);
	}

	public static BetPartitionData fromJsonToBetPartitionData(String json) {
		return (BetPartitionData) (new JSONDeserializer())
				.use("list.values.values", Integer.class).deserialize(json,
						BetPartitionData.class);
	}
	
	public String toString(String lotno) {
		String string = "";
		if(lotno.equals("T01005")) {
			List<Integer[]> list_new = new ArrayList<Integer[]>();
			for (int i = 0; i < 4; i++) {
				Integer[] a = (Integer[]) list.get(i * 2);
				Integer[] b = (Integer[]) list.get(i * 2 + 1);
				Integer[] ab = new Integer[] { a[0], a[1], a[2], a[3], b[0],
						b[1], b[2], b[3] };
				list_new.add(ab);
			}
			setList(list_new);
			string = this.toJson();
		}else if(lotno.equals("T01006")) {
			List<Integer[]> list_new = new ArrayList<Integer[]>();
			for (int i = 0; i < 6; i++) {
				Integer[] a = (Integer[]) list.get(i * 2);
				Integer[] b = (Integer[]) list.get(i * 2 + 1);
				Integer[] ab = new Integer[] { a[0], a[1], a[2], b[0], b[1],
						b[2] };
				list_new.add(ab);
			}
			setList(list_new);
			string = this.toJson();
		}else if(lotno.equals("T01003")||lotno.equals("T01004")) {
			string = this.toJson();
		}
		return string;
	}
	
	
	public static void main(String[] args) {
		Tlot t0 = new Tlot();
		t0.setLotno("T01004");
		t0.setLotmulti(new BigDecimal(2));
		t0.setBatchcode("2012012");
		t0.setBetcode("103103103#####");
		
		Tlot t1 = new Tlot();
		t1.setLotno("T01004");
		t1.setLotmulti(new BigDecimal(2));
		t1.setBatchcode("2012012");
		t1.setBetcode("1,0,3,1,0,3,1,0,3,#,#,#,#,#");
		
		Tlot t2 = new Tlot();
		t2.setLotno("T01004");
		t2.setLotmulti(new BigDecimal(2));
		t2.setBatchcode("2012012");
		t2.setBetcode("13,03,30,1,0,3,1,0,3,#,#,#,#,#");
		
		Tlot t3 = new Tlot();
		t3.setLotno("T01004");
		t3.setLotmulti(new BigDecimal(2));
		t3.setBatchcode("2012012");
		t3.setBetcode("0,#,#,#,#,31,#,#,#,#,#,#,#,#$#,1,0,1,#,#,1,#,3,3,03,#,1,#");
		
		
		Tlot t4 = new Tlot();
		t4.setLotno("T01004");
		t4.setLotmulti(new BigDecimal(2));
		t4.setBatchcode("2012012");
		t4.setBetcode("13,03,30,1,0,3,1,0,3,3,#,#,#,#");
		
		BetPartitionData b = new BetPartitionData();
		b.getEmptyBetPartionData("T01004");
		
		b.onSuccess(t0);
		System.out.println(b.toJson());
		
		b.onSuccess(t1);
		System.out.println(b.toJson());
		
		b.onSuccess(t2);
		System.out.println(b.toJson());
		
		b.onSuccess(t3);
		System.out.println(b.toJson());
		
		b.onSuccess(t4);
		System.out.println(b.toJson());
	}
}
