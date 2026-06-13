/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import service.authservice;


/**
 *
 * @author Lenovo
 */
public class userinjector {
    public static void main(String[] args) {
        authservice guruService = new authservice();
        guruService.registerUser("happy citra lestari", "haceel", "admin123");
    }
}
