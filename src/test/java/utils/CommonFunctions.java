package utils;

import helpers.ConstantHelper;

import java.security.NoSuchProviderException;

public class CommonFunctions {



    public static String getValueByTypeAndName(String consumer, String value) {

        String[] typeAndName = value.split(ConstantHelper.VALUE_SEPARATOR);
        if (typeAndName.length > 1) {
            String type = typeAndName[0].toUpperCase().trim();
            String name = typeAndName[1].trim();

            switch (type) {
                case "STASH":
                    String[] params = name.split(":");
                    if (params.length > 1) {
                        switch (params[0].toUpperCase().trim()) {
                            case "LOCAL":
                                return Stash.getOrDefault(consumer, params[1].trim(), "").toString();
                            case "GLOBAL":
                                return Stash.getOrDefault(params[1].trim(), "").toString();
                            default:
                                throw new RuntimeException(String.format("Method %s not found", params[0]));
                        }
                    } else {
                        return Stash.getOrDefault(consumer, name, "").toString();
                    }

                case "API":
                    return Api.getValueByName(consumer, name.toLowerCase());
            }

        }
        return value;
    }
}
