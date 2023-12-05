package com.musicplayer.alarmclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
interface Observer{
    void onEndIndex();
    void onStartIndex();
}
public class DataPickerView extends View {
    int canvasW;
    int canvasH;
    int position;
    int correction;
    int position1;
    int position2;
    int index;
  float initPosition=0;
    float roundPos;
    int needPos;
  boolean needAnimate=false;
    boolean actionUP=false;
  String numbers[];
    int itemHeight;
    int speed=10;
    int gravitySpeed=10;


    float delta;
    int scrollspeed;
    int initScrollSpeed;
    long actdownTime;
    String currentValue;

    int previousIndex;


    int startIndex;
  ArrayList numbersArray;
  boolean up;
  boolean down;
  com.yes.alarmclockfeature.presentation.ui.Observer observer;
    void registerObserver(com.yes.alarmclockfeature.presentation.ui.Observer observer){
        this.observer=observer;
    }
    void init(int[] numbers,int startIndex){
        this.startIndex=startIndex;
      int t=1;
      String q=String.format("%1$02d",t);
      this.numbers=new String[numbers.length];
      for(int i=0;i<numbers.length;i++){
          this.numbers[i]=String.format("%1$02d",numbers[i]);
      }
      //this.numbers=numbers;

      //forwardIndex=backIndex+3;
    }
    public DataPickerView(Context context) {
        super(context);
        //numbers=new int[] {1,2,3,4,5,6,7,8,9,10};
        numbersArray=new ArrayList();

    }
    public DataPickerView(Context context, AttributeSet attrs) {

        super(context, attrs);
       /* numbers=new int[] {1,2,3,4,5,6,7,8,9,10};
        numbersArray=new ArrayList();*/

    }

    public DataPickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        canvasW = w ;
        canvasH = h ;
        super.onSizeChanged(w, h, oldw, oldh);
        itemHeight=canvasH/3;
       /* backIndex=0;
        forwardIndex=3;*/


        correction=4;
        position=-(startIndex+correction/2)*itemHeight;

      /*  for(int i=0;i<4;i++){
            count++;
            if(count>numbers.length-1){
                count=0;
            }
            Item item =new Item(numbers,count);
            item.addPosition((int)(i*itemHeight));
            numbersArray.add(item);
        }*/

    }

    void moveBackward(){
        actionUP=true;
        int distance=-itemHeight;
        int time=300;
        initScrollSpeed = distance*1000/time;
        scrollspeed = initScrollSpeed;
        invalidate();
    }
    void moveForward(){
        actionUP=true;
        int distance=itemHeight;
        int time=300;
        initScrollSpeed = distance*1000/time;
        scrollspeed = initScrollSpeed;
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setTextSize(canvasH/3);
        paint.setColor(Color.WHITE);
        ///////////////////////////
      /*  Paint paint2 = new Paint();
        paint2.setTextSize(canvasH/3);
        paint2.setColor(Color.RED);

        Paint paint3 = new Paint();
        paint3.setTextSize(canvasH/3);
        paint3.setColor(Color.GREEN);

        Paint paint4 = new Paint();
        paint4.setTextSize(canvasH/3);
        paint4.setColor(Color.BLUE);

        Paint paint5 = new Paint();
        paint5.setTextSize(canvasH/3);
        paint5.setColor(Color.WHITE);*/
        //////////////////////////
     /*   canvas.drawLine(0,0,canvasW,0,paint);
        canvas.drawLine(0,canvasH/3,canvasW,canvasH/3,paint);
        canvas.drawLine(0,canvasH/2,canvasW,canvasH/2,paint2);
        canvas.drawLine(0,canvasH/3*2,canvasW,canvasH/3*2,paint);
        canvas.drawLine(0,canvasH-1,canvasW,canvasH-1,paint);

        canvas.drawLine(canvasW/2,0,canvasW/2,canvasH,paint2);*/
        /////////////////////////
      /*  int a=position1+(0*canvasH/3);
        int b=position1+(1*canvasH/3);
        int c=position1+(2*canvasH/3);
        int d=position1+(3*canvasH/3);
        int e=position1+(4*canvasH/3);
       int currentIndex=(int)(position1/itemHeight);*/
       /* if(currentIndex<backIndex){
            forwardIndex++;
            if(forwardIndex>numbers.length-1){
                forwardIndex=0;
            }
            numbersArray.add(numbers[forwardIndex]);
            numbersArray.remove(0);

        }*/
        //////////////////////////
       // canvas.drawLine(canvasW/2,0,canvasW/2,canvasH,paint2);
        float[] txtWidthArr=new float[numbers[0].length()];
       // paint.getTextWidths(((Item)numbersArray.get(0)).string,txtWidthArr);
        paint.getTextWidths(numbers[0],txtWidthArr);
        Rect bounds = new Rect();
       // paint.getTextBounds(((Item)numbersArray.get(0)).string, 0, ((Item)numbersArray.get(0)).string.length(), bounds);
        paint.getTextBounds(numbers[0], 0, numbers[0].length(), bounds);
        int textHeight=(int)(itemHeight-Math.abs(bounds.top));
        int txtWidth=0;
        for(int i=0;i<txtWidthArr.length;i++){
            txtWidth+=txtWidthArr[i];
        }

        ///////////////////////
        if(position>0){
            position=position-(numbers.length)*itemHeight;
        }else if(position<-(numbers.length)*itemHeight){
            position=position+(numbers.length)*itemHeight;
        }
        //////////////////////
        index=index(position,2);
        //////////////////////
/*
        int ind1=index(position,0);
        int ind2=index(position,1);
         index=index(position,2);
        int ind4=index(position,3);
        int ind5=index(position,4);
        canvas.drawText(numbers[ind1],canvasW/2-txtWidth/2,myDraw(position,0),paint);
        canvas.drawLine(canvasW/6,myDraw(position,0),canvasW/6*5,myDraw(position,0),paint);
        canvas.drawText(numbers[ind2],canvasW/2-txtWidth/2,myDraw(position,1),paint);
       canvas.drawText(numbers[index],canvasW/2-txtWidth/2,myDraw(position,2),paint);
        canvas.drawText(numbers[ind4],canvasW/2-txtWidth/2,myDraw(position,3),paint);
        canvas.drawText(numbers[ind5],canvasW/2-txtWidth/2,myDraw(position,4),paint);*/
        for(int i=0;i<5;i++){
            canvas.drawText(numbers[index(position,i)],canvasW/2-txtWidth/2,myDraw(position,i)-textHeight/2,paint);
            canvas.drawLine(canvasW/6,myDraw(position,i),canvasW/6*5,myDraw(position,i),paint);
        }
      /*  for(int i=0;i<numbersArray.size();i++){

            //canvas.drawText(((Item)numbersArray.get(0)).string,canvasW/2-txtWidth/2,position1/itemHeight,paint);
          // canvas.drawText(((Item)numbersArray.get(i)).string,canvasW/2-txtWidth/2,((Item)numbersArray.get(i)).pos-textHeight/2,paint);
          //  canvas.drawLine(canvasW/6,((Item)numbersArray.get(i)).pos,canvasW/6*5,((Item)numbersArray.get(i)).pos,paint);
        }*/

        Paint framePaint = new Paint();
        framePaint.setShader(new LinearGradient(0, 0, 0, getHeight() / 2, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP));
        canvas.drawPaint(framePaint);
        framePaint.setShader(new LinearGradient(0, getHeight(), 0, getHeight() - getHeight() / 2, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP));
        canvas.drawPaint(framePaint);



       // int needPosition=(canvasH/3)/(canvasH/position1);
      /*  if(needAnimate){
            this.postInvalidateDelayed(1000 / 50);
            if(roundPos>itemHeight/2){
                if(roundPos+speed>itemHeight){
                    item.addPosition((int)(itemHeight-item.roundPos));
                    needAnimate=false;
                }else{
                    item.roundPos+=speed;
                    item.addPosition(speed);
                }
            }else{
                if(item.roundPos-speed<0){
                    item.addPosition((int)(-item.roundPos));
                    needAnimate=false;
                }else{
                    item.roundPos-=speed;
                    item.addPosition(-speed);
                }
            }
        }
      /*  if(needAnimate){
            if(roundPos==0|roundPos==height){
                needAnimate=false;
            }else if(roundPos>0){//
                if(roundPos>height/2){
                    if(roundPos<height){
                        if(roundPos>height-speed){
                            position1+=height-roundPos;
                            roundPos+=height-roundPos;
                            needAnimate=false;
                        }else{
                            position1+=speed;
                            roundPos+=speed;
                        }
                    }
                }else{
                    if(roundPos>0){
                        if(roundPos<speed){
                            position1-=roundPos;
                            roundPos-=roundPos;
                            needAnimate=false;
                        }else{
                            position1-=speed;
                            roundPos-=speed;
                        }
                    }
                }
            }else{
                if(roundPos<=-(height/2)){
                    if(roundPos>-height){
                        if(roundPos<-height+speed){
                            position1-=height+roundPos;
                            roundPos-=height+roundPos;
                            needAnimate=false;
                        }else {
                            position1 -= speed;
                            roundPos -= speed;
                        }
                    }
                }else{
                    if(roundPos<0){
                        if(roundPos>-speed){
                            position1-=roundPos;
                            roundPos-=roundPos;
                            needAnimate=false;
                        }else {
                            position1 += speed;
                            roundPos += speed;
                        }
                    }
                }
            }


        }*/

////////////////scroll
        if(actionUP){
           if(scroll()){
                this.postInvalidateDelayed(1000 / 25);
            }else if(gravity()){
                this.postInvalidateDelayed(1000 / 25);
            }
        }


////////////////gravity
        if(needAnimate) {

           /* for(int i=0;i<numbersArray.size();i++){
                ((Item)numbersArray.get(i)).gravity();
            }*/
        }
//////////////animate
        if(needAnimate) {
           // this.postInvalidateDelayed(1000 / 25);
        }

    }
    void checkLoop(float delta){
        int posit;
        int currentPos;
        int a=0;
        int b=0;
      /*  if(position>0){
            a=1;
            previousPosition=previousPosition+(numbers.length)*itemHeight;
            currentPos=(numbers.length)*itemHeight-posit;
        }else if(position<-(numbers.length)*itemHeight){
            b=1;
            previousPosition=previousPosition-numbers.length*itemHeight;
            currentPos=posit-(numbers.length)*itemHeight;
        }else{
            currentPos=posit;
        }*/
        posit=Math.abs(position);
        currentPos=posit;
        int nextPos = (int)(position+delta);

        if(observer!=null){
            if(nextPos<=-itemHeight*1.5&position>-itemHeight*1.5) {
                observer.onEndIndex();

            }else if(nextPos<=-numbers.length*itemHeight-itemHeight*1.5){
                observer.onEndIndex();
            }else if(nextPos>-itemHeight*1.5&position<=-itemHeight*1.5){
                observer.onStartIndex();
            }

        }

        //////////////
       /* if(position>0){
            posit=position-(numbers.length)*itemHeight;
        }else if(position<-(numbers.length)*itemHeight){
            posit=position+(numbers.length)*itemHeight;
        }*/


        ////
     /*   if((observer!=null)&((currentPos>=itemHeight&previousPosition<=itemHeight)|(currentPos>=(numbers.length)*itemHeight&previousPosition<=(numbers.length)*itemHeight-itemHeight))){
             observer.onEndIndex();
        }
        if((observer!=null)&((currentPos<=itemHeight&previousPosition>=itemHeight)|(currentPos>=(numbers.length)*itemHeight-itemHeight&previousPosition>=(numbers.length)*itemHeight))){
              observer.onStartIndex();
        }*/

       /* if(observer!=null){
            if(currentPos>=itemHeight&previousPosition<=itemHeight){
                observer.onEndIndex();
            }else if(currentPos>=(numbers.length)*itemHeight&previousPosition<=(numbers.length)*itemHeight-itemHeight){
                observer.onEndIndex();
            }
        }
        if(observer!=null){
            if(currentPos<=itemHeight*2&previousPosition>=itemHeight*2){
                observer.onStartIndex();
            }else if(currentPos<=(numbers.length)*itemHeight-itemHeight&previousPosition>=(numbers.length)*itemHeight-itemHeight){
               // observer.onStartIndex();
            }
        }*/
        /////////////////////////////////////////






        ;
    }
    boolean scroll(){
        if(scrollspeed>0){
            if(scrollspeed-initScrollSpeed/10<0){
                scrollspeed=0;
                return false;
            }else{
                checkLoop(-scrollspeed*50/1000);
                position+=-scrollspeed*50/1000;
                scrollspeed-=initScrollSpeed/10;
               // checkLoop(position);
                return true;
            }
        }else if(scrollspeed<0){
            if(scrollspeed-initScrollSpeed/10>0){
                scrollspeed=0;
                return false;
            }else{
                checkLoop(-scrollspeed*50/1000);
                position+=-scrollspeed*50/1000;
                scrollspeed+=-initScrollSpeed/10;
               // checkLoop(position);
                return true;
            }
        }else{
            return false;
        }
        /////////////////////////////////////
      /*  if(scrollspeed==0){
            needAnimate=true;
        }else if(scrollspeed!=-1){
            if(scrollspeed!=0){
                if(scrollspeed>0){
                    if(scrollspeed-initScrollSpeed/10<0){
                        scrollspeed=0;
                        needAnimate=true;
                    }else{
                        needAnimate=false;
                      /*  for(int i=0;i<numbersArray.size();i++){
                            ((Item)numbersArray.get(i)).addPosition(-scrollspeed*50/1000);
                        }*/
                      /*  position+=-scrollspeed*50/1000;
                        /////////////////
                        scrollspeed-=initScrollSpeed/10;

                    }
                }else{
                    if(scrollspeed-initScrollSpeed/10>0){
                        scrollspeed=0;
                        needAnimate=true;
                    }else{
                        needAnimate=false;
                        /////////////////
                        position+=-scrollspeed*50/1000;
                        /////////////////
                      /*  for(int i=0;i<numbersArray.size();i++){
                            ((Item)numbersArray.get(i)).addPosition(-scrollspeed*50/1000);
                        }*/
                     /*   scrollspeed+=-initScrollSpeed/10;
                    }
                }

            }
        }*/
        /////////////////////////////////////
    }
    boolean gravity(){
        int tmp1=position/itemHeight;
        int tmp2=itemHeight*tmp1;
        int tmp3=position-tmp2;
        int itemPos=Math.abs(position-itemHeight*(position/itemHeight));
        if(itemPos!=0&itemPos!=itemHeight){
            if(itemPos>itemHeight/2){
                if(itemPos+gravitySpeed>itemHeight){
                    checkLoop(-(itemHeight-itemPos));
                    position-=itemHeight-itemPos;
                    invalidate();
                    return false;
                }else{
                    checkLoop(-gravitySpeed);
                    position-=gravitySpeed;
                    return true;
                }

            }else{
                if(itemPos-gravitySpeed<0){
                    checkLoop(itemPos);
                    position+=itemPos;
                    invalidate();
                    return false;
                }else{
                    checkLoop(gravitySpeed);
                    position+=gravitySpeed;
                    return true;
                }
            }
        }else{
            return false;
        }


    }
  /*  class Item{
        int index;
        int pos=0;
        String string;
        int roundPos;
       // int previousIndex;
        Item(int[] numbers,int ind){

            string=String.valueOf(numbers[ind]);
        }
        void gravity(){

                roundPos=(int)(pos-(itemHeight*(int)(pos/itemHeight)));
                if(roundPos>itemHeight/2){
                    if(roundPos+speed>itemHeight){
                        addPosition((int)(itemHeight-roundPos));
                        needAnimate=false;
                        scrollspeed=-1;
                        invalidate();


                    }else{
                        roundPos+=speed;
                        addPosition(speed);
                    }
                }else{
                    if(roundPos-speed<0){
                        addPosition((int)(-roundPos));
                        needAnimate=false;
                        scrollspeed=-1;
                        invalidate();

                    }else{
                        roundPos-=speed;
                        addPosition(-speed);
                    }
                }

        }
        void addPosition(int position){
            int posDelta=(int)(Math.abs(position)/itemHeight);
            pos+=position;
            if(pos>canvasH){
                int tmp1=(int)(pos/(canvasH+itemHeight));
                int tmp2=(int)((canvasH+itemHeight)*tmp1);
                int tmp3=pos-tmp2;
                pos=tmp3;
                pos=(int)itemHeight;
              //  pos=(int)(pos-(canvasH+itemHeight)*(pos/(canvasH+itemHeight)));//                       (int)(pos-(canvasH+itemHeight));

                backIndex-=posDelta+1;
                forwardIndex-=posDelta+1;
                if(backIndex<0){
                    backIndex=numbers.length-1-Math.abs(backIndex);
                }
                if(forwardIndex<0){
                    forwardIndex=numbers.length-1-Math.abs(forwardIndex);
                }


                string=String.valueOf(numbers[backIndex]);
            }



            /*
            if(pos+position>canvasH+itemHeight){
                pos=(int)((pos+position)-(canvasH+itemHeight));
                backIndex--;
                if(backIndex<0){
                    backIndex=numbers.length-1;


                }
                forwardIndex--;
                if(forwardIndex<0){
                    forwardIndex=numbers.length-1;
                }
              //  previousIndex=index;
                index=backIndex;
                string=String.valueOf(numbers[backIndex]);

            }else if(pos+position<0){
               pos=(int)((canvasH+itemHeight)+(pos+position));

                forwardIndex++;
                if(forwardIndex>numbers.length-1){
                    forwardIndex=0;


                }
                backIndex++;
                if(backIndex>numbers.length-1){
                    backIndex=0;
                }
               // previousIndex=index;
                index=forwardIndex;
                string=String.valueOf(numbers[forwardIndex]);
            }else{
                pos+=position;
                if(pos>itemHeight&pos<=itemHeight*2){
                    previousIndex=currentIndex;
                    currentIndex=index;
                }
                // roundPos=(int)(pos-(itemHeight*(int)(pos/itemHeight)));
            }
            */
     /*   }

    }*/
    int myDraw(int posit,int ind){

        int posit2=0;
        int tmp1=(posit/itemHeight);
        int tmp2=(itemHeight*tmp1);
        posit2=(posit+(itemHeight*ind))-tmp2;




       // posit2=((posit+(itemHeight*ind))-itemHeight*((posit+(itemHeight*ind))/(itemHeight)));

      return posit2;
    }

    int index(int posit,int ind){
        int index=0;



     /*   int tmp1=(posit/itemHeight);
        int tmp2=(itemHeight*tmp1);
        int tmp3=(posit+(itemHeight*ind))-tmp2;
      //  index=tmp3/itemHeight;

        int tmp4=posit-(itemHeight*ind);
        if(posit-(itemHeight*ind)<=canvasH+itemHeight&posit<=0){
            index=(posit-(itemHeight*ind))/itemHeight;
        }else if(posit>0&posit-(itemHeight*ind)<0){
            index=((posit-itemHeight*ind)-(itemHeight*ind))/itemHeight;
        }*/
       // index=(posit-(itemHeight*ind))/itemHeight;

       // AlarmDialog.debugView1.setText("posit: "+String.valueOf(posit));
       /* if(index>0&index<1){
            index=numbers.length-1;
        }else if(index>0){
            //index=numbers.length-(numbers.length+index);
            index=numbers.length-1-index;
        }else{
            index=-index;
        }*/
        int posit2=0;
        int tmp1=(posit/itemHeight);
        int tmp2=(itemHeight*tmp1);
     //   index=((posit+(itemHeight*ind))-tmp2)/itemHeight;
        index=((posit-(itemHeight*ind)))/(itemHeight);

      //  index=((posit-(itemHeight*ind))/itemHeight);

      //  index=((posit-(itemHeight*ind))/itemHeight);
        if(index>-4){
;            index=(numbers.length-index)-correction;
        }else if(index<-3){
            index+=correction;
        }

       /* if(index<=0){
            index=-index;
        }else{
            index=numbers.length-1-index;
        }*/
       // index=-index;
        //return Math.abs(index);
        ///////////////
        if(index<-numbers.length+1||index>numbers.length-1){
            index=0;
        }
        ///////////////
        return Math.abs(index);
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            actionUP=false;
            scrollspeed=0;
            initPosition=motionEvent.getY();
            position1=(int)motionEvent.getY();
            actdownTime = motionEvent.getEventTime();
        }
        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            delta=(int)(motionEvent.getY()-initPosition);
            initPosition=motionEvent.getY();
            checkLoop(delta);
            position+=delta;


            ///////////////////////////
         /*   if(position>0){
                position=position-(numbers.length)*itemHeight;
            }else if(position<-(numbers.length)*itemHeight){
                position=position+(numbers.length)*itemHeight;
            }*/
            /////////////////////////////
          /*  for(int i=0;i<numbersArray.size();i++){
                ((Item)numbersArray.get(i)).addPosition((int)delta);
            }*/
           // item.addPosition((int)delta);

            invalidate();
        }
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            actionUP=true;
           /* roundPos=position1/height;
            roundPos=position1-height*(int)(position1/height);*/
          //  roundPos=(int)(pos-(itemHeight*(int)(pos/itemHeight)));
            long time1=motionEvent.getEventTime() - actdownTime;
            float pos=motionEvent.getY();
            int distance=(int)(position1 - motionEvent.getY());
            if ((motionEvent.getEventTime() - actdownTime < 300)&& (Math.abs(position1 - motionEvent.getY()) > itemHeight)) {
                long time=motionEvent.getEventTime() - actdownTime;
                initScrollSpeed = (int) ( distance*1000/time);
                scrollspeed = initScrollSpeed;

            } else {
                scrollspeed = 0;

            }

            invalidate();
        }
        return true;
    }
}
