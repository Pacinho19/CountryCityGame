package pl.pracinho.countrycitygame.model.dto;

import pl.pracinho.countrycitygame.model.enums.Place;

public record PlayerSummaryDto(String name, int points, Place place) {

}
