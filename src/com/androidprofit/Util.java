
package com.androidprofit;

import java.text.DecimalFormat;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

	public static void Assert(boolean cond) {
		if (!cond) {
			throw new AssertionError();
		}
	}

    public static Toast MyToast(Context context, String content, int duration){
        View layout = View.inflate(context, R.layout.custom_toast, null);

        ImageView image = (ImageView) layout.findViewById(R.id.tvImageToast);
        image.setImageResource(R.drawable.ic_launcher);
        TextView title = (TextView) layout.findViewById(R.id.tvTitleToast);
        title.setText(content);

        Toast toast = new Toast(context.getApplicationContext());
        toast.setGravity(Gravity.RIGHT | Gravity.CENTER, 0, 0);
        toast.setDuration(duration);
        toast.setView(layout);

        return toast;
    }
}
