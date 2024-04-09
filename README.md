# BossLibrary

BossLibrary was made for developers wanting to create amazing custom bosses with amazing attacks, I suggest you to read the code
if you have any issues with the library as most of the methods have comments explaining what do they do

## Using BossLibrary
Maven
````xml
<dependency>
    <groupId>io.github.alejomc26</groupId>
    <artifactId>BossLibrarySource</artifactId>
    <version>1.42</version>
</dependency>
````

Gradle
````gradle
dependencies {
    implementation("io.github.alejomc26:BossLibrarySource:1.42")
}
````

Add the plugin to your server


## How to use

All the important classes extend ``BaseEntity`` so here is that class in a nutshell

- Constructor location = spawn location
- Uses item displays for entity scheduler
- Has a cancel method that gets called when the entity dies or despawns

# How to create a boss

- Extend ``CustomBoss`` and set the spawn location and health
- Construct a hitbox using ``CustomHitbox`` and make that it teleports with the boss 
- Do something in the tick method
- Make a class that implements ``CustomBehavior`` and do something there
- Use CustomBoss#SetBehavior and make an instance of the class implementing ``CustomBehavior``
- Use whatever you want to switch behaviors if anything happens
- Use ``BoneBuilder`` to create an itemDisplay and make the model data for it to display a 3d model of a part of the boss
- Make something fancy with a lot of bones
- Finally, create an instance of the class extending ``CustomBoss`` somewhere

# How to create a projectile

- Extend ``CustomProjectile`` and set the spawn location and contact damage
- Get the bone builder of it and make a 3d model of the projectile as how you did with the boss
- Do something in the tick method to make it move
- Make an instance of it, usually inside a custom behavior class
- Watch how your projectile moves

# How to create a hitbox

- Inside your ``CustomBoss`` construct a `CustomHitbox` and adjust it as necessary
- teleport your hitbox with your boss or just leave it there
- Press F3 + B inside Minecraft to see your hitbox, it is an interaction entity

# Useful methods

Inside BossUtils you can find some methods that may help you, use them if you want
