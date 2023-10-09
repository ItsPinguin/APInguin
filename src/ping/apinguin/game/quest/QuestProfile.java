package ping.apinguin.game.quest;

import org.bukkit.Bukkit;
import ping.apinguin.events.quest.QuestAdvancedEvent;
import ping.apinguin.events.quest.QuestCompletionEvent;
import ping.apinguin.game.profile.Profile;

public class QuestProfile {
  private static String QUEST_PATH="current.quest.";
  private Profile profile;
  public QuestProfile(Profile profile){
    this.profile=profile;
  }

  public Profile getProfile() {
    return profile;
  }

  public void completeQuest(Quest quest){

    setQuest(quest,true);
  }

  public void advanceQuest(Quest quest, Integer amount) {
    if (isCompleted(quest)) return;
    profile.set(QUEST_PATH + quest + ".completion", (((Long) profile.get(QUEST_PATH + quest + ".completion")) + amount));
    Bukkit.getPluginManager().callEvent(new QuestAdvancedEvent(quest, this, amount));
    if (getAdvancement(quest) >= quest.getRequiredCompletion()){
      setAdvancement(quest, quest.getRequiredCompletion());
      setQuest(quest, true);
    }
  }

  public void setQuest(Quest quest, Boolean completed){
    profile.set(QUEST_PATH+quest+".completed",completed);
    if (completed) {
      profile.set(QUEST_PATH + quest + ".completion", quest.getRequiredCompletion().longValue());
      Bukkit.getPluginManager().callEvent(new QuestCompletionEvent(quest, this));
    } else {
      profile.set(QUEST_PATH + quest + ".completion", 0L);
    }
  }

  public Integer getAdvancement(Quest quest){
    Object obj =profile.get(QUEST_PATH + quest + ".completion");
    return obj instanceof Long longO ? longO.intValue() : ((Integer) obj);
  }

  public void setAdvancement(Quest quest, Integer amount){
    profile.set(QUEST_PATH+quest+".completion",amount.longValue());
  }

  public boolean isCompleted(Quest quest){
    return ((Boolean) profile.get(QUEST_PATH + quest + ".completed", false));
  }
}
