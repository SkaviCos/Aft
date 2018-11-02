package utils;

import helpers.ConstantHelper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import pojo.CountryItem;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Api {

    public static String getValueByName(String consumer, String name) {

        Map<String, Supplier<String>> supplierMap = new HashMap<>();

        supplierMap.put("country from whitelist", () -> getRandomCountryByCryptoRestrictions(false).getName());
        supplierMap.put("country from blacklist", () -> getRandomCountryByCryptoRestrictions(true).getName());
        supplierMap.put("free phone number", Api::generateFreePhoneNumber);
        supplierMap.put("free email", Api::generateFreeEmail);

        if (supplierMap.containsKey(name)) {
            return supplierMap.get(name).get();
        }
        return name;
    }

    public static CountryItem getRandomCountryByCryptoRestrictions(boolean isRestricted) {

        Response r = mobile("/countries");
        CountryItem[] allCountries = r.as(CountryItem[].class);
        List<CountryItem> countries = Arrays.stream(allCountries)
                .filter(c -> c.getHasCryptoRestrictions().equals(isRestricted)).collect(Collectors.toList());
        if (countries.isEmpty()) {
            return new CountryItem();
        } else {
            return countries.get(new Random().nextInt(countries.size()));
        }

    }

    public static String generateFreePhoneNumber() {
        for (int i = 0; i < 10; i++) {
            //700000 00001-700000 29999
            String phoneNumber = "700000" + new Random().nextInt(3) + RandomStringUtils.random(4, false, true);
            Response r = management("/testers/mobile?mobile=" + phoneNumber);
            if (r.prettyPrint().equals("null")) {
                return phoneNumber;
            }
        }
        return "";
    }

    public static String generateFreeEmail() {
        for (int i = 0; i < 10; i++) {
            //%+test@%.com
            String email = String.format("%s+test@%s.com",
                    RandomStringUtils.randomAlphabetic(5),
                    RandomStringUtils.randomAlphabetic(3)
            );
            Response r = management("/testers/email?email=" + email);
            if (r.prettyPrint().equals("null")) {
                return email;
            }
        }
        return "";
    }


    public static Response management(String endPoint) {
        return RestAssured.get("https://api-beta.crypterium.io/management" + endPoint);
    }

    public static Response mobile(String endPoint) {
        return mobile(endPoint, new HashMap<>());
    }

    public static Response mobile(String endPoint, Map<String, String> body) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-PlatformId", ConstantHelper.X_PLATFORM_ID);
        return RestAssured.given().headers(headers).get("https://api-beta.crypterium.io/mobile/1.0" + endPoint, body);

    }

    public static void main(String[] args) {
        String s ="";
    }
}
