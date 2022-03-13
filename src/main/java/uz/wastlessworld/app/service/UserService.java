package uz.wastlessworld.app.service;// Author - Orifjon Yunusjonov
// t.me/coderr24

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.wastlessworld.app.entity.User;
import uz.wastlessworld.app.entity.helpers.ConfirmCode;
import uz.wastlessworld.app.exceptions.BadRequestException;
import uz.wastlessworld.app.exceptions.ResourceNotFoundException;
import uz.wastlessworld.app.model.Result;
import uz.wastlessworld.app.payload.UserPayload;
import uz.wastlessworld.app.repository.ConfirmCodeRepository;
import uz.wastlessworld.app.repository.RoleRepository;
import uz.wastlessworld.app.repository.UserRepository;
import uz.wastlessworld.app.security.JwtTokenProvider;

import java.util.*;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ConfirmCodeRepository confirmCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    public User save(UserPayload userPayload) {
        User user = new User();
        user.setUsername(userPayload.getUsername());
        user.setFullname(userPayload.getFullname());
        user.setPassword(passwordEncoder.encode(userPayload.getUsername()));
        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }

    public ResponseEntity<?> editById(UUID id, UserPayload userPayload) {
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("user not found: "+id));
            user.setFullname(userPayload.getFullname());
            return ResponseEntity.ok(userRepository.save(user));
        } else throw new BadRequestException("user not found id:" + id);
    }

    public ResponseEntity<?> forgotPassword(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        Random random = new Random();
        String code = String.valueOf(random.nextInt(999999 - 100000) + 100000);
        ConfirmCode confirmCode = new ConfirmCode();
        confirmCode.setCode(code);
//       confirmCode.setFinalDate(dateConfig.convertToDateViaSqlTimestamp(dateConfig.convertToLocalDateTimeViaInstant(new Date()).plusMinutes(5L)));
        confirmCode.setUser(user);
        ConfirmCode confirmC = confirmCodeRepository.save(confirmCode);
        if (confirmC.getId() == null) {
            return new ResponseEntity<>(new Result(false, "something went wrong. please try again"), HttpStatus.BAD_REQUEST);
        }

        // we need send sms method to username

        return ResponseEntity.ok(new Result(true, "code send to you"));
    }

    public ResponseEntity<?> checkCode(String username, String code) {
        try {

            User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("user not found"));
            List<ConfirmCode> confirmCodes = confirmCodeRepository.findAllByUser_UsernameOrderByDateDesc(username);
            ConfirmCode confirmCode = confirmCodes.get(0);
            if (confirmCode == null || !confirmCode.getCode().equals(code)) {
                return new ResponseEntity(new Result(false, "error"), HttpStatus.BAD_REQUEST);
            }

            if (code.equals(confirmCode.getCode())) {
                System.out.println(username);
                System.out.println(user.getPassword());
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,user.getUsername()));
                String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
                if (token == null) {
                    return new ResponseEntity(new Result(false, "something went wrong"), HttpStatus.BAD_REQUEST);
                }
                Map<String, Object> map = new HashMap();
                map.put("token", token);
                map.put("username", user.getUsername());
                map.put("success", true);
                return ResponseEntity.ok(map);
            };

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body(new Result(false, "something went wrong"));
    }

}
