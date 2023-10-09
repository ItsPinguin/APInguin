package ping.apinguin.events.achievement;

import ping.apinguin.game.achievement.Achievement;
import ping.apinguin.game.achievement.AchievementProfile;

public class AchievementAdvancedEvent extends AchievementEvent{
  private Integer amount;
  public AchievementAdvancedEvent(Achievement achievement, AchievementProfile achievementProfile, Integer amount) {
    super(achievement, achievementProfile);
    this.amount = amount;
  }

  public Integer getAmount() {
    return amount;
  }
}
