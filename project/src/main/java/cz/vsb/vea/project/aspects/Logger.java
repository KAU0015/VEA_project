package cz.vsb.vea.project.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

@Aspect
@Component
public class Logger {

    private static final String fileName = "log.txt";

    @Before("execution(* cz.vsb.vea.project.controllers..*(..))")
    public void log(JoinPoint joinPoint) {
        Date date = new Date();

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.append("Action: " + joinPoint.getSignature() + "\nTime: " + date.toString() + "\n\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
