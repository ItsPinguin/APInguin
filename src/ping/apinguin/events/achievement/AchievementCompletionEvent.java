package ping.apinguin.events.achievement;

import ping.apinguin.game.achievement.Achievement;
import ping.apinguin.game.achievement.AchievementProfile;

public class AchievementCompletionEvent extends AchievementEvent{

  public AchievementCompletionEvent(Achievement achievement, AchievementProfile achievementProfile) {
    super(achievement, achievementProfile);
  }
}
