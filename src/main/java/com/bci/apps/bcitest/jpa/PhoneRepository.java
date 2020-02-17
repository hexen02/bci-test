package com.bci.apps.bcitest.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bci.apps.bcitest.model.PhoneEntity;

@Repository
public interface PhoneRepository extends CrudRepository<PhoneEntity, Long> {

}
