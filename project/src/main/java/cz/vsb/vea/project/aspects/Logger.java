package cz.vsb.vea.project.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Aspect
@Component
public class Logger {

    private static final String fileName = "log.txt";

    @Autowired
    ConversionService conversionService;

    @Before("execution(* cz.vsb.vea.project.controllers..*(..))")
    public void log(JoinPoint joinPoint) {
        LocalDateTime localDateTime = LocalDateTime.now();

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.append("Action: " + joinPoint.getSignature() + "\nTime: " + conversionService.convert(localDateTime, String.class) + "\n\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
