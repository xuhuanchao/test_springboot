package com.xhc.test.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by xuhuanchao on 2018/5/22.
 */
@Component
public class DBInit {

    private static final Logger log = LoggerFactory.getLogger(DBInit.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void initDB(){
        log.info("******* Creating tables ********");

        jdbcTemplate.execute("DROP TABLE STUDENT IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE STUDENT(" +
                "id SERIAL auto_increment, first_name VARCHAR(255), last_name VARCHAR(255), level VARCHAR(10), age INTEGER, PRIMARY KEY (`id`) )");
        List a = new ArrayList();
        Stream stream = a.stream();
        // Split up the array of whole names into an array of first/last names
        List<Object[]> splitUpNames = Arrays.asList("John Woo 2 20", "Jeff Dean 3 24", "Josh Bloch 3 26", "Josh Long 2 21").stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        // Use a Java 8 stream to print out each tuple of the list
        splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate("INSERT INTO STUDENT(first_name, last_name, level, age) VALUES (?,?,?,?)", splitUpNames);


        log.info("******** init db complete. *********");

        /*log.info("Querying for customer records where first_name = 'Josh':");
        jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers WHERE first_name = ?", new Object[] { "Josh" },
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
        ).forEach(customer -> log.info(customer.toString()));*/
    }
}
