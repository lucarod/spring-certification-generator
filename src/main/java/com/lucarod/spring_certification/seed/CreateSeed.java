package com.lucarod.spring_certification.seed;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class CreateSeed {
  private final JdbcTemplate jdbcTemplate;

  public CreateSeed(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public static void main(String[] args) {
    var dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl("jdbc:postgresql://localhost:5434/pg_nlw");
    dataSource.setUsername("admin");
    dataSource.setPassword("admin");

    var createSeed = new CreateSeed(dataSource);
    createSeed.run(args);
  }

  public void run(String[] args) {
    executeSqlFile("src/main/resources/create.sql");
  }

  private void executeSqlFile(String filePath) {
    try {
      var sqlScript = new String(Files.readAllBytes(Paths.get(filePath)));

      jdbcTemplate.execute(sqlScript);
      System.out.println("Seed realizado com sucesso");
    } catch (IOException error) {
      System.err.println("Erro ao executar o arquivo " + error.getMessage());
    }
  }
}
