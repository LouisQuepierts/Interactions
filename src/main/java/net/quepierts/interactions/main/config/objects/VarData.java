package net.quepierts.interactions.main.config.objects;

import net.quepierts.interactions.Interactions;
import net.quepierts.interactions.main.data.var.VarManager;
import net.quepierts.interactions.main.data.var.VarType;
import net.quepierts.interactions.main.utils.math.number.IMutableNumber;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class VarData {
    private static final Pattern format = Pattern.compile("^(-&[\\w_]+)");
    private static final Map<String, VarData> CACHE = new HashMap<>();
    private static VarType varType = null;

    public final String varID;
    public final boolean global;

    public static VarData getInstance(Object[] args) {
        String varID = (String) args[0];
        boolean global = (boolean) args[1];
        varType = VarManager.getVarType(varID, global);
        if (varType != null) {
            return getData(varID, global);
        }

        Interactions.logger.warning("Illegal Var Name: " + varID + ", isGlobal: " + global);
        return null;
    }

    @Nullable
    public static VarData parse(@NotNull String arg) {
        if (format.matcher(arg).find()) {
            String[] info = arg.split(" ");
            String varID = info[0].replace("-&", "");
            boolean global = !(info.length == 2 && "-p".equals(info[1]));

            if (VarManager.isLegal(varID, global)) {
                return getData(varID, global);
            }

            Interactions.logger.warning("Illegal Var Name: " + varID + ", isGlobal: " + global);
        } else {
            Interactions.logger.warning("Illegal format of VarData info: " + arg + ", Required Format: \"&VarName (-p)\"");
        }

        return null;
    }

    private static VarData getData(@NotNull String varID, boolean global) {
        String index = varID + (global ? "_g" : "_p");
        VarData data = CACHE.get(index);

        if (data != null) {
            return data;
        }

        return CACHE.put(index, new VarData(varID, global));
    }

    private VarData(String varID, boolean global) {
        this.varID = varID;
        this.global = global;
    }

    public static boolean isLegalValue(String arg) {
        if (varType != null) {
            return isLegalValue(varType, arg);
        }

        return false;
    }

    public static boolean isLegalValue(@NotNull VarType type, String arg) {
        switch (type) {
            case NUMBER:
                return IMutableNumber.isNumber(arg);
            case BOOLEAN:
                return arg.equalsIgnoreCase("true") || arg.equalsIgnoreCase("false");
        }

        return false;
    }
}
