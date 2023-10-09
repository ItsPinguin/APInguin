package ping.apinguin.events.achievement;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import ping.apinguin.events.APInguinEvent;
import ping.apinguin.game.achievement.Achievement;
import ping.apinguin.game.achievement.AchievementProfile;

public class AchievementEvent extends APInguinEvent {
  private Achievement achievement;
  private AchievementProfile achievementProfile;

  public AchievementEvent(Achievement achievement, AchievementProfile achievementProfile) {
    this.achievement = achievement;
    this.achievementProfile = achievementProfile;
  }

  public Achievement getAchievement() {
    return achievement;
  }

  public AchievementProfile getAchievementProfile() {
    return achievementProfile;
  }

  public OfflinePlayer getPlayer(){
    return Bukkit.getOfflinePlayer(getAchievementProfile().getProfile().getId());
  }

}
