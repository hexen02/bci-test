package com.bci.apps.bcitest.controller;

import org.hibernate.HibernateError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.bci.apps.bcitest.in.UserIn;
import com.bci.apps.bcitest.out.ErrorOut;
import com.bci.apps.bcitest.out.LoginOut;
import com.bci.apps.bcitest.service.UserService;
import com.bci.apps.bcitest.util.JwtUtil;

@RestController()
public class RegisterController {

  @Autowired
  UserService service;

  private static Logger logger = LoggerFactory.getLogger(RegisterController.class);

  @PostMapping(path = "/user", produces = "application/json")
  public ResponseEntity<Object> userRegister(@RequestHeader("token") String token,
      @RequestBody UserIn userIn) {
    logger.info("/user: userRegister: " + userIn);
    logger.info("Token: " + token);

    if (token == null || !JwtUtil.checkToken(token)) {
      return new ResponseEntity<>(new ErrorOut("El token es nulo o inválido"),
          HttpStatus.UNAUTHORIZED);
    }

    try {
      return new ResponseEntity<>(service.saveUser(userIn, token), HttpStatus.OK);
    } catch (HibernateError e) {
      return new ResponseEntity<>(new ErrorOut(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorOut(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(path = "/login", produces = "application/json")
  public ResponseEntity<Object> login(@RequestBody UserIn userIn) {

    logger.info("Verifying user");
    if (!service.validateUser(userIn.getEmail(), userIn.getPassword())) {
      return new ResponseEntity<>(new ErrorOut("User not found or wrong password"),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    String token = "";

    try {
      token = JwtUtil.getToken(userIn.getEmail());
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorOut("Error al generar el token: " + e.getMessage()),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(new LoginOut("login successful", token), HttpStatus.OK);
  }
  
  @PostMapping(path = "/getAllUsers", produces = "application/json")
  public ResponseEntity<Object> getAllUsers(@RequestHeader("token") String token ) {

    logger.info("getAllUsers");
    logger.info("Token: " + token);

    if (token == null || !JwtUtil.checkToken(token)) {
      return new ResponseEntity<>(new ErrorOut("El token es nulo o inválido"),
          HttpStatus.UNAUTHORIZED);
    }

    try {
      return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    } catch (HibernateError e) {
      return new ResponseEntity<>(new ErrorOut(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      return new ResponseEntity<>(new ErrorOut(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
