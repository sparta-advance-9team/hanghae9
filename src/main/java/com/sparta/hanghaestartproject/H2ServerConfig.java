package com.sparta.hanghaestartproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.h2.tools.Server;

import java.sql.SQLException;

@Configuration
public class H2ServerConfig {
     @Bean
     public Server h2TcpServer() throws SQLException {
          return Server.createTcpServer().start();
     }
}
