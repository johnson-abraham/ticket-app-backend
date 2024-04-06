package app.ticket.ticketapp.bean;

public class SeatUpdateRequest {
  private String passengerId;
  private String oldSeatId;
  private String newSeatId;

  public String getPassengerId() {
    return passengerId;
  }

  public void setPassengerId(String passengerId) {
    this.passengerId = passengerId;
  }

  public String getOldSeatId() {
    return oldSeatId;
  }

  public void setOldSeatId(String oldSeatId) {
    this.oldSeatId = oldSeatId;
  }

  public String getNewSeatId() {
    return newSeatId;
  }

  public void setNewSeatId(String newSeatId) {
    this.newSeatId = newSeatId;
  }
}
