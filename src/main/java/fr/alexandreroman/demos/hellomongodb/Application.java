/*
 * Copyright (c) 2019 Pivotal Software, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.alexandreroman.demos.hellomongodb;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Locale;

@SpringBootApplication
@EnableMongoRepositories
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@RestController
@Slf4j
class HelloController {
    private final AccessLogRepository repo;
    private final DateFormatter df;

    public HelloController(final AccessLogRepository repo) {
        this.repo = repo;
        df = new DateFormatter();
        df.setIso(DateTimeFormat.ISO.DATE_TIME);
    }

    @GetMapping("/")
    String hello(HttpServletRequest req, Locale locale) {
        AccessLog accessLog = new AccessLog();
        accessLog.setRequestReceived(new Date());
        accessLog.setClientAddress(req.getRemoteAddr());

        accessLog = repo.save(accessLog);
        log.info("Updated MongoDB access log entity: {}", accessLog);

        return "Hello world: current time is " + df.print(accessLog.getRequestReceived(), locale);
    }
}

interface AccessLogRepository extends MongoRepository<AccessLog, Long> {
}

@Data
@Document
class AccessLog {
    @Id
    private String id;
    private String clientAddress;
    private Date requestReceived;
}
