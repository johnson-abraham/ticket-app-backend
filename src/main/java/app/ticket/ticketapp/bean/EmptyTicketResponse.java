package app.ticket.ticketapp.bean;

public class EmptyTicketResponse {
  private final String id;
  private final String section;
  private final int seatNumber;

  public EmptyTicketResponse(String id, String section, int seatNumber) {
    this.id = id;
    this.section = section;
    this.seatNumber = seatNumber;
  }

  public static EmptyTicketResponse fromTicket(Ticket ticket) {
    return new EmptyTicketResponse(ticket.getId(), ticket.getSection(), ticket.getSeatNumber());
  }

  public String getId() {
    return id;
  }

  public String getSection() {
    return section;
  }

  public int getSeatNumber() {
    return seatNumber;
  }
}
