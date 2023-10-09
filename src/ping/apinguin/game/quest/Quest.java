package ping.apinguin.game.quest;

public class Quest {
  private String name="null";
  private Boolean completed=false;
  private Integer requiredCompletion=1;

  public Quest(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }


  public Integer getRequiredCompletion() {
    return requiredCompletion;
  }

  public void setRequiredCompletion(Integer requiredCompletion) {
    this.requiredCompletion = requiredCompletion;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }

  @Override
  public String toString() {
    return name;
  }
}
