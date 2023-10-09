package ping.apinguin.events.skill;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import ping.apinguin.events.APInguinEvent;
import ping.apinguin.game.skill.Skill;
import ping.apinguin.game.skill.SkillProfile;

public class SkillEvent extends APInguinEvent {
  private Skill skill;
  private SkillProfile skillProfile;
  public SkillEvent(Skill skill, SkillProfile skillProfile){
    this.skill=skill;
    this.skillProfile=skillProfile;
  }

  public Skill getSkill() {
    return skill;
  }

  public SkillProfile getSkillProfile() {
    return skillProfile;
  }
  public OfflinePlayer getPlayer(){
    return Bukkit.getOfflinePlayer(getSkillProfile().getProfile().getId());
  }
}
