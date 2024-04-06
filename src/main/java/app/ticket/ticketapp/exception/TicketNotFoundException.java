package app.ticket.ticketapp.exception;

public class TicketNotFoundException extends RuntimeException {
  public TicketNotFoundException(String s) {
    super(s);
  }
}
