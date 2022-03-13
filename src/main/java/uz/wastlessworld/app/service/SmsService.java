package uz.wastlessworld.app.service;// Author - Orifjon Yunusjonov
// t.me/coderr24

import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import uz.wastlessworld.app.entity.helpers.SmsConstant;
import uz.wastlessworld.app.json.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@EnableScheduling
@Configuration
public class SmsService {

    @Scheduled(fixedDelay = 50000L)
    public void getToken() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "https://notify.eskiz.uz/api/auth/login";
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", "test@eskiz.uz");
        params.put("password", "j6DWtQjjpLDNjWEk74Sx");
        ResponseEntity<Result> response
                = restTemplate.postForEntity(fooResourceUrl, params, Result.class);
        SmsConstant.setToken(Objects.requireNonNull(response.getBody()).getData().getToken());

        System.out.println(SmsConstant.getToken());
    }
}
