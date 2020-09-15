package com.server;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BankSystemApplication implements ApplicationRunner{
	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(BankSystemApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		env.getProperty("spring.datasource.url");
		String aSQLScriptFilePath = "./src/main/resources/data.sql";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"),
				env.getProperty("spring.datasource.password"));
		try {
			ScriptRunner scriptRunner = new ScriptRunner(con);
			Reader reader = new BufferedReader(
					new FileReader(aSQLScriptFilePath));
			scriptRunner.runScript(reader);
		} catch (Exception e) {
			System.err.println("Failed to Execute" + aSQLScriptFilePath
					+ " The error is " + e.getMessage());
		}
	}

}