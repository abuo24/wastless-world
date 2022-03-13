package uz.wastlessworld.app.controller;// Author - Orifjon Yunusjonov
// t.me/coderr24

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import uz.wastlessworld.app.entity.User;
import uz.wastlessworld.app.entity.helpers.ConfirmCode;
import uz.wastlessworld.app.entity.helpers.SmsConstant;
import uz.wastlessworld.app.json.SendSms;
import uz.wastlessworld.app.model.ResponseDto;
import uz.wastlessworld.app.model.Result;
import uz.wastlessworld.app.payload.LoginPayload;
import uz.wastlessworld.app.payload.UserPayload;
import uz.wastlessworld.app.repository.ConfirmCodeRepository;
import uz.wastlessworld.app.repository.UserRepository;
import uz.wastlessworld.app.security.JwtTokenProvider;
import uz.wastlessworld.app.service.UserService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600L)
public class AuthController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ConfirmCodeRepository confirmCodeRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginPayload payload) {
        int rand = new Random().nextInt(99999) + 100000;
        try {
            User user = userRepository.findByUsername(payload.getUsername()).orElse(null);
            if (user == null) {
                user = userService.save(new UserPayload(payload.getUsername(), null));
            }
            if (user == null) {
                throw new Exception("user not created");
            }
            confirmCodeRepository.save(new ConfirmCode(String.valueOf(rand), user));

            // write send sms logic to here
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(SmsConstant.getToken());
            String url = new String("https://notify.eskiz.uz/api/message/sms/send");
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobile_phone", payload.getUsername());
            params.put("message", String.valueOf(rand + " bu sms test uchun"));
            params.put("from", "4546");
            params.put("callback_url", "http://0000.uz/test.php");
            HttpEntity<?> request = new HttpEntity<>(params, headers);
            ResponseEntity<SendSms> response = restTemplate.postForEntity(url, request, SendSms.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String urlRes = "https://notify.eskiz.uz/api/message/sms/status/" + response.getBody().getId();
                request = new HttpEntity<>(headers);
                ResponseEntity<String> responseEntity = restTemplate.exchange(
                        urlRes,
                        HttpMethod.GET,
                        request,
                        String.class,
                        1
                );
                if (response.getStatusCode() == HttpStatus.OK) {
                    confirmCodeRepository.save(new ConfirmCode(String.valueOf(rand), user));
                    return ResponseEntity.ok(new Result(true, String.valueOf(rand)));
                } else {

                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

//            return ResponseEntity.ok("Sms code: "+user.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, e.getMessage(),rand));
        }
    }

    @PostMapping("/check")
    public ResponseEntity check(@RequestBody LoginPayload loginPayload) {
        return userService.checkCode(loginPayload.getUsername(), loginPayload.getCode());
    }

}
