package com.bci.apps.bcitest.out;

public class ErrorOut {

  private String mensaje;

  public ErrorOut (String mensaje){
    this.mensaje = mensaje;
  }
  
  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

}
