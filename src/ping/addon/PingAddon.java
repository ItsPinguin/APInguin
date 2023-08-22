package ping.addon;

public interface PingAddon {
    void startAddon();
    default PingItemAddon getItemAddon(){
        return new PingItemAddon() {};
    }
}
