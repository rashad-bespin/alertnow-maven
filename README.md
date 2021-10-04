<h3>AlertNow Maven Plugin</h3>

<h5>1. Sign up on AlertNow and get API Key.</h5>

<h5>2. Create new maven application and add AlertNow dependency to your project’s
pom.xml file.</h5>
---
<dependency>
   <groupId>com.bespinglobal.alertnow</groupId>
   <artifactId>log-collector</artifactId>
   <version>{version}</version>
</dependency>
---

<h5>3. Init AlertNow configs</h5>
```
import com.bespinglobal.alertnow.logcollector.AlertNow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlertNowConfig {
    @Bean
    public void alertNowConfiguration() {
        AlertNow.init(options -> {
            options.setApiKey("api key");
            options.setHost("host");
            options.setRelease("package name");
        });
    }
}
```

<h5>4. Write your logs and information using AlertNow.</h5>
```
import com.bespinglobal.alertnow.logcollector.AlertNow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
@RequestMapping(value = "/test")
public class TestLogController {

    @GetMapping(value = "/log")
    public String test() {
        try {
            throw new Exception("This is a Exception.");
        } catch (Exception e) {
            AlertNow.info(e.getMessage());
        }
        
        try {
            throw new IOException("This is a IOException.");
        } catch (IOException e) {
            AlertNow.error(e);
        }
        
        return "Tested successfully!";
    }
}
```