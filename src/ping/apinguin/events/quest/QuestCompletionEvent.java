package ping.apinguin.events.quest;

import ping.apinguin.game.quest.Quest;
import ping.apinguin.game.quest.QuestProfile;

public class QuestCompletionEvent extends QuestEvent {
  public QuestCompletionEvent(Quest quest, QuestProfile questProfile) {
    super(quest, questProfile);
  }
}
