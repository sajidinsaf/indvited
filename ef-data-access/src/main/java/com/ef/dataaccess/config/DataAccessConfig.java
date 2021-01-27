package com.ef.dataaccess.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ef.dataaccess.common.UuidGenerator;

@Configuration
//@EnableWebMvc
//@PropertySource("classpath:controller.properties")
@ComponentScan("com.ef.dataaccess")
public class DataAccessConfig implements WebMvcConfigurer {

  private static final Logger logger = LoggerFactory.getLogger(DataAccessConfig.class);

  @Bean
  public JdbcTemplate indvitedDbJdbcTemplate() throws SQLException {
    return new JdbcTemplate(getMySqlDataSource());
  }

  public DataSource getMySqlDataSource() throws SQLException {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://mysql3000.mochahost.com:3306/ifaru02_indvited?serverTimezone=UTC");
    dataSource.setUsername("ifaru02_indvited");
    dataSource.setPassword("@SilverGun95@");
    return dataSource;

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UuidGenerator uuidGenerator() {
    return new UuidGenerator();
  }
}
