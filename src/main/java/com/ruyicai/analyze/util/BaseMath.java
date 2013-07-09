package com.ruyicai.analyze.util;

public class BaseMath {
	
	/**
	 * 计算从n中选出k个数的组合数
	 * @param n 样本总数
	 * @param k 选取样本数
	 * @return 组合数
	 */
	public static long nchoosek(int n,int k) {
		//楠岃瘉浼犲叆鍙傛暟锛宯涓嶈兘灏忎簬绛変簬0锛宬涓嶈兘灏忎簬0锛宯涓嶈兘灏忎簬k锛沰=0鐨勬椂鍊欙紝瑙勫畾缁勫悎鏁颁负1
		if(n <= 0 || k < 0 || n < k) {
			return -1;
		}
		if(k == 0 || n==k) {
			return 1;
		}
		//鎸夌収缁勫悎鏁版�璐紝鍦╧澶т簬n/2鏃讹紝璁＄畻浠巒涓�鍑簄-k鐨勫�锛屽噺灏戣绠楅噺
		if(k > n/2) {
			k = n - k;
		}
		
		//閫氳繃缁勫悎鏁板叕寮忔眰缁勫悎鏁�
		long result = multiplyByStep(n,n-k+1)/multiplyByStep(k,1);
		
		return result;
	}
	
	/**
	 * 计算从m到n,以1为步长的成绩(m:1:n),m、n为正整数
	 * @param m 正整数
	 * @param n 正整数
	 * @return 步长为1，m到n的乘机，-1表示传入的mn为负数
	 */
	public static long multiplyByStep(int m,int n) {
		//鏁版嵁楠岃瘉锛岃瀹歮鍜宯涓嶈兘灏忎簬0
		if(m < 0 || n < 0) {
			return -1;
		}
		
		//瀹氫箟榛樿杩斿洖鍊�
		long result = 1l;
		
		//m澶т簬绛変簬n锛屼粠n浠�涓烘闀夸箻鍒癿;m灏忎簬n锛屼粠m浠�涓烘闀夸箻鍒皀
		if(m >= n) {
			for(int i = n;i <= m;i++) {
				result = result * i;
			}
		}else {
			for(int i = m;i <= n;i++) {
				result = result * i;
			}
		}
		return result;
	}
	
}
