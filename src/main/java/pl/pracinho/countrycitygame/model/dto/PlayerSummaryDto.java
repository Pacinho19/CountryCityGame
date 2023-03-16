package pl.pracinho.countrycitygame.model.dto;

import pl.pracinho.countrycitygame.model.enums.Place;
import pl.pracinho.countrycitygame.utils.TimeUtils;

public record PlayerSummaryDto(String name, int points, long summaryTime, Place place) {

    public String getSummaryTimeText() {
        return TimeUtils.timeLeft(this.summaryTime);
    }

}
