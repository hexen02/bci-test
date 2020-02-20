package com.bci.apps.bcitest.service;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import org.hibernate.HibernateError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bci.apps.bcitest.in.PhoneIn;
import com.bci.apps.bcitest.in.UserIn;
import com.bci.apps.bcitest.jpa.PhoneRepository;
import com.bci.apps.bcitest.jpa.UserRepository;
import com.bci.apps.bcitest.model.PhoneEntity;
import com.bci.apps.bcitest.model.UserEntity;
import com.bci.apps.bcitest.out.UserOut;

@Service
public class UserService {
  
  private static Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  UserRepository userRepository;
  
  @Autowired 
  PhoneRepository phoneRepository;

  private boolean checkEmail(String email) {
    logger.info("Checking email");
    Pattern pattern = Pattern.compile("^[^@]+@[^@]+\\.[a-zA-Z]{2,}$");
    return pattern.matcher(email).matches();
  }

  private boolean checkPassword(String password) {
    logger.info("Checking password");
    Pattern pattern = Pattern.compile("^([A-Z]{1})([a-z]*)(\\d{2})$");
    return pattern.matcher(password).matches();
  }
  
  public boolean validateUser(String email, String password) {
    return userRepository.loginUser(email, password) == 1;
  }

  @Transactional
  public UserOut saveUser(UserIn userIn, String token) throws Exception {
    logger.info("saveUser starts");

    if (!checkEmail(userIn.getEmail())) {
      throw new Exception("El email no cumple con un formato correcto");
    }
    
    int count = userRepository.searchEmail(userIn.getEmail());

    if (count > 0) {
      throw new Exception("El correo ya est� registrado");
    }

    if (!checkPassword(userIn.getPassword())) {
      throw new Exception(
          "La contrase�a no cumple con el formato (una mayuscula, letras min�sculas, y dos n�meros)");
    }

    UserEntity userEntity = new UserEntity();
    userEntity.setEmail(userIn.getEmail());
    userEntity.setName(userIn.getName());
    userEntity.setPassword(userIn.getPassword());
    userEntity.setToken(token);
    
    logger.info("saving user");
    UserEntity userSaved = null;
    
    try{
      userSaved = userRepository.save(userEntity);
    }
    catch(Exception e) {
      throw new HibernateError(e.getMessage());
    }

    UserOut userOut = new UserOut();
    userOut.setId(userSaved.getId());
    userOut.setCreated(new Date());
    userOut.setModified(new Date());
    userOut.setLastLogin(new Date());
    userOut.setToken(userSaved.getToken());
    userOut.setIsActive(true);
    
    logger.info("saving phones");
    
    for(PhoneIn phone: userIn.getPhones()) {
      PhoneEntity phoneEntity = new PhoneEntity();
      phoneEntity.setCityCode(phone.getCityCode());
      phoneEntity.setCountryCode(phone.getCountryCode());
      phoneEntity.setIdUser(userSaved.getId());
      phoneEntity.setNumber(phone.getNumber());
      try {
        phoneRepository.save(phoneEntity);
      }
      catch(Exception e) {
        throw new HibernateError(e.getMessage());
      }
    }
    
    logger.info("saveUser ends");
    return userOut;
  }
  
  public List<UserEntity> getAllUsers(){
    List<UserEntity> list = userRepository.getAllUsers();
    return list;
  }

}
