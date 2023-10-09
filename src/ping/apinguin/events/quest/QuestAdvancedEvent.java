package ping.apinguin.events.quest;

import ping.apinguin.game.quest.Quest;
import ping.apinguin.game.quest.QuestProfile;

public class QuestAdvancedEvent extends QuestEvent{
  private Integer amount;

  public QuestAdvancedEvent(Quest quest, QuestProfile questProfile, Integer amount) {
    super(quest, questProfile);
    this.amount=amount;
  }

  public Integer getAmount() {
    return amount;
  }
}
