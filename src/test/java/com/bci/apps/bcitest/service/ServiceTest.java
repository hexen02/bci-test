package com.bci.apps.bcitest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import com.bci.apps.bcitest.in.PhoneIn;
import com.bci.apps.bcitest.in.UserIn;
import com.bci.apps.bcitest.jpa.PhoneRepository;
import com.bci.apps.bcitest.jpa.UserRepository;
import com.bci.apps.bcitest.model.UserEntity;
import com.bci.apps.bcitest.out.UserOut;

public class ServiceTest {
  
  

  @Mock
  @Autowired
  UserRepository userRepo;
  
  @Mock
  @Autowired
  PhoneRepository phoneRepo;
  
  @InjectMocks
  @Autowired
  UserService service;

  @Test
  public void testSaveUserOK() throws Exception {
    MockitoAnnotations.initMocks(this);
    
    String token = "123asdf";

    UserIn userIn = new UserIn();
    userIn.setEmail("ferna650@hotmail.com");
    userIn.setName("fernando");
    userIn.setPassword("Asdfg12");

    PhoneIn phone = new PhoneIn();
    phone.setCityCode("SCL");
    phone.setCountryCode("CL");
    phone.setIdUser(1);
    phone.setNumber("83568203");

    List<PhoneIn> phones = new ArrayList<PhoneIn>();
    phones.add(phone);

    userIn.setPhones(phones);
    
    UserEntity userEntity = new UserEntity();
    userEntity.setId(1);
    userEntity.setToken("123asf");

    when(userRepo.searchEmail(userIn.getEmail())).thenReturn(0);
    when(userRepo.save(any(UserEntity.class))).thenReturn(userEntity);

    UserOut userOut = service.saveUser(userIn, token);

    assertNotNull(userOut);
  }
  
  @Test
  public void testSaveUserWrongEmail() throws Exception {
    MockitoAnnotations.initMocks(this);
    
    String token = "123asdf";

    UserIn userIn = new UserIn();
    userIn.setEmail("ferna650");
    userIn.setName("fernando");
    userIn.setPassword("Asdfg12");

    PhoneIn phone = new PhoneIn();
    phone.setCityCode("SCL");
    phone.setCountryCode("CL");
    phone.setIdUser(1);
    phone.setNumber("83568203");

    List<PhoneIn> phones = new ArrayList<PhoneIn>();
    phones.add(phone);

    userIn.setPhones(phones);
    
    UserEntity userEntity = new UserEntity();
    userEntity.setId(1);
    userEntity.setToken("123asf");

    when(userRepo.searchEmail(userIn.getEmail())).thenReturn(0);
    when(userRepo.save(any(UserEntity.class))).thenReturn(userEntity);

    try{
      service.saveUser(userIn, token);
    }
    catch(Exception e) {
      assertEquals("El email no cumple con un formato correcto", e.getMessage());
    }

  }
  
  @Test
  public void testSaveUserEmailAlreadyExists() throws Exception {
    MockitoAnnotations.initMocks(this);
    
    String token = "123asdf";

    UserIn userIn = new UserIn();
    userIn.setEmail("ferna650@hotmail.com");
    userIn.setName("fernando");
    userIn.setPassword("Asdfg12");

    PhoneIn phone = new PhoneIn();
    phone.setCityCode("SCL");
    phone.setCountryCode("CL");
    phone.setIdUser(1);
    phone.setNumber("83568203");

    List<PhoneIn> phones = new ArrayList<PhoneIn>();
    phones.add(phone);

    userIn.setPhones(phones);
    
    UserEntity userEntity = new UserEntity();
    userEntity.setId(1);
    userEntity.setToken("123asf");

    when(userRepo.searchEmail(userIn.getEmail())).thenReturn(1);
    when(userRepo.save(any(UserEntity.class))).thenReturn(userEntity);

    try{
      service.saveUser(userIn, token);
    }
    catch(Exception e) {
      assertEquals("El correo ya est� registrado", e.getMessage());
    }

  }
  
  @Test
  public void testSaveUserEmailWrongPassword() throws Exception {
    MockitoAnnotations.initMocks(this);
    
    String token = "123asdf";

    UserIn userIn = new UserIn();
    userIn.setEmail("ferna650@hotmail.com");
    userIn.setName("fernando");
    userIn.setPassword("asdf");

    PhoneIn phone = new PhoneIn();
    phone.setCityCode("SCL");
    phone.setCountryCode("CL");
    phone.setIdUser(1);
    phone.setNumber("83568203");

    List<PhoneIn> phones = new ArrayList<PhoneIn>();
    phones.add(phone);

    userIn.setPhones(phones);
    
    UserEntity userEntity = new UserEntity();
    userEntity.setId(1);
    userEntity.setToken("123asf");

    when(userRepo.searchEmail(userIn.getEmail())).thenReturn(0);
    when(userRepo.save(any(UserEntity.class))).thenReturn(userEntity);

    try{
      service.saveUser(userIn, token);
    }
    catch(Exception e) {
      assertEquals("La contrase�a no cumple con el formato (una mayuscula, letras min�sculas, y dos n�meros)",e.getMessage());
    }

  }

}
