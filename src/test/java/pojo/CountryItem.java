
package pojo;

import java.util.List;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryItem {

    @SerializedName("code")
    private String mCode;
    @SerializedName("hasCryptoRestrictions")
    private Boolean mHasCryptoRestrictions;
    @SerializedName("imageUrl")
    private String mImageUrl;
    @SerializedName("mobilePrefixes")
    private List<String> mMobilePrefixes;
    @SerializedName("name")
    private String mName;
    @SerializedName("regions")
    private List<Object> mRegions;

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public Boolean getHasCryptoRestrictions() {
        return mHasCryptoRestrictions;
    }

    public void setHasCryptoRestrictions(Boolean hasCryptoRestrictions) {
        mHasCryptoRestrictions = hasCryptoRestrictions;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public List<String> getMobilePrefixes() {
        return mMobilePrefixes;
    }

    public void setMobilePrefixes(List<String> mobilePrefixes) {
        mMobilePrefixes = mobilePrefixes;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Object> getRegions() {
        return mRegions;
    }

    public void setRegions(List<Object> regions) {
        mRegions = regions;
    }

}
