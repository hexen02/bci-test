package com.bci.apps.bcitest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PhoneEntity {

  @Column(name = "ID_USER")
  private int idUser;
  @Id
  @Column(name = "NUMBER")
  private String number;
  @Column(name = "CITY_CODE")
  private String cityCode;
  @Column(name = "COUNTRY_CODE")
  private String countryCode;
  
  public int getIdUser() {
    return idUser;
  }

  public void setIdUser(int idUser) {
    this.idUser = idUser;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

}
