package com.bci.apps.bcitest.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.bci.apps.bcitest.model.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

  @Query(
      value = "SELECT COUNT(*) from TBL_USER WHERE EMAIL = :email",
      nativeQuery = true)
  Integer searchEmail(@Param("email") String email);
  
  @Query(
      value = "SELECT COUNT(*) from TBL_USER WHERE EMAIL = :email AND PASSWORD = :password",
      nativeQuery = true)
  Integer loginUser(@Param("email") String email, @Param("password") String password);
  
  @Query(
      value = "SELECT * from TBL_USER",
      nativeQuery = true)
  List<UserEntity> getAllUsers();

}
