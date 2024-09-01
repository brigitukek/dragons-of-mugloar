package com.brigita.dragons.of.mugloar;

import com.brigita.dragons.of.mugloar.services.GameHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Main implements CommandLineRunner {

    @Autowired
    private GameHandler gameHandler;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        gameHandler.playGame();

        System.exit(0);
    }
}
