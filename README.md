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
Create methods are processed in this order:
- create items
- create recipes
- create drops

If you're searching how to create one of the objects, check in the object's section.
### Handler Methods
In `PingAddon`, you can find `#getItemHandler`,`#getConditionHandler` and `#getDropHandler`. 
