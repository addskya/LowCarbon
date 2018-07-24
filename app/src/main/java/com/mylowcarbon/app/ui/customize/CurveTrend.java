package com.mylowcarbon.app.ui.customize;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.mylowcarbon.app.R;
import com.mylowcarbon.app.net.response.Trade;
import com.mylowcarbon.app.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 曲线趋势图
 */

public class CurveTrend extends View {
    private float viewWith;
    private float viewHeight;

    private float brokenLineWith = 0.5f;

    private int brokenLineColor = 0xff02bbb7;
    private int straightLineColor = 0xffe2e2e2;//0xffeaeaea
    private int textNormalColor = 0xff7e7e7e;
    private int selectPointColor = 0xff7e7e7e;
    private int selectBgColor = 0xff7e7e7e;

    private int maxScore = 700;
    private int minScore = 650;

    private int monthCount = 7;
    private int selectMonth = 3;//选中的月份
    private int lastPositon = monthCount-1;

    private String[] monthText = new String[]{"01JAN", "02JAN", "03JAN", "04JAN", "05JAN", "06JAN", "07JAN"};
    private int[] score = new int[]{660, 803, 669, 678, 633, 682, 720};
    private String[] floatText = new String[]{"↑20%", "↓20%", "↓120%", "↓20.22%", "↑120.34%", "↑20%", "↑20%"};

    private List<Point> scorePoints;

    private int textSize = dipToPx(15);

    private Paint brokenPaint;
    private Paint straightPaint;
    private Paint dottedPaint;
    private Paint textPaint;

    private Path brokenPath;
    private Context context;


    public CurveTrend(Context context) {
        super(context);
        this.context= context;
        initConfig(context, null);
        init();
    }

    public CurveTrend(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context= context;
        initConfig(context, attrs);
        init();
    }

    public CurveTrend(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfig(context, attrs);
        init();

    }

    /**
     * 初始化布局配置
     *
     * @param context
     * @param attrs
     */
    private void initConfig(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CurveTrend);

        maxScore = a.getInt(R.styleable.CurveTrend_max_score, 700);
        minScore = a.getInt(R.styleable.CurveTrend_min_score, 650);
        brokenLineColor = a.getColor(R.styleable.CurveTrend_broken_line_color, brokenLineColor);
        straightLineColor = a.getColor(R.styleable.CurveTrend_straight_line_color, straightLineColor);
        selectPointColor = a.getColor(R.styleable.CurveTrend_select_point_color, brokenLineColor);
        textNormalColor = a.getColor(R.styleable.CurveTrend_text_normal_color, textNormalColor);
        selectBgColor = a.getColor(R.styleable.CurveTrend_select_bg_color, textNormalColor);

        a.recycle();

    }

    private void init() {
        brokenPath = new Path();

        brokenPaint = new Paint();
        brokenPaint.setAntiAlias(true);
        brokenPaint.setStyle(Paint.Style.STROKE);
        brokenPaint.setStrokeWidth(dipToPx(brokenLineWith));
        brokenPaint.setStrokeCap(Paint.Cap.ROUND);

        straightPaint = new Paint();
        straightPaint.setAntiAlias(true);
        straightPaint.setStyle(Paint.Style.STROKE);
        straightPaint.setStrokeWidth(brokenLineWith);
        straightPaint.setColor((straightLineColor));
        straightPaint.setStrokeCap(Paint.Cap.ROUND);

        dottedPaint = new Paint();
        dottedPaint.setAntiAlias(true);
        dottedPaint.setStyle(Paint.Style.STROKE);
        dottedPaint.setStrokeWidth(brokenLineWith);
        dottedPaint.setColor((straightLineColor));
        dottedPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor((textNormalColor));
        textPaint.setTextSize(dipToPx(15));


    }

    private void initData() {
        scorePoints = new ArrayList<>();
        float maxScoreYCoordinate = viewHeight * 0.15f;
        float minScoreYCoordinate = viewHeight * 0.65f;

        viewWith = 1080;
        Log.v("ScoreTrend", "initData  viewWith: " + viewWith);

        float newWith = viewWith - (viewWith * 0.02f) * 2;//分隔线距离最左边和最右边的距离是0.15倍的viewWith

        Log.v("ScoreTrend", "initData  newWith: " + newWith);
        Log.v("ScoreTrend", "initData  monthCount: " + monthCount);

        int coordinateX;

        for (int i = 0; i < score.length; i++) {
            Point point = new Point();
//            coordinateX = (int) (newWith * ((float) (i) / (monthCount)) + (viewWith * 0.02f)) + (int)avgWidth/2;
            coordinateX = (int) (newWith * ((float) (i) / (monthCount-1)) + (viewWith * 0.02f))  ;
            point.x = coordinateX;
            if (score[i] > maxScore) {
                score[i] = maxScore;
            } else if (score[i] < minScore) {
                score[i] = minScore;
            }
            point.y = (int) (((float) (maxScore - score[i]) / (maxScore - minScore)) * (minScoreYCoordinate - maxScoreYCoordinate) + maxScoreYCoordinate);
            Log.v("ScoreTrend", "initData: " + "scorePoints["+i+"] = "+score[i]+ "  point.x = "+coordinateX + "  point.y = "+point.y);

            scorePoints.add(point);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
         if(w==0){
            return;
        }
        viewWith = w;
        viewHeight = h;
        initData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawDottedLine(canvas, viewWith * 0.02f, viewHeight * 0.15f, viewWith -viewWith * 0.02f, viewHeight * 0.15f);
//        drawDottedLine(canvas, viewWith * 0.02f, viewHeight * 0.4f, viewWith-viewWith * 0.02f, viewHeight * 0.4f);
        drawMonthLine(canvas);
        drawBrokenLine(canvas);
        drawPoint(canvas);
        drawText(canvas);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.getParent().requestDisallowInterceptTouchEvent(true);//一旦底层View收到touch的action后调用这个方法那么父层View就不会再调用onInterceptTouchEvent了，也无法截获以后的action

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                onActionUpEvent(event);
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_CANCEL:
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return true;
    }

    private void onActionUpEvent(MotionEvent event) {
        boolean isValidTouch = validateTouch(event.getX(), event.getY());

        if (isValidTouch) {
            invalidate();
        }
    }

    //是否是有效的触摸范围
    private boolean validateTouch(float x, float y) {

        //曲线触摸区域
        for (int i = 0; i < scorePoints.size(); i++) {
            // dipToPx(8)乘以2为了适当增大触摸面积
            if (x > (scorePoints.get(i).x - dipToPx(8) * 2) && x < (scorePoints.get(i).x + dipToPx(8) * 2)) {
                if (y > (scorePoints.get(i).y - dipToPx(8) * 2) && y < (scorePoints.get(i).y + dipToPx(8) * 2)) {
                    selectMonth = i + 1;
                    return true;
                }
            }
        }

        //月份触摸区域
        //计算每个月份X坐标的中心点
        float monthTouchY = viewHeight * 0.7f - dipToPx(3);//减去dipToPx(3)增大触摸面积

        float newWith = viewWith - (viewWith * 0.02f) * 2;//分隔线距离最左边和最右边的距离是0.15倍的viewWith
        float validTouchX[] = new float[monthText.length];
        for (int i = 0; i < monthText.length; i++) {
            validTouchX[i] = newWith * ((float) (i) / (monthCount)) + (viewWith * 0.02f);
        }

        if (y > monthTouchY) {
            for (int i = 0; i < validTouchX.length; i++) {
                Log.v("ScoreTrend", "validateTouch: validTouchX:" + validTouchX[i]);
                if (x < validTouchX[i] + dipToPx(8) && x > validTouchX[i] - dipToPx(8)) {
                    Log.v("ScoreTrend", "validateTouch: " + (i + 1));
                    selectMonth = i + 1;
                    return true;
                }
            }
        }

        return false;
    }


    //绘制折线穿过的点
    protected void drawPoint(Canvas canvas) {
        if (scorePoints == null) {
            return;
        }
        brokenPaint.setStrokeWidth(dipToPx(1));
        for (int i = 0; i < scorePoints.size(); i++) {
            if(i==0||(i==selectMonth-1)&&i<=lastPositon||i==lastPositon){
                brokenPaint.setColor(brokenLineColor);
                brokenPaint.setStyle(Paint.Style.STROKE);
                brokenPaint.setStrokeWidth(dipToPx(2));
                canvas.drawCircle(scorePoints.get(i).x, scorePoints.get(i).y, dipToPx(5), brokenPaint);
                brokenPaint.setColor(Color.WHITE);
                brokenPaint.setStyle(Paint.Style.FILL);
                brokenPaint.setColor(0xffffffff);
                canvas.drawCircle(scorePoints.get(i).x, scorePoints.get(i).y, dipToPx(4f), brokenPaint);
                if (i == selectMonth - 1) {
                    textPaint.setColor(0xffffffff);
                    brokenPaint.setStrokeWidth(dipToPx(5));
                    brokenPaint.setStyle(Paint.Style.FILL);

                    canvas.drawCircle(scorePoints.get(i).x, scorePoints.get(i).y, dipToPx(6f), brokenPaint);
                    brokenPaint.setColor(selectPointColor);
                    canvas.drawCircle(scorePoints.get(i).x, scorePoints.get(i).y, dipToPx(3f), brokenPaint);
                    if(!TextUtils.isEmpty(floatText[i])){
                        //绘制浮动文本背景框
                        drawFloatTextBackground(canvas, scorePoints.get(i).x, scorePoints.get(i).y - dipToPx(8f),floatText[i].length());

                        textPaint.setColor(0xffffffff);
                        //绘制浮动文字
                        canvas.drawText(floatText[i], scorePoints.get(i).x, scorePoints.get(i).y - dipToPx(5f) - textSize, textPaint);
                    }

                }
//                brokenPaint.setColor(0xffffffff);
//                canvas.drawCircle(scorePoints.get(i).x, scorePoints.get(i).y, dipToPx(4f), brokenPaint);
//                brokenPaint.setStyle(Paint.Style.STROKE);
//                brokenPaint.setColor(brokenLineColor);
//                canvas.drawCircle(scorePoints.get(i).x, scorePoints.get(i).y, dipToPx(4f), brokenPaint);
            }

        }
    }

    //绘制月份的直线(包括刻度)
    private void drawMonthLine(Canvas canvas) {
        straightPaint.setStrokeWidth(1);
//        canvas.drawLine(0+viewWith * 0.02f, viewHeight * 0.7f, viewWith -viewWith * 0.02f, viewHeight * 0.7f, straightPaint);

        float newWith = viewWith ;//分隔线距离最左边和最右边的距离是0.15倍的viewWith
        float avgWidth  = viewWith /(monthCount);
        float coordinateX;//分隔线X坐标
        for (int i = 0; i <= monthCount; i++) {

            coordinateX = newWith * ((float) (i) / (monthCount))  ;
            canvas.drawLine(coordinateX, 0, coordinateX, viewHeight, straightPaint);
            if (i == selectMonth ) {
                Paint paint = new Paint();
                paint.setColor(selectBgColor);
                 paint.setStyle(Paint.Style.FILL);
                 canvas.drawRect(coordinateX, 0, coordinateX-avgWidth, viewHeight, paint);
            }
        }
    }

    //绘制折线
    private void drawBrokenLine(Canvas canvas) {
        brokenPath.reset();
        brokenPaint.setColor(brokenLineColor);
        brokenPaint.setStyle(Paint.Style.STROKE);
        if (score.length == 0) {
            return;
        }

        brokenPaint.setStyle(Paint.Style.STROKE);
        brokenPaint.setDither(true);
        brokenPaint.setAntiAlias(true);
        brokenPaint.setStrokeWidth(5);
        PathEffect pathEffect = new CornerPathEffect(50);
        brokenPaint.setPathEffect(pathEffect);
        Log.v("ScoreTrend", "drawBrokenLine  lastPositon: " + lastPositon);

        for (int i = 0; i < scorePoints.size(); i++) {
            Log.v("ScoreTrend", "drawBrokenLine  i: " + i);

            if(i<=lastPositon){


            if (i == 0 ){
                brokenPath.moveTo(scorePoints.get(i).x, scorePoints.get(i).y);

            } else  if(i==scorePoints.size()-1){
                brokenPath.lineTo(scorePoints.get(i).x, scorePoints.get(i).y );
            } else {
                float off = scorePoints.get(i).y - scorePoints.get(i - 1).y ;
                Log.v("ScoreTrend", "drawBrokenLine  off: " + off);

                if (off>50) {
                    brokenPath.lineTo(scorePoints.get(i).x, scorePoints.get(i).y +20 );
                } else if (off<-50)  {
                    brokenPath.lineTo(scorePoints.get(i).x, scorePoints.get(i).y -20);
                } else{
                    brokenPath.lineTo(scorePoints.get(i).x, scorePoints.get(i).y  );

                }
            }
            }
        }
        canvas.drawPath(brokenPath, brokenPaint);

    }


    //绘制文本
    private void drawText(Canvas canvas) {
        textPaint.setTextSize(dipToPx(12));
        textPaint.setColor(textNormalColor);

//        canvas.drawText(String.valueOf(maxScore), viewWith * 0.1f - dipToPx(10), viewHeight * 0.15f + textSize * 0.25f, textPaint);
//        canvas.drawText(String.valueOf(minScore), viewWith * 0.1f - dipToPx(10), viewHeight * 0.4f + textSize * 0.25f, textPaint);

        textPaint.setColor(0xff7c7c7c);

        float newWith = viewWith  ;//分隔线距离最左边和最右边的距离是0.15倍的viewWith
        float avgWidth  = viewWith /(monthCount);

        float coordinateX;//分隔线X坐标
        textPaint.setTextSize(dipToPx(12));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textNormalColor);
        textSize = (int) textPaint.getTextSize();
        for (int i = 0; i < monthText.length; i++) {
            coordinateX = newWith * ((float) (i) / (monthCount))   + avgWidth/2;

            if (i == selectMonth-1 ) {

                textPaint.setStyle(Paint.Style.STROKE);
                textPaint.setColor(brokenLineColor);
            }
            if(!TextUtils.isEmpty(monthText[i])){
                //绘制月份
                String date = monthText[i].substring(3,5);
                String month = monthText[i].substring(0,2);
                textPaint.setTextSize(dipToPx(18));

                canvas.drawText(date, coordinateX, viewHeight * 0.7f + dipToPx(4) + textSize + dipToPx(5), textPaint);
                textPaint.setTextSize(dipToPx(16));

                canvas.drawText(month, coordinateX, viewHeight * 0.7f + dipToPx(4) + textSize + dipToPx(5)+40, textPaint);
                textPaint.setColor(textNormalColor);
            }

        }

    }

    //绘制显示浮动文字的背景
    private void drawFloatTextBackground(Canvas canvas, int x, int y ,int textLen) {
        brokenPath.reset();
        brokenPaint.setColor(brokenLineColor);
        brokenPaint.setStyle(Paint.Style.FILL);
        Log.v("ScoreTrend", "drawFloatTextBackground  textSize : " + textSize + " textLen : "+textLen);
        float OffsetX = 5 ;
        float OffsetY = 5 ;
        float height =  20;
        float width =  10 * textLen ;
        if((x + dipToPx((width/2)-OffsetX))>viewWith){
//            OffsetX = (x + (dipToPx((width/2)-OffsetX)) - viewWith) / 2 ;
        }
        Log.e("ScoreTrend", "drawFloatTextBackground  OffsetX : " + OffsetX  );

        Log.v("ScoreTrend", "drawFloatTextBackground  width : " + width  );

        //P1
        Point point = new Point(x, y);
        brokenPath.moveTo(point.x, point.y);

        //P2
        point.x = point.x + dipToPx(OffsetX);
        point.y = point.y - dipToPx(OffsetX);
        brokenPath.lineTo(point.x, point.y);

        //P3
        point.x = point.x + dipToPx((width/2)-OffsetX);
        brokenPath.lineTo(point.x, point.y);

        //P4
        point.y = point.y - dipToPx(height);
        brokenPath.lineTo(point.x, point.y);

        //P5
        point.x = point.x - dipToPx(width);
        brokenPath.lineTo(point.x, point.y);

        //P6
        point.y = point.y + dipToPx(height);
        brokenPath.lineTo(point.x, point.y);

        //P7
        point.x = point.x + dipToPx((width/2)-OffsetX);
        brokenPath.lineTo(point.x, point.y);

        //最后一个点连接到第一个点
        brokenPath.lineTo(x, y);

        canvas.drawPath(brokenPath, brokenPaint);
    }

    /**
     * 画虚线
     *
     * @param canvas 画布
     * @param startX 起始点X坐标
     * @param startY 起始点Y坐标
     * @param stopX  终点X坐标
     * @param stopY  终点Y坐标
     */
    private void drawDottedLine(Canvas canvas, float startX, float startY, float stopX, float stopY) {
        dottedPaint.setPathEffect(new DashPathEffect(new float[]{20, 10}, 4));
        dottedPaint.setStrokeWidth(1);
        // 实例化路径
        Path mPath = new Path();
        mPath.reset();
        // 定义路径的起点
        mPath.moveTo(startX, startY);
        mPath.lineTo(stopX, stopY);
        canvas.drawPath(mPath, dottedPaint);

    }


    public int[] getScore() {
        return score;
    }

    public void setScore(int[] score) {
        this.score = score;
        initData();
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public void setData(List<Trade.TradeTrend> data){
        Log.e("ScoreTrend", "setData  data.isEmpty(): " + data.isEmpty());

        if(data.isEmpty()){
            return;
        }

        if(data.size()<7){
            for(int i = 0;1<= 7- data.size();i++){
                data.add(new Trade.TradeTrend(0,"04月01日"));
                }
        }
        Log.e("ScoreTrend", "setData  data.size(): " + data.size());

        int min = 10;
        int max =  0;
//        score = new int[7];
//        monthText = new String[7];
//        floatText = new String[7];
        for(int i = 0;i<data.size();i++){
             if(max < data.get(i).avg_price && data.get(i).avg_price!=0){
                max = data.get(i).avg_price;
            }
            if(min > data.get(i).avg_price&& data.get(i).avg_price!=0){
                min = data.get(i).avg_price;
            }
//            String date = DateUtil.timeStampToStr3(data.get(i).create_time);
//            monthText[i] = date;
             monthText[i] = data.get(i).time;
            score[i] = data.get(i).avg_price;
            if(i==0||data.get(i).avg_price == 0){
                floatText[i]="";
            } else {
                floatText[i] = ""+(int)((float)((data.get(i).avg_price - data.get(i-1).avg_price)/(float)data.get(i-1).avg_price)*100f) + "%";
            }
            if(data.get(i).avg_price!=0){
                lastPositon = i ;
            }
        }

        this.minScore = min;
        this.maxScore = max;
        Log.e("ScoreTrend", "setData  lastPositon: " +lastPositon);
        Log.e("ScoreTrend", "setData  score: " +score.length);
        for(int i = 0;i<score.length;i++){
            Log.e("ScoreTrend", "setData  score["+i+"]: " + score[i]);

        }
        Log.e("ScoreTrend", "setData  floatText: " +floatText.length );
        for(int i = 0;i<floatText.length;i++){
            Log.e("ScoreTrend", "setData  floatText["+i+"]: " + floatText[i]);

        }

        Log.e("ScoreTrend", "setData  monthText.length: " + monthText.length);
        Log.e("ScoreTrend", "setData  monthCount: " + monthCount);
        Log.e("ScoreTrend", "setData  minScore: " + minScore);
        Log.e("ScoreTrend", "setData  maxScore: " + maxScore);
        monthCount = score.length ;

        initData();
    }
    /**
     * dip 转换成px
     *
     * @param dip
     * @return
     */
    private int dipToPx(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

}
