package com.example.tournowadmin;

public class StorePlaceData {
    String divisionValue, placeType, placeName, aboutPlace, guidePlace, placeImageUrl, districtValue;

    public StorePlaceData(String divisionValue, String placeType, String placeName, String aboutPlace, String guidePlace, String placeImageUrl, String districtValue) {
        this.divisionValue = divisionValue;
        this.placeType = placeType;
        this.placeName = placeName;
        this.aboutPlace = aboutPlace;
        this.guidePlace = guidePlace;
        this.placeImageUrl = placeImageUrl;
        this.districtValue = districtValue;
    }

    public StorePlaceData() {
    }

    public String getDivisionValue() {
        return divisionValue;
    }

    public void setDivisionValue(String divisionValue) {
        this.divisionValue = divisionValue;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAboutPlace() {
        return aboutPlace;
    }

    public void setAboutPlace(String aboutPlace) {
        this.aboutPlace = aboutPlace;
    }

    public String getGuidePlace() {
        return guidePlace;
    }

    public void setGuidePlace(String guidePlace) {
        this.guidePlace = guidePlace;
    }

    public String getPlaceImageUrl() {
        return placeImageUrl;
    }

    public void setPlaceImageUrl(String placeImageUrl) {
        this.placeImageUrl = placeImageUrl;
    }

    public String getDistrictValue() {
        return districtValue;
    }

    public void setDistrictValue(String districtValue) {
        this.districtValue = districtValue;
    }
}
