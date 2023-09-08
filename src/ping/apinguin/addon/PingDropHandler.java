package ping.apinguin.addon;

import ping.apinguin.game.drop.PingDrop;

public interface PingDropHandler extends PingAddonComponent{
  default int handleAmount(PingDrop drop, int amount){
    return amount;
  }
}
