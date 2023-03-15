package pl.pracinho.countrycitygame.model.entity.memory;

import lombok.Getter;
import lombok.Setter;
import pl.pracinho.countrycitygame.model.dto.RoundResultDto;
import pl.pracinho.countrycitygame.model.enums.GameStatus;

import java.time.LocalDateTime;
import java.util.*;

@Getter
public class Game {

    private String id;
    @Setter
    private GameStatus status;
    private LinkedList<Player> players;
    private LocalDateTime startTime;
    private int playersCount;
    private Character letter;
    private List<Character> usedLetters;

    private Map<Character, List<Answer>> answers;
    private List<RoundResultDto> results;

    public Game(String player1, int playersCount) {
        this.playersCount = playersCount;
        players = new LinkedList<>();
        players.add(new Player(player1, 1));
        this.id = UUID.randomUUID().toString();
        this.status = GameStatus.NEW;
        this.startTime = LocalDateTime.now();
        this.usedLetters = new ArrayList<>();
        this.answers = new HashMap<>();
        this.results = new LinkedList<>();
    }

    public void setLetter(Character letter) {
        this.letter = letter;
        this.usedLetters.add(letter);
        this.answers.put(letter, new ArrayList<>());
    }

    public void addAnswer(Answer answer) {
        this.answers.get(letter).add(answer);
    }

    public void addRoundResult(RoundResultDto roundResultDto){
        this.results.add(0, roundResultDto);
    }

}
