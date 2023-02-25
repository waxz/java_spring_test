package com.example.application;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

import io.reactivex.rxjava3.core.*;



@Controller
@EnableAutoConfiguration
public class Hello {
    private static final Logger logger =
            LoggerFactory.getLogger(Hello.class);
    int count = 0;
    @RequestMapping("test")
    @ResponseBody
    public Map<String,String> test(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("hello","you have click " + count );

        logger.info("get request count " + count);

        count+=1;
        return map;

    }

    public static void startServer(String[] args) throws  Exception{
        SpringApplication.run(Hello.class,args);
    }

    public static void main(String[] args) throws  Exception{



        List<String> letters = Arrays.asList("A", "B", "C", "D", "E");


        Flowable<String> source = Flowable.fromCallable(() -> {
            Thread.sleep(1000); //  imitate expensive computation
            return "Done";
        });

        Flowable<String> runBackground = source.subscribeOn(Schedulers.io());

        Flowable<String> showForeground = runBackground.observeOn(Schedulers.single());

        showForeground.subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(2000);


        {
            Flowable.range(1, 10)
                    .flatMap(v ->
                            Flowable.just(v)
                                    .subscribeOn(Schedulers.computation())
                                    .map(w -> w * w)
                    )
                    .blockingSubscribe(System.out::println);
        }
        logger.info( "START " + Hello.class.getSimpleName() );

//        startServer(args);

    }
}
