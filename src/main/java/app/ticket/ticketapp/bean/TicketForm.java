package app.ticket.ticketapp.bean;

public class TicketForm {
  private String email;
  private String firstName;
  private String lastName;
  private String sourceStation;
  private String destinationStation;
  private String seatId;
  private double price;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getSourceStation() {
    return sourceStation;
  }

  public void setSourceStation(String sourceStation) {
    this.sourceStation = sourceStation;
  }

  public String getDestinationStation() {
    return destinationStation;
  }

  public void setDestinationStation(String destinationStation) {
    this.destinationStation = destinationStation;
  }

  public String getSeatId() {
    return seatId;
  }

  public void setSeatId(String seatId) {
    this.seatId = seatId;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }
}
