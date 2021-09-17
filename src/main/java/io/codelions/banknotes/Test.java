package io.codelions.banknotes;

public class Test {
    public static void main(String[] args) throws Exception {
        CassetteStorage storage = new CassetteStorage();

        var availableMoney = storage.getStorage().stream()
                .mapToInt(cassette -> cassette.sum())
                .sum();
        System.out.println(availableMoney);
    }
}
