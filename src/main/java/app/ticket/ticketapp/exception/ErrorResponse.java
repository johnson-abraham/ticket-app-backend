package app.ticket.ticketapp.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

  private String errorMessage;
  private HttpStatus errorCode;

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public HttpStatus getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(HttpStatus errorCode) {
    this.errorCode = errorCode;
  }
}
