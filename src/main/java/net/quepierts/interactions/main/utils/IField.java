package net.quepierts.interactions.main.utils;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public interface IField {
    void update(@NotNull Player player);

    static IField[] getFields(@NotNull Object[] args) {
        IField[] fields = new IField[args.length];

        int count = 0;
        for (Object arg : args) {
            if (arg instanceof IField) {
                fields[count] = (IField) arg;
                ++ count;
            }
        }

        return Arrays.copyOf(fields, count);
    }
}
