package mobile.models;

import lombok.Data;

@Data
public class Calendar {
  private String eventName;
  private String startTime;
  private String endTime;
  private String attendees;
}
