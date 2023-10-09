package ping.apinguin.events;

import ping.apinguin.addon.PingAddon;
import ping.apinguin.events.addon.AddonEvent;

public class AddonLoadEvent extends AddonEvent {
  public AddonLoadEvent(PingAddon addon){
    super(addon);
  }
}
