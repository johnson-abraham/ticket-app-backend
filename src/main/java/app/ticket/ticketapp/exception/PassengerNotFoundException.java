package app.ticket.ticketapp.exception;

public class PassengerNotFoundException extends RuntimeException {
  public PassengerNotFoundException(String message) {
    super(message);
  }
}
