package net.quepierts.interactions.main.config;

@FunctionalInterface
public interface IConstructor<T> {
    T construct(Object... args);
}
