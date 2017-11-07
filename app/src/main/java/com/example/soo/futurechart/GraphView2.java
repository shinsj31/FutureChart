package com.example.soo.futurechart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

/**
 * Created by soo on 2017-08-07.
 */

public class GraphView2 extends View {
    private Paint mLinePaint, mTextPaint;

    private float mTextGap;
    private int[]  mPointX, mPointY;
    private int[] numbers;
    private int value;
    private int size;
    private int startAge;

    private int bottom=0;

    private int position=0;

    private TypedArray types;

    public GraphView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        setTypes(context, attrs);
    }

    public int getGap()
    {
        return (int) types.getDimension(R.styleable.GraphView2_lineGap,0);
    }

    //그래프 옵션을 받는다
    private void setTypes(Context context, AttributeSet attrs) {
        types = context.obtainStyledAttributes(attrs, R.styleable.GraphView2);

        //수치 옵션
        Paint paint = new Paint();
        paint.setColor(types.getColor(R.styleable.GraphView2_textColor, Color.BLACK));
        paint.setTextSize(types.getDimension(R.styleable.GraphView2_textSize, 0));
        paint.setTextAlign(Paint.Align.CENTER);
        mTextPaint = paint;

        //막대와 수치와의 거리
        mTextGap = types.getDimension(R.styleable.GraphView2_textGap, 0);

        //막대 옵션
        mLinePaint = new Paint();
        mLinePaint.setColor(types.getColor(R.styleable.GraphView2_lineColor, Color.BLACK));
        mLinePaint.setStrokeWidth(types.getDimension(R.styleable.GraphView2_lineThickness, 0));
    }

    //그래프 정보를 받는다
    public void setValue(int value, int size, int age) {
        this.value=value; this.size=size;   startAge=age; //y축 값 배열
    }

    //그래프를 만든다
    public void draw() {
        int height = getHeight();

        //x축 막대 사이의 거리
        float gapx = types.getDimension(R.styleable.GraphView2_lineGap,0);

        //y축 단위 사이의 거리
        float gapy = height / (size+10);

        float halfgab = gapx / 2;

        int length = size;

        if(mPointX == null)
        {
            mPointX = new int[length];
            mPointY = new int[length];
            numbers = new int[length];

            int y=0;

            for(int i = 0 ; i < length ; i++) {
                //막대 좌표를 구한다
                int x = (int)(halfgab + (i * gapx));
                y += gapy;

                mPointX[i] = x;
                mPointY[i] = y;
                numbers[i]=12*value*(i+1);
            }
        }

    }

    public void setZero(){position=0; valuesSetting(0,value,true);}

    public void valuesSetting(int year, int value, boolean plus)
    {
        int num=0;
        int y=0;
        int height = getHeight();
        float gapy = height / (size+10);
        invalidate();

        if(plus)
            position+=year;
        else
            position-=year;
        if(position>=size || position<0){
            Toast.makeText(getContext(),"올바른 범위가 아닙니다.",Toast.LENGTH_SHORT).show();

            if(plus)
                position-=year;
            else
                position+=year;
            return;
        }

        for(int i=position; i<size; i++)
        {
            y += gapy;
            mPointY[i] = y;
            numbers[i]=value*12*(num+1);
            num++;
        }
    }

    //그래프를 그린다(onCreate 등에서 호출시)
    public void drawForBeforeDrawView() {
        //뷰의 크기를 계산하여 그래프를 그리기 때문에 뷰가 실제로 만들어진 시점에서 함수를 호출해 준다
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                draw();

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                else
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void settingBottom(int bottom)
    {
        this.bottom=bottom;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mPointX != null && mPointY != null) {
            int length = mPointX.length;
            int num=0;

            int temp=startAge;

            int bottom = getHeight()-this.bottom;
            for (int i = 0; i < length; i++) {
                int x = mPointX[i];
                int y = mPointY[i];

                //num+=value;

                mTextPaint.setColor(types.getColor(R.styleable.GraphView2_textColor, Color.BLACK));
                //막대 위 수치를 쓴다
                canvas.drawText("" + numbers[i], x, bottom-y-mTextGap, mTextPaint);

                //막대를 그린다
                canvas.drawLine(x, bottom, x, bottom-y, mLinePaint);

                //연령을 쓴다
                mTextPaint.setColor(Color.WHITE);
                canvas.drawText(temp+"세", x, getHeight()-((getHeight()-bottom)/2), mTextPaint);
                temp++;
            }
        }
    }
}
