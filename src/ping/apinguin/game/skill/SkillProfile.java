package ping.apinguin.game.skill;

import org.bukkit.Bukkit;
import ping.apinguin.events.skill.SkillGainEvent;
import ping.apinguin.events.skill.SkillLevelUpEvent;
import ping.apinguin.game.profile.Profile;

public class SkillProfile {
  private Profile profile;
  public SkillProfile(Profile profile){
    this.profile=profile;
  }

  public Profile getProfile() {
    return profile;
  }

  public void setLevel(Skill skill, Long level){
    profile.set("current.skill."+skill+".level",level);
  }
  public Long getLevel(Skill skill){
    return ((Long) profile.get("current.skill."+skill+".level",0L));
  }

  public void setExperience(Skill skill, Double xp){
    profile.set("current.skill."+skill+".exp",xp);
  }
  public Double getExperience(Skill skill){
    return (Double) profile.get("current.skill."+skill+".exp",0d);
  }

  public void setTotalExperience(Skill skill, Double xp){
    profile.set("current.skill."+skill+".totalExp",xp);
  }
  public Double getTotalExperience(Skill skill){
    return (Double) profile.get("skill."+skill+".totalExp",0d);
  }

  public void setRequiredExperience(Skill skill, Double xp){
    profile.set("current.skill."+skill+".exp",xp);
  }
  public Double getRequiredExperience(Skill skill){
    return (Double) skill.getRequiredExperience(getLevel(skill).intValue());
  }

  public void levelUp(Skill skill){
    setLevel(skill,getLevel(skill)+1);
    Bukkit.getPluginManager().callEvent(new SkillLevelUpEvent(skill, this, getLevel(skill).intValue()));
  }

  public void addExperience(Skill skill, Double amount){
    SkillGainEvent e = new SkillGainEvent(skill, this, amount);
    Bukkit.getPluginManager().callEvent(e);
    setExperience(skill, getExperience(skill)+ e.getAmount());
    while (getExperience(skill)>=getRequiredExperience(skill)){
      setExperience(skill,getExperience(skill)-getRequiredExperience(skill));
      levelUp(skill);
    }
  }
}
