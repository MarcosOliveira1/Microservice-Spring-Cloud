package com.cloud.auth.controller;

import com.cloud.auth.jwt.JwtTokenProvider;
import com.cloud.auth.repository.UserRepository;
import com.cloud.auth.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @GetMapping("/testeSecurity")
    public String teste() {
        return "testado";
    }

    @PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
            "application/json", "application/xml", "application/x-yaml" })
    public ResponseEntity<?> login(@RequestBody UserVO vo) {
        try {
            var userName = vo.getUserName();
            var pass = vo.getPassword();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, pass));

            var user = userRepository.findByUserName(userName);

            var token = "";
            if (user != null) {
                token = jwtTokenProvider.createToken(userName, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Usuario não encontrado!");
            }

            Map<Object, Object> model = new HashMap<>();
            model.put("username", userName);
            model.put("token", token);

            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Erro ao logar");
        }
    }
}
