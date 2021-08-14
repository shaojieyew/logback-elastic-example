package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
public class HelloController {

    Marker HTTP_REQUEST = MarkerFactory.getMarker("HTTP_REQUEST");
    Marker METHOD = MarkerFactory.getMarker("METHOD");
    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Value("${application.env}") String env;

    private static final String template = "Hello, %s!";
    @GetMapping("/greeting")
    public String greeting(@RequestParam(value="name",
            defaultValue="World") String name) {

        logger.info(HTTP_REQUEST,"url='http://abc.com'");

        logger.info(METHOD,"name='"+name+"',msg='"+String.format(template, name)+"'");

        return  String.format(template, name) +"</br>"
                +"the environment is "+env;    }
}