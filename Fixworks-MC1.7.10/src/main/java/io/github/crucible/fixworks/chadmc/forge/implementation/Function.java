package io.github.crucible.fixworks.chadmc.forge.implementation;

/**
 * Abstraction for some simple cache calls.
 *
 * @author Jikoo
 */
public abstract class Function<V> {

    public abstract boolean run(V value);

}