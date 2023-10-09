package ping.apinguin.events.addon;

import ping.apinguin.addon.PingAddon;
import ping.apinguin.events.APInguinEvent;

public class AddonEvent extends APInguinEvent {
  private PingAddon addon;
  public AddonEvent(PingAddon addon){
    this.addon=addon;
  }
}
