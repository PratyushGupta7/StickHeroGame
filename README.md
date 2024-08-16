# Stick Hero Game - JavaFX Adventure 🎮

Welcome to Stick Hero, an exciting JavaFX game inspired by the classic Stick Hero, where Stick-Hero embarks on a perilous journey between platforms, mastering the art of stick extension while collecting cherries which can be used to bring you back to action and also dodging scary ghosts which try to attack you.🌟

## Authors
- Pratyush Gupta 
- Dhruv Sharma

## Features That Will Keep You Hooked!

### Stick-tacular Gameplay 🕹️

- **Stick Extension Mechanism:** Master the art of extending Stick Hero's stick to bridge the perilous gaps between platforms.
- **Multiple Pillars:** Navigate through a variety of platforms with different widths and positions, adding an element of challenge.
- **Revive with Cherries:** Collect cherries to revive Stick Hero when faced with adversity.
- **Timing is Everything:** Perfect your timing to land Stick Hero on the next platform. Fail, and witness Stick Hero's dramatic fall!
- **Cherry Bonanza:** Flip Stick Hero upside down to collect cherries and boost your score.
- **Score to the Stars:** A scoring system that encourages you to reach new heights! Collect more cherries for epic scores.
- **Save Your Progress:** Save your journey at any moment, keeping track of your last score, highest score, and total cherries.
- **Serialization & Deserialization:** Your adventure is secure! Serialization and deserialization have been implemented to save and load your progress seamlessly.
- **Design Patterns Magic:**
  - **Factory Pattern:** Efficiently creates rewards (cherries and obstacles) using `RewardFactory`.
  - **Singleton Pattern:** Ensures a single instance of `Character` throughout the game.
- **Seamless Transitions:** Experience smooth transitions as the game seamlessly shifts between platforms, creating an immersive gaming loop.

### JUnit Tests

- **Test Serialization:** A JUnit test verifies the perfect serialization and deserialization of the game's data. A `DataBase` object is created with specific values for highest score and cherries. The object is written to a file and then read back. Assertions ensure that the read object matches the original, confirming proper serialization and deserialization.
- **Test Initialize Game Objects:** Another JUnit test ensures the proper initialization of game objects in the `WorldController`. The test checks if the `Character`, `Cherry`, and `Obstacle` objects are not null after initialization. This guarantees that the game starts with essential objects in a valid state.

### OOP Principles

- **Encapsulation:** Data and functionality are encapsulated within classes and attributes are accessed and modified through appropriate getters and setters, promoting clean and organized code.
- **Abstraction:** Abstract classes and interfaces are used to create a clear hierarchy of game entities, promoting code readability.
- **Polymorphism:** Different types of rewards and game entities utilize polymorphism to achieve flexibility and extensibility.
- **Inheritance:** Inheritance is employed to create specialized classes, enhancing code reuse and maintaining a structured class hierarchy.
- **Interfaces:** Interfaces are implemented to define common behaviors, promoting code consistency and adaptability.

### Buttons

- **PLAY:** Start your Stick Hero adventure from the beginning.
- **SAVE AND EXIT:** Save your current progress and exit the game.
- **PLAY AGAIN:** Restart the game from the beginning after losing.
- **REVIVE:** Use 3 of your collected cherries to revive Stick Hero and continue the game.
- **EXIT:** Exit the game without saving progress.
## Bonus Adventures 🌈

- **Dodging Obstacles:** Brace yourself for a surprise as a mischievous ghost tries to intercept Stick Hero! Dodge it skillfully to continue your journey.
- **Smooth Multithreading Gameplay:** Experience parallel gameplay as Stick Hero and the ghost navigate the platforms smoothly, thanks to multithreading magic.

## How to Begin Your Stick Hero Journey

1. **Clone the Repository:** Start by cloning the repository to your local machine.
   ```bash
   git clone https://github.com/PratyushGupta7/StickHeroGame.git
2. **Install Java Development Kit (JDK):** Ensure you have the JDK installed. You can download it from the official Oracle website.
3. **Download JavaFX Library:** Download and set up the JavaFX library. You can find the necessary files and instructions on the Gluon website.
4. **Update Paths:** Don’t forget to update the paths to `database.txt` and `savedgame.txt` according to your local machine setup.
5. **Launch the Adventure:** Compile and run the `World` class to embark on your Stick Hero adventure.
6. **Stick Mastery:** Hold the 'A' key to extend Stick Hero's stick and press the 'A' key once to flip the character and one more time to get it back up.
7. **Kick into Action:** Release the 'A' key to unleash a powerful kick and face unexpected challenges.
8. **Enjoy the Thrill:** Immerse yourself in the thrilling world of Stick Hero and aim for the highest score!

---
