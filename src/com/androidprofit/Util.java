
package com.androidprofit;

import java.text.DecimalFormat;

public class Util {
	public static class Format {
		/**
		 * 将单位装换为K，M的计量
		 * 
		 * @param size
		 * @return
		 */
		public static String sizeUnit(int size) {
			DecimalFormat df = new DecimalFormat("###.##");
			float f;
			if (size < 1024 * 1024) {
				f = (float)((float)size / (float)1024);
				return (df.format(new Float(f).doubleValue()) + "K");
			} else {
				f = (float)((float)size / (float)(1024 * 1024));
				return (df.format(new Float(f).doubleValue()) + "M");
			}
		}
	}
}
