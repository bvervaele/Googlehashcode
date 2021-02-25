package be.dhofief.farmwatchbackend;

import be.dhofief.farmwatchbackend.service.Task1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
@ComponentScan(basePackages = "be.dhofief")
@EnableJpaRepositories
public class GCODEApplication {

    private static String file = "a.txt";
    private static String outputFile = "a_output.txt";
    private static String basePath = "C:\\Users\\Timber\\GCODE\\";

    public static void main(String[] args) throws IOException {
        SpringApplication.run(GCODEApplication.class, args);
        Collection<String> input = readFile(basePath + file);
        Task1 task1 = new Task1(input, new FileWriter(basePath + outputFile));
        System.out.println("done");
    }

    private static Collection<String> readFile(String absolutePath){
        Collection<String> lines = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    absolutePath));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

}
