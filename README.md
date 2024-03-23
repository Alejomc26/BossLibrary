# BossLibrary

Inside your onEnable method add `BossLibraryManager.setPlugin(this)`, otherwise the library won't work correctly

## How to use

Extending `CustomBoss` to a class grants you the tick and death methods, tick will be called every tick that the boss is alive, death will be called when the boss dies

Extending `CustomBehavior` to a class also gives you a tick method, you can make an instance of this class and set the boss behavior to it, that will call the tick method of custom behavior
every tick, but the tick method of custom boss will still be called, if you change the behavior the exit method of the previous behavior will be called.

Extending `CustomProjectile` to a class also gives you a tick method, but also an atLaunch method and touchBlock, touch block will be called when the projectile touches a block,
after the projectile touches the block it will also disappear, making instances of a class extending `CustomProjectile` is useful when doing custom behaviors, and you want to do an attack
with projectiles

`DisplayBuilder` is similar to Destrokyo `ParticleBuilder` but this one is oriented to display entities

`LineDisplay` is useful for when you want to make a display entity that connects 2 points