## What will the application do?
Dungeon Keeper is a project aimed at creating a top-down 
sprite and text-based roguelike dungeon crawler similar to Dwarf Fortress and 
Dungeon Crawl Stone Soup. In short, it is a game about fighting enemies and 
exploring dungeons, while trying to stay alive.

## Who will use it?
The game is intended for anyone, from casual players to diehard gamers. 
Of course, this game will not rival AAA titles of today, but it should still give users 
a fun experience to run around and explore.

## Why is this project of interest to you?
Video game development was one of my first forays into programming and software-development. 
One of my first coding projects in 2016 was a top-down terminal-based game named "spikemaze". 
Then, in 2017, one of my first major projects was another open-world 2d Minecraft-inspired 
adventure game. For this CPSC 210 project, I wanted to continue this trend by building another 
2d game as an introduction to Software Construction.

## User Stories
- I want to be able to see and move my own player.
- I want to be able to pick up and drop items on the ground.
- I want to be able to pick up multiple items and add them to my inventory.
- I want to be able to see and fight enemies
- I want to be able to use and equip items.

## User Stories (Part 2)
- I want to have the option to save my game state while playing
- I want to have the option to reload my game state while playing
- I want to have the option to save my game state when quitting the game
- I want to have the option to reload my game state when starting the game
- I want to have the option to create a new game, without loading the previous game state

## User Stories (Part 3)
- I want to be able to do everything the previous user stories have asked for, in a graphical interface. 
  - Seeing and moving the player
  - Picking up and dropping items
  - Fighting enemies
  - Using and equipping items
  - Saving and loading the game

## Instructions for Grader
- To start the game:
  - Hover over and click "New Game" in the main menu.
- To continue a saved game:
  - Hover over and click "Load Game" in the main menu.
  - NOTE: A game save must be created in order for this to work!
- Quick introduction to the GUI:
  - The top left corner has the player's health, defense, and strength.
    - Icons will show up as any of these stats increase.
  - The bottom left corner has the game chat box. This is where the game will display messages.
  - The bottom right corner has quick instructions as to how to get started with the game.
- To move around the game:
  - Use the keys "W", "A", "S", "D" to move.
  - The player, by default, spawns in the top left corner of the map.
    - NOTE: you may need to move around a little, as the player may be hidden by the GUI.
- To activate tooltips
  - Similar to the terminal UI, you can learn more about items, tiles, and enemies by hovering over them.
  - Hover over some tiles to see their descriptions.
  - Hover over some enemies to see their health, attack, and defense.
  - Hover over some dropped items to see their descriptions.
  - Hover over the player to see in-depth information about the player.
- To fight enemies:
  - Move the player to the same tile as an enemy and walk into the enemy.
  - Hover over the enemy to see their health.
- To pick up items:
  - Move the player to the same tile as an item and walk into the item.
  - Press "Q" to pick up the item or press "X" to discard.
  - Hover over the dropped item to learn more about it.
- To open and navigate the inventory:
  - Press "E" to open the inventory.
  - The player attack, defense, and health is shown next to the player icon.
  - The player's inventory is shown on the right side of the screen.
  - The player's equipped items are shown on the two boxes on left side of the screen.
  - Hover over the items to see their information.
  - Click on the items to use and/or equip them.
  - Hover over an item and press "Q" to drop the item or "X" to delete the item.
  - Press "E" or "Escape" to close the inventory.
- To open the inventory and save the game:
  - Press "Escape" to pause the game.
  - Hover over the options on screen.
  - Press "Resume" to resume the game.
  - Press "Save and Quit" to save the game and quit.
  - Press "Quit Without Saving" to quit the game without saving.
  - Press "Escape" again to exit the pause menu.

## Phase 4: Task 2
Example log is too long to fit here, so please refer to `example_game_logs.txt`.

## Phase 4: Task 3
There are a couple areas that could be improved. For one, functionality between
DroppedItem and Item could be merged together as one single "Item" class.
This is because the coupling between the two classes is very high, and
there is some duplicated code between the two classes. Another thing is that 
many functionalities in different potions are similar, so it would be nice to 
abstract those away to the parent potion class. Finally, the "Level" class could
be improved by merging into the main "Game" class. This was originally designed so that
a single game could have multiple levels, but this was never implemented. There is a
lot more specific implementation details that could be improved as well if there was
more time, especially in the rendering code.