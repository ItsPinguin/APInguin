package ping.apinguin.events.quest;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import ping.apinguin.events.APInguinEvent;
import ping.apinguin.game.quest.Quest;
import ping.apinguin.game.quest.QuestProfile;

public class QuestEvent extends APInguinEvent {
  private Quest quest;
  private QuestProfile questProfile;

  public QuestEvent(Quest quest, QuestProfile questProfile) {
    this.quest = quest;
    this.questProfile = questProfile;
  }

  public Quest getQuest() {
    return quest;
  }

  public QuestProfile getQuestProfile() {
    return questProfile;
  }

  public OfflinePlayer getPlayer(){
    return Bukkit.getOfflinePlayer(getQuestProfile().getProfile().getId());
  }
}
