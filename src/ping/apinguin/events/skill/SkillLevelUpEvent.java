package ping.apinguin.events.skill;

import ping.apinguin.game.skill.Skill;
import ping.apinguin.game.skill.SkillProfile;

public class SkillLevelUpEvent extends SkillEvent{
  private int newLevel;
  public SkillLevelUpEvent(Skill skill, SkillProfile skillProfile, int newLevel) {
    super(skill, skillProfile);
    this.newLevel=newLevel;
  }

  public int getNewLevel() {
    return newLevel;
  }

}
