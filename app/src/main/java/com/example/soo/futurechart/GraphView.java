package com.example.soo.futurechart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by soo on 2017-08-04.
 * 차트 유형1 구현
 * 가로형 Stacked Bar Chart
 */

public class GraphView extends View {
    private Paint mBarPaint;
    private Paint mBarFill;
    private Paint mTextPaint;

    private int[] mPointY;
    private int mBase;
    private int mStartAge;
    private int mAges;
    private int mIndex;

    private int[] mItems;
    private ArrayList<String> names;

    private int[] colors=new int[3];
    private boolean mYear;

    private Rect[] rect;

    boolean flag=false;

    private TypedArray types;

    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setTypes(context, attrs);
    }

    //그래프 옵션을 받는다.
    private void setTypes(Context context, AttributeSet attrs)
    {
        types=context.obtainStyledAttributes(attrs,R.styleable.GraphView);

        colors[0]=ContextCompat.getColor(context,R.color.color1);
        colors[1]=ContextCompat.getColor(context,R.color.color2);
        colors[2]=ContextCompat.getColor(context,R.color.color3);

        //막대 옵션
        mBarPaint=new Paint();
        mBarPaint.setColor(ContextCompat.getColor(context,R.color.color_bar));
        mBarPaint.setStrokeWidth(5);

        //색칠 막대 옵션
        mBarFill=new Paint();
        mBarFill.setColor(colors[0]);
        mBarFill.setStyle(Paint.Style.FILL);

        //텍스트 옵션
        mTextPaint=new Paint();
        mTextPaint.setTextSize(40);
        mTextPaint.setColor(Color.WHITE);
    }
    public void setPoints(int base, int startAge, int numOfAge, int[] items, boolean year, int index)
    {
        mBase=base;     //막대 길이 기준점
        mStartAge=startAge;
        mAges=numOfAge;     //나이 범위
        mItems=items;

        mYear=year;
        mIndex=index;

        rect=new Rect[mAges];
    }
    public void setNames(ArrayList<String> names){this.names=names;}

    public void draw()
    {
        int width=getWidth();

        //막대 사이의 간격
        float gap=(float)types.getDimension(R.styleable.GraphView_bar_gap,0);

        float halfGab=gap/2;

        mPointY=new int[mAges];

        for(int i=0; i<mAges; i++)
            mPointY[i]=(int)(halfGab+gap*i);
    }

    //그래프를 그린다(onCreate 등에서 호출시)
    public void drawForBeforeDrawView()
    {
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

    public int getGap()
    {
        return (int) types.getDimension(R.styleable.GraphView_bar_gap,0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mPointY != null)
        {
            int length=mPointY.length;
            int boundLen=(int)(getWidth()*0.75);
            int textSize=(int)(types.getDimension(R.styleable.GraphView_text_gap,20));
            int textPadding=(int)(types.getDimension(R.styleable.GraphView_text_padding,20));
            float gap=(float)types.getDimension(R.styleable.GraphView_bar_gap,0);
            boolean up=true;

            int base=mBase;

            if(mAges>40)
                boundLen=(int)(getWidth()*0.65);

            ///////////////////////////////여기서부터 그래프 그리기/////////////////////////////////

            int x;
             for(int i=0; i<length; i++)
            {
                if(mYear)
                    x=(int)(boundLen+(i/3)*(getWidth()*0.012));
                else
                    x=(int)(boundLen+(mIndex/3)*(getWidth()*0.012));
                int y=mPointY[i];
                int position=textSize+textPadding;
                float endPoint;
                int color_i=0;
                float contents=0;

                int len=boundLen-position;

                //판 그리기. 3년마다 1.2% 길이 증가
                mBarPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                if(mYear)
                    rect[i]=new Rect(textSize+textPadding,y,x,y+100);
                canvas.drawRect(textSize+textPadding,y,x,y+100,mBarPaint);

                //컨텐츠 그리기
                for(int j=0; j<mItems.length; j++)
                {
                    float temp=contents;
                    contents+=mItems[j];
                   mBarFill.setColor(colors[color_i++]);
                    if(color_i==3)
                        color_i=0;
                    endPoint=(textSize+textPadding)+ (len*(contents/base));
                    canvas.drawRect(position,y,endPoint ,y+100,mBarFill);
                    mTextPaint.setColor(Color.BLACK);
                    mTextPaint.setTextSize(30);

                    if(up) {
                        canvas.drawText(" " + names.get(j) + "  ", position, y - 20, mTextPaint);
                        up = false;
                    }
                    else
                    {
                        canvas.drawText(" "+names.get(j)+"  ",position,y+130,mTextPaint);
                        up=true;
                    }

                    mTextPaint.setColor(Color.DKGRAY);
                    mTextPaint.setTextSize(28);
                    if(mYear)
                        canvas.drawText(" "+mItems[j]*12+"  ",position,y+gap/4,mTextPaint);
                    else
                        canvas.drawText(" "+mItems[j]+"  ",position,y+gap/4,mTextPaint);
                    position= (int) ((textSize+textPadding)+(len*(contents/base)));
                }
                mBarPaint.setStyle(Paint.Style.STROKE);
                mTextPaint.setColor(Color.WHITE);
                mTextPaint.setTextSize(40);
                canvas.drawRect(textSize+textPadding,y,x,y+100,mBarPaint);
                if(mYear)
                    canvas.drawText((mStartAge+i)+"세",textPadding,y+gap/3,mTextPaint);
                else
                    canvas.drawText((mStartAge+i)+"월",textPadding,y+gap/3,mTextPaint);
            }
        }
    }

    public double[]getExtra()
    {
        double[] extras=new double[mAges];
        int total_items=0;

        for(int i=0; i<mItems.length; i++)
            total_items+=mItems[i];

        double base=mBase;
        extras[0]= (int) (base-total_items);
        for(int i=1; i<mAges; i++)
        {
            base= base+base*0.012;
            extras[i]= (int) (base-total_items);
            Log.i("log1",extras[i]+"");
        }
        return extras;
    }


    public Rect[] getRect()
    {
        return rect;
    }
}
