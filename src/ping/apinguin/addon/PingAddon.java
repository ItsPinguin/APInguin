package ping.apinguin.addon;

public interface PingAddon {
  default void createItems() {
  }

  default void createRecipes() {
  }

  default void createDrops() {
  }

  default PingItemHandler getItemHandler() {
    return new PingItemHandler() {
    };
  }

  default PingConditionHandler getConditionHandler() {
    return new PingConditionHandler() {
    };
  }

  default PingDropHandler getDropHandler(){
    return new PingDropHandler() {
    };
  }
}
