package ywcai.ls.control.scan;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;


public class LsScan extends RelativeLayout {
    private LsRadarScanView lsRadarScanView;
    private TextView progressText;
    private List<TextView> temp = new ArrayList<>();

    public LsScan(Context context) {
        super(context);
        initView(context);

    }

    public LsScan(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LsScan(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.main_layout, this);
        lsRadarScanView = (LsRadarScanView) findViewById(R.id.radar);
        progressText = (TextView) findViewById(R.id.progress_Text);
    }

    public void startScan() {
        reset();
        lsRadarScanView.startScan();
    }

    public void stopScan() {
        lsRadarScanView.stopScan();
    }

    public boolean isScanRunning() {
        return lsRadarScanView.isRun;
    }

    public void setTextColor(int color) {
        TextView textView = new TextView(this.getContext());

        textView.setTextColor(color);
    }

    public void setTextSize(int size) {
        TextView textView = new TextView(this.getContext());
        textView.setTextSize(size);
    }

    public void addText(String text, int rate) {
        int length = new Random().nextInt(35) + 10;
        addText(text, length * 1.00 / 50, rate, 14, Color.GREEN);
    }

    public void addText(String text, Double radiusPercent, int rate) {
        addText(text, radiusPercent, rate, 14, Color.GREEN);
    }

    public void addText(String text, int rate, int size, int color) {
        int length = new Random().nextInt(35) + 10;
        addText(text, length * 1.00 / 50, rate, size, color);
    }

    public void addText(String text, Double radiusPercent, int rate, int size, int color) {
        TextView textView = new TextView(this.getContext());
        textView.setTextSize(size);
        textView.setTextColor(color);
        RelativeLayout.LayoutParams lay = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int cX = lsRadarScanView.getCenter().x;
        int cY = lsRadarScanView.getCenter().y;
        int radius = lsRadarScanView.getRadius();
        int top = (int) (cY - (radiusPercent * radius) * Math.cos(rate * 3.1415926 / 180));
        int left = (int) (cX + (radiusPercent * radius) * Math.sin(rate * 3.1415926 / 180));
        lay.setMargins(left, top, 0, 0);
        textView.setLayoutParams(lay);
        textView.setText(text);
        this.addView(textView);
        temp.add(textView);
    }

    public void reset() {
        setProgress(0);
        for (int i = 0; i < temp.size(); i++) {
            removeView(temp.get(i));
        }
        temp.clear();
    }

    public void setProgress(int percent) {
        progressText.setText(percent + "%");
        if (percent == 100) {
            lsRadarScanView.stopScan();
        }
    }
}
