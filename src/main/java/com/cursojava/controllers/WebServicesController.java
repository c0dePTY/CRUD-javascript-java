/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cursojava.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alejandro
 */
@RestController
public class WebServicesController {

    @GetMapping("/rest")
    public String takeRest(){
        System.out.println("Resting...");
        return "Rest is only necessary - Not a goal!";
    
    }

}
