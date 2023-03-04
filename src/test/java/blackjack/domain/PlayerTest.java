package blackjack.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayerTest {

    @Test
    @DisplayName("딜러의 점수가 내 점수보다 낮으면 승리해야 한다.")
    void matchGame_win() {
        // given
        Player player = new Player("glen", List.of(
                new Card(Suit.DIAMOND, Rank.KING)
        ));
        Dealer dealer = new Dealer();

        // when
        GameResult gameResult = player.matchGame(dealer);

        // then
        assertThat(gameResult)
                .isEqualTo(GameResult.WIN);
    }

    @Test
    @DisplayName("딜러의 점수가 내 점수보다 높으면 패배해야 한다.")
    void matchGame_lose() {
        // given
        Player player = new Player("glen");
        Dealer dealer = new Dealer(List.of(
                new Card(Suit.DIAMOND, Rank.KING)
        ));

        // when
        GameResult gameResult = player.matchGame(dealer);

        // then
        assertThat(gameResult)
                .isEqualTo(GameResult.LOSE);
    }

    @Test
    @DisplayName("딜러의 점수가 나와 같으면 무승부여야 한다.")
    void matchGame_draw() {
        // given
        Player player = new Player("glen", List.of(
                new Card(Suit.DIAMOND, Rank.KING)
        ));
        Dealer dealer = new Dealer(List.of(
                new Card(Suit.DIAMOND, Rank.KING)
        ));

        // when
        GameResult gameResult = player.matchGame(dealer);

        // then
        assertThat(gameResult)
                .isEqualTo(GameResult.DRAW);
    }

    @Test
    @DisplayName("나의 점수가 21점을 넘으면 패배해야 한다.")
    void matchGame_overScore() {
        // given
        Player player = new Player("glen", List.of(
                new Card(Suit.DIAMOND, Rank.KING),
                new Card(Suit.DIAMOND, Rank.KING),
                new Card(Suit.DIAMOND, Rank.KING)
        ));
        Dealer dealer = new Dealer();

        // when
        GameResult gameResult = player.matchGame(dealer);

        // then
        assertThat(gameResult)
                .isEqualTo(GameResult.LOSE);
    }

    @Test
    @DisplayName("딜러의 점수가 21점을 넘고 내가 넘지 않으면 승리해야 한다.")
    void matchGame_otherOverScore() {
        // given
        Player player = new Player("glen");
        Dealer dealer = new Dealer(List.of(
                new Card(Suit.DIAMOND, Rank.KING),
                new Card(Suit.DIAMOND, Rank.KING),
                new Card(Suit.DIAMOND, Rank.KING)
        ));

        // when
        GameResult gameResult = player.matchGame(dealer);

        // then
        assertThat(gameResult)
                .isEqualTo(GameResult.WIN);
    }

    @Test
    @DisplayName("딜러와 나의 점수가 21점을 넘으면 패배해야 한다.")
    void matchGame_sameOverScore() {
        // given
        Player player = new Player("glen", List.of(
                new Card(Suit.DIAMOND, Rank.KING),
                new Card(Suit.DIAMOND, Rank.KING),
                new Card(Suit.DIAMOND, Rank.KING)
        ));
        Dealer dealer = new Dealer(List.of(
                new Card(Suit.DIAMOND, Rank.KING),
                new Card(Suit.DIAMOND, Rank.KING),
                new Card(Suit.DIAMOND, Rank.KING)
        ));

        // when
        GameResult gameResult = player.matchGame(dealer);

        // then
        assertThat(gameResult)
                .isEqualTo(GameResult.LOSE);
    }

    @Test
    @DisplayName("Player는 플레이어여야 한다.")
    void isPlayer_true() {
        // given
        Player player = new Player("glen");

        // expect
        assertThat(player.isPlayer())
                .isTrue();
    }

    @Test
    @DisplayName("이름이 딜러면 예외가 발생해야 한다.")
    void validateBlacklist() {
        // expect
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new Player("딜러");
        }).withMessage("[ERROR] 딜러는 사용할 수 없는 이름입니다.");
    }

    @Test
    @DisplayName("Player가 정상적으로 생성되어야 한다.")
    void create_success() {
        // given
        Player player = new Player("123");

        // expect
        assertThat(player.getName())
                .isEqualTo("123");
    }
}
