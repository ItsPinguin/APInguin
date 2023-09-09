# APInguin
## Features
- Items
- Recipes
- Drops

## Addon
### Get Started

Implement `PingAddon` to your plugin's main class:
```java
public class MyPlugin extends JavaPlugin implements PingAddon {
}
```
There are two types of methods in `PingAddon`:
- create methods
- handler methods

Create methods are used to create items, recipes, drops.

Handler methods are used for handling certain part of the API.

### Create Methods
