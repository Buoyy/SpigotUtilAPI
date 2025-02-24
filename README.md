# SpigotUtilAPI 
This is a utilities API which I made on my own to make my plugin development a bit easier 
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
- Reload your Maven project and you should be able to use this API.
