package ping.apinguin.events.skill;

import ping.apinguin.game.skill.Skill;
import ping.apinguin.game.skill.SkillProfile;

public class SkillGainEvent extends SkillEvent{
  private Double amount;
  public SkillGainEvent(Skill skill, SkillProfile skillProfile, Double amount) {
    super(skill, skillProfile);
    this.amount=amount;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }
}
