package ping.apinguin.game.skill;

public class Skill {
  private String name;
  public Skill(String name){
    this.name=name;
  }
  public double getRequiredExperience(int level){
    return 100*level*level;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }
}
