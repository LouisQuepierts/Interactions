package net.quepierts.interactions.main.data.var;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.main.config.objects.VarData;
import net.quepierts.interactions.main.utils.math.number.IMutableNumber;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static net.quepierts.interactions.main.data.var.VarType.BOOLEAN;
import static net.quepierts.interactions.main.data.var.VarType.NUMBER;

public class VarManager {
    private static final UUID STATIC = new UUID(0, 0);
    private static final Map<String, VarInfo> registryPersonal = new HashMap<>();
    private static final Map<String, VarInfo> registryStatic = new HashMap<>();

    private static final Map<UUID, Storage> storage = new HashMap<>();

    private static final int[] countPersonal = new int[3];
    private static final int[] countStatic = new int[3];

    private static Storage defP;

    private static boolean init = false;

    public static boolean register(@NotNull String varName, String def, VarType varType, boolean global) {
        if (!init) {
            Map<String, VarInfo> map = global ? registryStatic : registryPersonal;
            if (!map.containsKey(varName)) {
                VarInfo varInfo = new VarInfo(def, global ? countStatic[varType.index()]++ : countPersonal[varType.index()]++, varType);
                map.put(varName, varInfo);
                return true;
            }
        }

        return false;
    }

    @Nullable
    public static String getString(@Nullable Player player, @NotNull String varName) {
        boolean global = player == null;
        VarInfo info = getInfo(varName, global);

        if (info != null) {
            return storage.get(getUUID(player)).getString(info.index);
        }

        return null;
    }

    public static IMutableNumber<?> getINumber(@Nullable Player player, @NotNull String varName) {
        boolean global = player == null;
        VarInfo info = getInfo(varName, global);

        if (info != null && info.type.equals(NUMBER)) {
            try {
                return storage.get(getUUID(player)).getNumber(info.index);
            } catch (Exception ignored) {}
        }

        return IMutableNumber.get(0);
    }

    public static double getDouble(@Nullable Player player, @NotNull String varName) {
        return getINumber(player, varName).doubleValue();
    }

    public static float getFloat(@Nullable Player player, @NotNull String varName) {
        return getINumber(player, varName).floatValue();
    }

    public static int getInteger(@Nullable Player player, @NotNull String varName) {
        return getINumber(player, varName).intValue();
    }

    public static long getLong(@Nullable Player player, @NotNull String varName) {
        return getINumber(player, varName).longValue();
    }

    public static boolean getBoolean(@Nullable Player player, @NotNull String varName) {
        boolean global = player == null;
        VarInfo info = getInfo(varName, global);

        if (info != null && info.type.equals(BOOLEAN)) {
            return storage.get(getUUID(player)).getBoolean(info.index);
        }

        return false;
    }

    public static void trySet(@Nullable Player player, @NotNull String varName, String value) {
        boolean global = player == null;
        VarInfo info = getInfo(varName, global);

        UUID uuid = global ? STATIC : player.getUniqueId();

        if (info != null) {
            if (VarData.isLegalValue(info.type, value)) {
                storage.get(uuid).set(info, value);
            }
        }
    }

    public static void setString(@Nullable Player player, @NotNull String varName, String value) {
        boolean global = player == null;
        VarInfo info = getInfo(varName, global);

        if (info != null) {
            storage.get(getUUID(player)).setString(info.index, value);
        }
    }

    public static void setNumber(@Nullable Player player, @NotNull String varName, Number value) {
        boolean global = player == null;
        VarInfo info = getInfo(varName, global);

        if (info != null && info.type.equals(NUMBER)) {
            storage.get(getUUID(player)).getNumber(info.index).setValue(value);
        }
    }

    public static void setNumber(@Nullable Player player, @NotNull String varName, String value) {
        boolean global = player == null;
        VarInfo info = getInfo(varName, global);

        if (IMutableNumber.isNumber(value) && info != null && info.type.equals(NUMBER)) {
            storage.get(getUUID(player)).getNumber(info.index).setValue(IMutableNumber.getNumber(value));
        }
    }

    public static void setBoolean(@Nullable Player player, @NotNull String varName, boolean value) {
        boolean global = player == null;
        VarInfo info = getInfo(varName, global);

        if (info != null && info.type.equals(BOOLEAN)) {
            storage.get(getUUID(player)).setBoolean(info.index, value);
        }
    }

    @Nullable
    public static VarType getVarType(@NotNull String varName, boolean global) {
        VarInfo info = getInfo(varName, global);

        if (info != null) {
            return info.type;
        }

        return null;
    }

    public static boolean isLegal(@NotNull String varName, boolean global) {
        if (global) {
            return registryStatic.containsKey(varName);
        } else {
            return registryPersonal.containsKey(varName);
        }
    }

    public static void setDefault(Player player) {
        Storage playerStorage = storage.get(player.getUniqueId());

        if (playerStorage == null) {
            playerStorage = new Storage(defP);
        } else {
            playerStorage.set(defP);
        }

        storage.put(player.getUniqueId(), playerStorage);
    }

    public static boolean setDefault(Player player, @NotNull String varName) {
        if (!registryPersonal.containsKey(varName)) {
            Interactions.logger.warning("Nonexistent var: " + varName);
            return false;
        }

        VarInfo info = registryPersonal.get(varName);
        Storage storage = VarManager.storage.get(player.getUniqueId());
        if (storage != null) {
            storage.setDefault(info);
        } else {
            add(player);
        }

        return true;
    }

    public static boolean setDefault(@NotNull String varName) {
        if (!registryStatic.containsKey(varName)) {
            Interactions.logger.warning("Nonexistent var: " + varName);
            return false;
        }

        VarInfo info = registryStatic.get(varName);
        VarManager.storage.get(STATIC).setDefault(info);

        return true;
    }

    public static void add(Player player) {
        storage.put(player.getUniqueId(), new Storage(defP));
    }

    public static void erase(Player player) {
        storage.remove(player.getUniqueId());
    }

    public static void init(Interactions plugin) {
        init = true;
        VarManager.storage.put(STATIC, new Storage(countStatic, registryStatic.values()));
        defP = new Storage(countPersonal, registryPersonal.values());

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            add(player);
        }
    }

    public static void cleanUp() {
        init = false;

        for (int i = 0; i < 3; i++) {
            countPersonal[i] = 0;
            countStatic[i] = 0;
        }

        registryPersonal.clear();
        registryStatic.clear();
        storage.clear();
    }

    public static void load(ConfigurationSection config) {
        for (String key : config.getKeys(false)) {
            if (key.equals("global")) {
                load(config.getConfigurationSection(key), true);
            } else if (key.equals("personal")) {
                load(config.getConfigurationSection(key), false);
            }
        }
    }

    private static void load(ConfigurationSection config, boolean global) {
        VarType type;
        String def;
        ConfigurationSection varInfo;
        for (String key : config.getKeys(false)) {
            varInfo = config.getConfigurationSection(key);

            type = VarType.parse(varInfo.getString("type"));
            def = varInfo.getString("default");

            if (type == null || def == null) {
                continue;
            }

            if (global) {
                if (!registryStatic.containsKey(key)) {
                    registryStatic.put(key, new VarInfo(def, countStatic[type.index()] ++, type));
                }
            } else {
                if (!registryPersonal.containsKey(key)) {
                    registryPersonal.put(key, new VarInfo(def, countPersonal[type.index()] ++, type));
                }
            }
        }
    }

    @Nullable
    private static VarInfo getInfo(@NotNull String varName, boolean global) {
        if (global) {
            return registryStatic.get(varName);
        } else {
            return registryPersonal.get(varName);
        }
    }

    private static UUID getUUID(@Nullable Player player) {
        return player == null ? STATIC : player.getUniqueId();
    }

    private static void setValue(@Nullable Player player, @NotNull VarInfo info, String value) {
        UUID uuid = player == null ? STATIC : player.getUniqueId();
        storage.get(uuid).set(info, value == null ? "null" : value);
    }

    private static class Storage {
        private final String[] strings;
        private final IMutableNumber<?>[] numbers;
        private final boolean[] booleans;

        private Storage(Storage storage) {
            this.strings = Arrays.copyOf(storage.strings, storage.strings.length);
            this.numbers = Arrays.copyOf(storage.numbers, storage.numbers.length);
            this.booleans = Arrays.copyOf(storage.booleans, storage.booleans.length);
        }

        private Storage(int[] count) {
            strings = new String[count[0]];
            numbers = new IMutableNumber[count[1]];
            booleans = new boolean[count[2]];
        }

        private Storage(int[] count, Collection<VarInfo> info) {
            this(count);

            for (VarInfo value : info) {
                setDefault(value);
            }
        }

        private String getString(int index) {
            return strings[index];
        }

        private IMutableNumber<?> getNumber(int index) {
            return numbers[index];
        }

        private boolean getBoolean(int index) {
            return booleans[index];
        }

        private void setDefault(VarInfo info) {
            switch (info.type) {
                case STRING:
                    strings[info.index] = info.def;
                    break;
                case NUMBER:
                    numbers[info.index] = IMutableNumber.get(info.def);
                    break;
                case BOOLEAN:
                    booleans[info.index] = Boolean.parseBoolean(info.def);
            }
        }

        private void set(VarInfo info, String value) {
            switch (info.type) {
                case STRING:
                    strings[info.index] = value;
                    break;
                case NUMBER:
                    numbers[info.index] = IMutableNumber.get(value);
                    break;
                case BOOLEAN:
                    booleans[info.index] = Boolean.parseBoolean(value);
            }
        }

        private void set(Storage storage) {
            System.arraycopy(storage.strings, 0, strings, 0, strings.length);
            System.arraycopy(storage.numbers, 0, numbers, 0, numbers.length);
            System.arraycopy(storage.booleans, 0, booleans, 0, booleans.length);
        }

        private void setString(int index, String value) {
            strings[index] = value;
        }

        private void setNumber(int index, IMutableNumber<?> value) {
            numbers[index] = value;
        }

        private void setBoolean(int index, boolean value) {
            booleans[index] = value;
        }
    }
}
