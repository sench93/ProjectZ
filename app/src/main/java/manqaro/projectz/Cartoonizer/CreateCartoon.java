package manqaro.projectz.Cartoonizer;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Debug;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

import manqaro.projectz.R;

/**
 * Created by Admin on 9/13/2016.
 */
public class CreateCartoon extends AsyncTask<ArrayList<Object>,Bitmap,Bitmap> {

    private Bitmap source,result;
    private Canvas c;
    private Paint p;
    private ImageView tempImageView;
    private ProgressDialog progressDialog;
    private Context context;
    private int tobeCartoonizedDrawable;

    public CreateCartoon(Context context){
        this.context = context;
        progressDialog = new ProgressDialog(this.context);
        tobeCartoonizedDrawable = R.drawable.pryanka;
    }

    @Override
    protected Bitmap doInBackground(ArrayList<Object>... arrayLists) {
        initFields((Context) arrayLists[0].get(0));
        tempImageView = (ImageView) arrayLists[0].get(1);
        return startCreateingCartoon((int)arrayLists[0].get(2));
    }

    @Override
    protected void onPostExecute(Bitmap bmp) {
        tempImageView.setImageBitmap(bmp);
        progressDialog.dismiss();
        progressDialog.cancel();
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setMessage("Loading . . . Please Wait.");
        progressDialog.show();
    }


    private void initFields(Context context){

        source = BitmapFactory.decodeResource(context.getResources(),
                tobeCartoonizedDrawable);
        result = Bitmap.createBitmap(source.getWidth(), source.getHeight(),  Bitmap.Config.ARGB_8888);
        c = new Canvas(result);
        p = new Paint();
    }


    private Bitmap startCreateingCartoon(int position){
        float tempR=0.45f;
        float tempG=0.5f;
        float tempB=0.45f;
        float brightness;
        Bitmap tmp;

        Log.d("bzz","Case5 "  + source.toString());
        p.setColorFilter(new ColorMatrixColorFilter(createGreyMatrix(tempR, tempG, tempB)));
        c.drawBitmap(source, 0, 0, p);
        p.setColorFilter(new ColorMatrixColorFilter(createThresholdMatrix(128)));
        c.drawBitmap(result, 0, 0, p);
        tmp = colorCartoon();
        brightness= brightnessLevel(tmp);
        while(brightness>20 && brightness>30){
            if(brightness-32.5f>8){
                tempR+=0.1f;
                tempG+=0.1f;
                tempB+=0.1f;
            }

            else{
                tempR+=0.05f;
                tempG+=0.05f;
                tempB+=0.05f;

            }
            Log.d("bzz",String.valueOf(tempR) + "/"+String.valueOf(tempG) + "/"+String.valueOf(tempB));
            p.setColorFilter(new ColorMatrixColorFilter(createGreyMatrix(tempR,tempG,tempB)));
            c.drawBitmap(source, 0, 0, p);
            p.setColorFilter(new ColorMatrixColorFilter(createThresholdMatrix(128)));
            c.drawBitmap(result, 0, 0, p);
            tmp = colorCartoon();
            brightness= brightnessLevel(tmp);
        }
        while(brightness<=20){
            if(brightness-30<-8){
                if(tempG>0.35f){

                    tempG-=0.1f;
                }
                if(tempR>0.2f){

                    tempR-=0.1f;
                }
                if(tempB>0.1f){

                    tempB-=0.05f;
                }
                else{
                    break;
                }

            }
            else{
                if(tempG>0.35f){

                    tempG-=0.05f;
                }
                if(tempR>0.15f){

                    tempR-=0.05f;
                }
                if(tempB>0.7f){

                    tempB-=0.02f;
                }
                else{
                    break;
                }
            }
            Log.d("bzz",String.valueOf(tempR) + "/"+String.valueOf(tempG) + "/"+String.valueOf(tempB));
            p.setColorFilter(new ColorMatrixColorFilter(createGreyMatrix(tempR,tempG,tempB)));
            c.drawBitmap(source, 0, 0, p);
            p.setColorFilter(new ColorMatrixColorFilter(createThresholdMatrix(128)));
            c.drawBitmap(result, 0, 0, p);
            tmp = colorCartoon();
            brightness= brightnessLevel(tmp);
        }
        return tmp;
    }


    private static ColorMatrix createGreyMatrix(float r,float g , float b) {

        return  new  ColorMatrix(new float[] {
                r, g, b, 0, 0,
                r, g, b, 0, 0,
                r, g, b, 0, 0,
                0, 0, 0, 1, 0
        });
    }

    private static ColorMatrix createThresholdMatrix(int threshold) {

        return  new ColorMatrix(new float[] {
                90f, 90f, 90f, 0.f, -255.f * threshold,
                90f, 90f, 90f, 0.f, -255.f * threshold,
                90f, 90f, 90f, 0.f, -255.f * threshold,
                0f, 0f, 0f, 1f, 0f
        });
    }
    private Bitmap colorCartoon(){

        int [] allpixels = new int [result.getHeight()*result.getWidth()];
        result.getPixels(allpixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        for(int i = 0; i < allpixels.length; i++)
        {
            if(allpixels[i] != Color.BLACK &&  allpixels[i]!=Color.TRANSPARENT)
            {
                allpixels[i] = Color.parseColor("#d9bb99");
            }
        }

        result.setPixels(allpixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return  result;

    }


    private float brightnessLevel(Bitmap a){

        int[] histogram=new int[256];
        for (int i=0;i<256;i++) {
            histogram[i] = 0;
        }

        for (int x = 0; x < a.getWidth(); x++) {
            for(int y = 0; y < a.getHeight(); y++) {
                int color = a.getPixel(x, y);

                if(color!=Color.TRANSPARENT){
                    int r = Color.red(color);
                    int g = Color.green(color);
                    int b = Color.blue(color);

                    int brightness = (int) (r+r+r+b+g+g+g+g)>>3;
                    histogram[brightness]++;
                }

            }
        }

        int allPixelsCount = a.getWidth() * a.getHeight();
        int darkPixelCount = 0;
        for (int i=0;i<10;i++) {
            darkPixelCount += histogram[i];
        }

            Log.d("bzz",String.valueOf((float)(darkPixelCount*100)/(allPixelsCount+1)));
            return  (float)(darkPixelCount*100)/(allPixelsCount+1);


    }


}
