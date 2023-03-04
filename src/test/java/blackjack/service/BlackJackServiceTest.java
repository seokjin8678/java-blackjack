package blackjack.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import blackjack.domain.Card;
import blackjack.domain.Rank;
import blackjack.domain.Suit;
import blackjack.dto.PersonStatusResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BlackJackServiceTest {

    BlackJackService blackJackService;

    @BeforeEach
    void setUp() {
        blackJackService = new BlackJackService(new MockDeckGenerator(new Card(Suit.DIAMOND, Rank.KING), 52));
    }

    @Test
    @DisplayName("참여자가 정상적으로 생성되어야 한다.")
    void createPeople_success() {
        // given
        List<String> names = List.of("glen", "pobi", "encho");

        // when
        blackJackService.createPeople(names);

        // then
        assertThat(blackJackService.getPlayersName())
                .hasSize(3)
                .containsExactly("glen", "pobi", "encho");
    }

    @Test
    @DisplayName("참여자가 생성되면 카드를 2장 소지하고 있어야 한다.")
    void createPeople_haveTwoCard() {
        // given
        List<String> names = List.of("glen");

        // when
        blackJackService.createPeople(names);

        // then
        assertThat(blackJackService.getPersonStatusResponseByName("glen").getCards())
                .hasSize(2);
    }

    @Test
    @DisplayName("참여자의 이름으로 카드를 뽑을 수 있어야 한다.")
    void drawMoreCardByName_success() {
        // given
        blackJackService.createPeople(List.of("glen", "pobi"));

        // when
        blackJackService.drawMoreCardByName("glen");

        // then
        PersonStatusResponse response = blackJackService.getPersonStatusResponseByName("glen");
        assertThat(response.getCards())
                .hasSize(3);
    }

    @Test
    @DisplayName("참여자의 이름으로 카드를 뽑을 때 점수가 21을 초과하면 예외가 발생해야 한다.")
    void drawMoreCardByName_overScore() {
        // given
        blackJackService.createPeople(List.of("glen", "pobi"));

        // when
        blackJackService.drawMoreCardByName("glen");

        // then
        assertThatIllegalStateException().isThrownBy(() -> {
            blackJackService.drawMoreCardByName("glen");
        }).withMessage("[ERROR] 더이상 카드를 뽑을 수 없습니다.");
    }
}
