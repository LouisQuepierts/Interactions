package net.quepierts.interactions.main.config;

@FunctionalInterface
public interface ILegalChecker<T> {
    boolean legal(T arg);
}
