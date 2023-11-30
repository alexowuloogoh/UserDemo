package com.project3.UserDemo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;

import static java.nio.file.Files.lines;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/users")
public class RestController {

    File messageFile = new File("messages.txt");
    File newFile = new File("log.txt");

    @GetMapping("/message")
    public String getMessage(@RequestBody String message){

        StringBuilder read = new StringBuilder();
        String readfrom;

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(messageFile))){
            while ((readfrom = bufferedReader.readLine()) != null){
                read.append(readfrom);
            }
        } catch (IOException exception){
            System.out.println(exception.getMessage());
        }

        return read.toString();
    }

    @GetMapping("/messageCount")
    public long getMessageCount() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(newFile))) {
            return bufferedReader.lines().count();
        }
    }

    @PostMapping("/message")
    public String postMessage(@RequestBody String message){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(messageFile, true))){
            bufferedWriter.write(message + "End");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return logActivity();
    }

    private String logActivity(){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile, true))){
            bufferedWriter.write("New message created");
            bufferedWriter.newLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return "done";
    }

}