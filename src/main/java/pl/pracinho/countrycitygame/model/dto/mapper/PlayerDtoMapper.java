package pl.pracinho.countrycitygame.model.dto.mapper;

import pl.pracinho.countrycitygame.model.dto.PlayerDto;
import pl.pracinho.countrycitygame.model.entity.memory.Player;

public class PlayerDtoMapper {
    public static PlayerDto parse(Player player) {
        return new PlayerDto(
                player.getName()
        );
    }
}