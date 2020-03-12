package com.avirias.pi_server;

import com.avirias.pi_server.dto.ConnectDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: avirias
 * Date: 12/03/20 12:27 PM
 */

@RestController
public class Demo {

    @GetMapping
    public String getHello() {
        return "Hello World";
    }

    @PostMapping("/connect")
    public ResponseEntity connect(@RequestBody ConnectDto connectDto) {

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "curl -w \"\\n\" -d '{\"ssid\":\"" + connectDto.getSsid() + "\", \"psk\":\""
                + connectDto.getPassword() + "\"}' \\\n" +
                "     -H \"Content-Type: application/json\" \\\n" +
                "     -X POST localhost:8080/connect");

        try {

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
                System.exit(0);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Connected");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }
}
