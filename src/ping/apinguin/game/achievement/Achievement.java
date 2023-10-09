package ping.apinguin.game.achievement;

public class Achievement {
  private String name;
  private Integer requiredCompletion=1;

  public Achievement(String name) {
    this.name = name;
  }

  public Integer getRequiredCompletion() {
    return requiredCompletion;
  }

  public void setRequiredCompletion(Integer requiredCompletion) {
    this.requiredCompletion = requiredCompletion;
  }

  @Override
  public String toString() {
    return name;
  }
}
