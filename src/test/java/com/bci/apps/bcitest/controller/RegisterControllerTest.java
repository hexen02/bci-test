package com.bci.apps.bcitest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateError;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.bci.apps.bcitest.in.PhoneIn;
import com.bci.apps.bcitest.in.UserIn;
import com.bci.apps.bcitest.out.LoginOut;
import com.bci.apps.bcitest.out.UserOUT;
import com.bci.apps.bcitest.service.UserService;

public class RegisterControllerTest {

  @Mock
  @Autowired
  UserService service;

  @InjectMocks
  @Autowired
  RegisterController controller;

  @Test
  public void testLoginOK() throws Exception {
    MockitoAnnotations.initMocks(this);

    UserIn userIn = new UserIn();
    userIn.setEmail("ferna650@hotmail.com");
    userIn.setPassword("asdf123");

    when(service.validateUser(userIn.getEmail(), userIn.getPassword())).thenReturn(true);

    ResponseEntity<Object> userOut = controller.login(userIn);

    assertNotNull(userOut);
    assertEquals(HttpStatus.OK, userOut.getStatusCode());
  }

  @Test
  public void testLoginInvalidUser() throws Exception {
    MockitoAnnotations.initMocks(this);

    UserIn userIn = new UserIn();
    userIn.setEmail("ferna650@hotmail.com");
    userIn.setPassword("asdf123");

    when(service.validateUser(userIn.getEmail(), userIn.getPassword())).thenReturn(false);

    ResponseEntity<Object> userOut = controller.login(userIn);

    assertNotNull(userOut);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, userOut.getStatusCode());
  }

  @Test
  public void testUserRegisterOK() throws Exception {
    MockitoAnnotations.initMocks(this);

    UserIn userIn = new UserIn();
    userIn.setEmail("ferna650@hotmail.com");
    userIn.setName("fernando");
    userIn.setPassword("123asdf");

    PhoneIn phone = new PhoneIn();
    phone.setCityCode("SCL");
    phone.setCountryCode("CL");
    phone.setIdUser(1);
    phone.setNumber("83568203");

    List<PhoneIn> phones = new ArrayList<PhoneIn>();
    phones.add(phone);

    userIn.setPhones(phones);

    when(service.validateUser(userIn.getEmail(), userIn.getPassword())).thenReturn(true);

    ResponseEntity<Object> response = controller.login(userIn);
    
    LoginOut loginOut = (LoginOut)response.getBody();

    String token = loginOut.getToken();

    when(service.saveUser(userIn, token)).thenReturn(new UserOUT());
    
    ResponseEntity<Object> responseRegister = controller.userRegister(token, userIn);
    
    assertNotNull(responseRegister);
    assertEquals(HttpStatus.OK, responseRegister.getStatusCode());
  }
  
  @Test
  public void testUserRegisterInvalidToken() throws Exception {
    MockitoAnnotations.initMocks(this);

    UserIn userIn = new UserIn();
    userIn.setEmail("ferna650@hotmail.com");
    userIn.setName("fernando");
    userIn.setPassword("123asdf");

    PhoneIn phone = new PhoneIn();
    phone.setCityCode("SCL");
    phone.setCountryCode("CL");
    phone.setIdUser(1);
    phone.setNumber("83568203");

    List<PhoneIn> phones = new ArrayList<PhoneIn>();

    userIn.setPhones(phones);

    String token = "123asdf";
    
    ResponseEntity<Object> responseRegister = controller.userRegister(token, userIn);
    
    assertNotNull(responseRegister);
    assertEquals(HttpStatus.UNAUTHORIZED, responseRegister.getStatusCode());
  }
  
  @Test
  public void testUserRegisterNullToken() throws Exception {
    MockitoAnnotations.initMocks(this);

    UserIn userIn = new UserIn();
    userIn.setEmail("ferna650@hotmail.com");
    userIn.setName("fernando");
    userIn.setPassword("123asdf");

    PhoneIn phone = new PhoneIn();
    phone.setCityCode("SCL");
    phone.setCountryCode("CL");
    phone.setIdUser(1);
    phone.setNumber("83568203");

    List<PhoneIn> phones = new ArrayList<PhoneIn>();

    userIn.setPhones(phones);

    String token = null;
    
    ResponseEntity<Object> responseRegister = controller.userRegister(token, userIn);
    
    assertNotNull(responseRegister);
    assertEquals(HttpStatus.UNAUTHORIZED, responseRegister.getStatusCode());
  }

  @Test
  public void testUserRegisterHibernateError() throws Exception {
    MockitoAnnotations.initMocks(this);

    UserIn userIn = new UserIn();
    userIn.setEmail("ferna650@hotmail.com");
    userIn.setName("fernando");
    userIn.setPassword("123asdf");

    PhoneIn phone = new PhoneIn();
    phone.setCityCode("SCL");
    phone.setCountryCode("CL");
    phone.setIdUser(1);
    phone.setNumber("83568203");

    List<PhoneIn> phones = new ArrayList<PhoneIn>();

    userIn.setPhones(phones);

    when(service.validateUser(userIn.getEmail(), userIn.getPassword())).thenReturn(true);

    ResponseEntity<Object> response = controller.login(userIn);
    
    LoginOut loginOut = (LoginOut)response.getBody();

    String token = loginOut.getToken();

    when(service.saveUser(userIn, token)).thenThrow(new HibernateError("Connection error"));
    
    ResponseEntity<Object> responseRegister = controller.userRegister(token, userIn);
    
    assertNotNull(responseRegister);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseRegister.getStatusCode());
  }
  
  @Test
  public void testUserRegisterExceptionError() throws Exception {
    MockitoAnnotations.initMocks(this);

    UserIn userIn = new UserIn();
    userIn.setEmail("ferna650@hotmail.com");
    userIn.setName("fernando");
    userIn.setPassword("123asdf");

    PhoneIn phone = new PhoneIn();
    phone.setCityCode("SCL");
    phone.setCountryCode("CL");
    phone.setIdUser(1);
    phone.setNumber("83568203");

    List<PhoneIn> phones = new ArrayList<PhoneIn>();

    userIn.setPhones(phones);

    when(service.validateUser(userIn.getEmail(), userIn.getPassword())).thenReturn(true);

    ResponseEntity<Object> response = controller.login(userIn);
    
    LoginOut loginOut = (LoginOut)response.getBody();

    String token = loginOut.getToken();

    when(service.saveUser(userIn, token)).thenThrow(new NullPointerException());
    
    ResponseEntity<Object> responseRegister = controller.userRegister(token, userIn);
    
    assertNotNull(responseRegister);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseRegister.getStatusCode());
  }

}
