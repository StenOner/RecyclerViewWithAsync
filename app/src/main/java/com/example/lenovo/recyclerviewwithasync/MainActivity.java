package com.example.lenovo.recyclerviewwithasync;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.NumberPicker;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<Memes> memes;
    NumberPicker numberPicker;
    ArrayList<ReturningValues> returningValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberPicker = (NumberPicker)findViewById(R.id.nbp_generate);
        numberPicker.setMaxValue(20);
        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(false);
    }


    public void Generar(View view)
    {
        new GetImageText().execute();
    }
    public class GetImageText extends AsyncTask<Void,Integer,ArrayList<ReturningValues>>{

        ProgressDialog pd;
        @Override
        protected void onPreExecute(){
            pd=new ProgressDialog(MainActivity.this);
            pd.setTitle("Descargando...");
            pd.setMax(100);
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setCancelable(false);
            pd.show();
        }
        @Override
        protected ArrayList<ReturningValues> doInBackground(Void... voids){
            /*ArrayList<String> images= new ArrayList<>();
            ArrayList<String> texts= new ArrayList<>();
            for (int i=0;i<numberPicker.getValue();i++){
                images.add("https://picsum.photos/600/400/?random");
                texts.add("https://textfiles.com/computers/DOCUMENTATION/a00.txt");
            }*/

            ArrayList<Bitmap> bitmap = new ArrayList<>();
            ArrayList<String> desc = new ArrayList<>();
            returningValues = new ArrayList<>();
            try{
                int j=0;
                for (int i=0;i<numberPicker.getValue();i++){
                    publishProgress(j*50/numberPicker.getValue());
                    j++;
                    String imageUrl="https://picsum.photos/600/400/?random";
                    URL url = new URL(imageUrl);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    bitmap.add(BitmapFactory.decodeStream(input));

                    publishProgress(j*50/numberPicker.getValue());
                    j++;
                    /*String url2="http://www.randomtextgenerator.com/";
                    URL url2 = new URL(textUrl);
                    Scanner in = new Scanner(url2.openStream());
                    desc.add(in.nextLine());*/
                    //String textUrl = "https://stackoverflow.com/questions/2835505";
                    String textUrl = "https://www.rottentomatoes.com/m/avengers_infinity_war";
                    Document document = Jsoup.connect(textUrl).get();
                    Elements reviews = document.select(" #reviews .review_quote .media .media-body");
                    if (!reviews.isEmpty()){
                        desc.add(reviews.get(i).text());
                    }else{
                        desc.add("");
                    }

                    returningValues.add(new ReturningValues(desc.get(i),bitmap.get(i)));
                }
                /*for (String durl: images){
                    publishProgress(j*50/numberPicker.getValue());
                    j++;
                    try{
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    URL url = new URL(durl);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    bitmap.add(BitmapFactory.decodeStream(input));
                }
                for (String durl: texts){
                    publishProgress(j*50/numberPicker.getValue());
                    j++;
                    try{
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    URL url = new URL(durl);
                    Scanner in = new Scanner(url.openStream());
                    desc.add(in.nextLine());
                }

                for (int i=0;i<numberPicker.getValue();i++){
                    returningValues.add(new ReturningValues(desc.get(i),bitmap.get(i)));
                }*/
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return returningValues;
        }
        @Override
        protected void onProgressUpdate(Integer... integers){
            pd.setProgress(integers[0]);
        }
        @Override
        protected void onPostExecute(ArrayList<ReturningValues> result){
            RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
            rv.setHasFixedSize(true);

            LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
            rv.setLayoutManager(llm);
            memes = new ArrayList<>();
            int count=0;
            for (int i=0;i<numberPicker.getValue();i++){
                count++;
                memes.add(new Memes("Imagen "+count, result.get(i).desc, result.get(i).bitmap));
            }

            RVAdapter adapter = new RVAdapter(memes,getBaseContext());
            rv.setAdapter(adapter);
            pd.hide();
        }
    }
}
