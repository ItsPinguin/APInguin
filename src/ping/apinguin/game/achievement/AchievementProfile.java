package ping.apinguin.game.achievement;

import org.bukkit.Bukkit;
import ping.apinguin.events.achievement.AchievementAdvancedEvent;
import ping.apinguin.events.achievement.AchievementCompletionEvent;
import ping.apinguin.game.profile.Profile;

public class AchievementProfile {
  private static String ACHIEV_PATH="current.achievement.";
  private Profile profile;

  public AchievementProfile(Profile profile) {
    this.profile = profile;
  }

  public Profile getProfile() {
    return profile;
  }

  //todo ===============================
  //advance achievement
  //advance achievement event
  //complete achievement
  //complete achievement event
  public void setAdvancement(Achievement achievement, Integer amount){
    profile.set(ACHIEV_PATH+achievement+".completion", amount.longValue());
  }

  public void advanceAchievement(Achievement achievement, Integer amount){
    if (isAchieved(achievement)) return;
    setAdvancement(achievement,  amount + getAdvancement(achievement));
    Bukkit.getPluginManager().callEvent(new AchievementAdvancedEvent(achievement, this, amount));
    if (getAdvancement(achievement)>=achievement.getRequiredCompletion()){
      setAdvancement(achievement, achievement.getRequiredCompletion());
      setAchievement(achievement, true);
    }
  }

  public Integer getAdvancement(Achievement achievement){
    return ((Long) profile.get(ACHIEV_PATH + achievement + ".completion", 0L)).intValue();
  }

  public void setAchievement(Achievement achievement, Boolean completed){
    profile.set(ACHIEV_PATH+achievement+".completed",completed);
    if (completed) {
      profile.set(ACHIEV_PATH + achievement + ".completion", achievement.getRequiredCompletion().longValue());
      Bukkit.getPluginManager().callEvent(new AchievementCompletionEvent(achievement, this));
    } else {
      profile.set(ACHIEV_PATH + achievement + ".completion", 0L);
    }
  }

  public boolean isAchieved(Achievement achievement){
    return ((Boolean) profile.get(ACHIEV_PATH + achievement + ".completed",false));
  }
}
