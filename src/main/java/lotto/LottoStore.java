package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import java.util.ArrayList;

public class LottoStore {

    private List<Lotto> myLottos;
    private Lotto winningNumber;
    private int bonusNumber;

    public LottoStore() {
        myLottos = new ArrayList<Lotto>();
    }

    public void purchaseLotto() {
        while (true) {
            try {
                System.out.println("구입금액을 입력해 주세요.");
                String input = Console.readLine();
                validateMoney(input);
                issueLotto(Integer.parseInt(input));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void printLottos() {
        System.out.println(myLottos.size() + "개를 구매했습니다.");
        for (Lotto lotto : myLottos) {
            lotto.printLotto();
        }
    }

    public void getWinningNumber() {
        while (true) {
            try {
                System.out.println("당첨 번호를 입력해주세요.");
                String input = Console.readLine();
                List<Integer> numbers = parseString(input);
                this.winningNumber = new Lotto(numbers);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private List<Integer> parseString(String input) {
        String[] splitInput = input.split(",");
        validateInput(splitInput);
        // 마지막이 쉼표로 끝나는 경우 split에서 사라지므로 해당 메서드에서 예외 처리
        if (input.charAt(input.length() - Value.ONE.get()) == ',') {
            throw new IllegalArgumentException("[ERROR] 쉼표로 구분된 값이 비어있습니다.");
        }

        List<Integer> numbers = new ArrayList<>();
        for (String s : splitInput) {
            numbers.add(Integer.parseInt(s));
        }
        return numbers;
    }

    private void validateInput(String[] input) {
        if (input.length == Value.IS_EMPTY.get()) {
            throw new IllegalArgumentException("[ERROR] 입력된 값이 없습니다.");
        }
        for (String s : input) {
            if (s.isEmpty()) {
                throw new IllegalArgumentException("[ERROR] 쉼표로 구분된 값이 비어있습니다.");
            }
        }
    }

    private void issueLotto(int input) {
        int number = input / Value.LOTTO_PRICE.get();
        for (int i = Value.ZERO.get(); i < number; ++i) {
            List<Integer> lottoNumbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
            myLottos.add(new Lotto(lottoNumbers));
        }
    }

    private void validateMoney(String number) {
        for (int i = Value.ZERO.get(); i < number.length(); ++i) {
            if (number.charAt(i) < '0' || number.charAt(i) > '9') {
                throw new IllegalArgumentException("[ERROR] 숫자로 된 금액을 입력해야 합니다.");
            }
        }
        if (Integer.parseInt(number) <= Value.ZERO.get()) {
            throw new IllegalArgumentException("[ERROR] 금액은 양수를 입력해야 합니다");
        }
        if (Integer.parseInt(number) % Value.LOTTO_PRICE.get() != Value.ZERO.get()) {
            throw new IllegalArgumentException("[ERROR] 1,000원 단위로 금액을 입력해야 합니다.");
        }
    }
}
