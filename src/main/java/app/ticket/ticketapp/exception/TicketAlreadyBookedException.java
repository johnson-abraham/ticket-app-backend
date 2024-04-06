package app.ticket.ticketapp.exception;

public class TicketAlreadyBookedException extends RuntimeException {
  public TicketAlreadyBookedException(String message) {
    super(message);
  }
}
