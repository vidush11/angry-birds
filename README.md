<h1>angry-birds</h1>

This contains the project for course CSE-201 (Advanced Programming). In this we have made the angry-birds game.

<h2>How to run?</h2>
1) clone the repository<br>
2) set the root as idea project<br>
3) set gradle configuration as:
<br>
Run:run<br>
Gradle Project: myProject:lwjgl3 

<br>
4)<i>Click on Run</i>

<h2>Methods defined in the game</h2>
1) In each level we have added loadGameState and saveGameState methods for serialization, we are storing the game state (all the blocks, piggies and birds) by pressing <i> s</i> and then you can load it using <i>l</i>. Even if the game exits, you can restart the game and press <i>l</i> to load perfectly.
<br>
2) We have added game classes in the package Game_Classes: Piggy, Bird, Ground, Wall which have we initialized in arrays and disposed when destroyed.
<br>
3) We are Remaking the slingshot inside the level itself each time it is loaded through a custom function.
<br>
4) Birds are set inactive by default and are set active when they are loaded on SlingShot.
<br>
5) We are calculating score based on number of birds used and objects destroyed and assigning stars likewise.



<h3>Repo:vidush11/angry-birds</h3>


<h3>[Video Link](https://drive.google.com/file/d/1XoGo9nmlgDXR8j5FrboLG5k87UbILAk2/view?usp=drive_link)</h3>
<link>https://drive.google.com/file/d/1XoGo9nmlgDXR8j5FrboLG5k87UbILAk2/view?usp=drive_link</link>
