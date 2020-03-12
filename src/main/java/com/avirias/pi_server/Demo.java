package com.avirias.pi_server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: avirias
 * Date: 12/03/20 12:27 PM
 */

@RestController
public class Demo {

    @GetMapping
    public String getHello(){
        return  "Hello World";
    }
}
