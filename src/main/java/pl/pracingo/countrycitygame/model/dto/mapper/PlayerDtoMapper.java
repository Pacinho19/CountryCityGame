package pl.pracingo.countrycitygame.model.dto.mapper;

import pl.pracingo.countrycitygame.model.dto.PlayerDto;
import pl.pracingo.countrycitygame.model.entity.memory.Player;

public class PlayerDtoMapper {
    public static PlayerDto parse(Player player) {
        return new PlayerDto(
                player.getName()
        );
    }
}