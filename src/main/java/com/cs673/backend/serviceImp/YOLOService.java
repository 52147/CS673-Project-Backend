package com.cs673.backend.serviceImp;

import jep.Jep;
import jep.JepConfig;
import jep.JepException;
import org.opencv.core.Core;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class YOLOService {
    private Net net;
    private ThreadLocal<Jep> jep;

    public YOLOService() throws JepException {

        jep = new ThreadLocal<Jep>() {
            @Override
            protected Jep initialValue() {
                try {
                    JepConfig config = new JepConfig();
                    config.addIncludePaths("C:\\Users\\15221\\anaconda3\\envs\\ml\\lib\\site-packages");
                    Jep jepInstance = new Jep(config);
                    jepInstance.eval("import sys");
                    jepInstance.eval("sys.path.insert(0, 'src/main/java/com/cs673/backend/py')");
                    jepInstance.runScript("src/main/java/com/cs673/backend/py/demo.py");
                    return jepInstance;
                } catch (JepException e) {
                    throw new RuntimeException("Failed to initialize Jep instance", e);
                }
            }
        };
    }


    public String detectObjects(String image_path) throws JepException {
        try {
            return (String) jep.get().invoke("main", image_path);
        } catch (JepException e) {
            System.err.println("Error while invoking the 'main' function in the Python script:");
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }

    }
}
