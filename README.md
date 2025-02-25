# SpigotUtilAPI 
This is a utilities API which I made on my own to make my plugin development a bit easier.
It also contains some other stuff because I wanted to look cool. 

## What this API has:
- Command management system (with base and subcommands)
- Inventory GUIs and the like
- YAML file management for non-default config.yml files
- All of BuoyyEcon's dual economy codebase lies here.

You could use this, but I'm not really sure myself. 
## How to use:
- First, make sure you have JDK 21 and Apache Maven.
- Clone this repo.
- Run the command "mvn" in the root folder of this project.
- Then, you can add this little section to your plugin's POM's dependencies section:
- ```xml
  <dependency>
      <groupId>com.github.buoyy</groupId>
      <artifactId>SpigotUtilAPI</artifactId>
      <version>1.0</version>
      <scope>provided</scope>
  </dependency>
  ```
- Reload your Maven project, and you should be able to use this API.

# Use Cases:
## Better logging with Messenger class:
You can use the `Messenger` class to beautifully log messages to the console.
You can use it in such a way: (Recommended to use singleton pattern for this)

```java
private void buyForPlayer(Player player, ItemStack item) {
    if (hasNoBalance()/*if player does not enough balance*/) {
        doSomething();
    }
}
```
## Economy handling:
While making a supporting plugin for BuoyyEcon, you can connect your
plugin with the Economy like this:

```java
import com.github.buoyy.api.economy.Economy;
import org.bukkit.Bukkit;

private boolean setupEcon() {
    if (Bukkit.getPluginManager().getPlugin("BuoyyEcon") == null)
        return false;
    economy = Bukkit.getServicesManager().getRegistration(Economy.class);
    return economy != null;
}
```
where `economy` is an Economy field object. It is advised to use
singleton pattern with this field. After this, you can use methods like 
`Economy#add()` and `Economy#subtract()` in your plugin.
