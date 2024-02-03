/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cursojava.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *
 * @author Alejandro
 */

@Controller
public class WebController {
    
    @RequestMapping("/hello") //bloque que se ejecuta al abrirse la pág /hello, como un Listener
    public String sayHello(Model model){
        System.out.println("greetings...");
        model.addAttribute("message", "este mensaje es el que aparecerá en la página /hello");
        return "hello";
    }
    
}
