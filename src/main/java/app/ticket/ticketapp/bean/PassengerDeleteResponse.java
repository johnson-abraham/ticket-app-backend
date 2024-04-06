package app.ticket.ticketapp.bean;

public class PassengerDeleteResponse {
  private final boolean success;
  private final String id;

  public PassengerDeleteResponse(boolean success, String id) {
    this.success = success;
    this.id = id;
  }

  public boolean isSuccess() {
    return success;
  }

  public String getId() {
    return id;
  }
}
