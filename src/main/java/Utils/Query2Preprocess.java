package Utils;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;
import scala.Tuple3;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;



public class Query2Preprocess {


    public static JavaPairRDD<Tuple3<Integer, Integer, String>, Tuple2<Double,Double> > executeProcess(JavaSparkContext sc,JavaRDD<Tuple3<String,String,Double>> values, int fileType) {


        Map<String, Tuple2<String,String>> nations = Nations.retryNation(sc);

         return values
                .mapToPair(new PairFunction<Tuple3<String, String, Double>, Tuple3<Integer, Integer, String>, Tuple2<Double, Double>>() {
                    @Override
                    public Tuple2<Tuple3<Integer, Integer, String>, Tuple2<Double, Double>> call(Tuple3<String, String, Double> tuple) throws Exception {
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = df.parse(tuple._1());
                        GregorianCalendar cal = new GregorianCalendar();
                        cal.setTime(date);

                        return new Tuple2<>(new Tuple3<>(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), nations.get(tuple._2())._1() ), new Tuple2<>( ( (fileType == 0) & (tuple._3() > 400) ) ? tuple._3()/1000 : tuple._3() ,1.0)  );

                    }
                });
    }



}
