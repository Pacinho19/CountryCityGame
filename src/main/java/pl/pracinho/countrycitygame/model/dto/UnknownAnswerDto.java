package pl.pracinho.countrycitygame.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.pracinho.countrycitygame.model.enums.Category;

@Setter
@Getter
@NoArgsConstructor
public class UnknownAnswerDto {

    private Category category;
    private String value;

    public UnknownAnswerDto(Category category, String value) {
        this.category = category;
        this.value = value;
    }

    @Setter
    private Boolean correct = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnknownAnswerDto that = (UnknownAnswerDto) o;

        if (category != that.category) return false;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        int result = category.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
