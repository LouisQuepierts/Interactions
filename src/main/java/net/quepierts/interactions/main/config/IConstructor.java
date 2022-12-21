package net.quepierts.interactions.main.config;

@FunctionalInterface
public interface IConstructor {
    Object construct(Object... args) throws Exception;
}
