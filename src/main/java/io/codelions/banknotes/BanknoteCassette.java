package io.codelions.banknotes;

import java.util.Arrays;

public class BanknoteCassette<T extends Banknote> {

    private int size;

    private Banknote[] banknotes;

    private int nominal;

    public BanknoteCassette() {
        this(10);
    }

    public BanknoteCassette(int size) {
        this.size = size;
        banknotes = new Banknote[this.size];
    }

    public int sum() {
        return Arrays.stream(banknotes)
                .mapToInt(banknote -> banknote.getNominal())
                .sum();
    }

    public int removeBanknote() {
        var tmpBanknotes = new Banknote[this.banknotes.length - 1];
        System.arraycopy(banknotes, 1, tmpBanknotes, 0, banknotes.length - 1);
        banknotes = tmpBanknotes;
        tmpBanknotes = null;
        size--;
        return nominal;
    }

    public BanknoteCassette fillSlots(Banknote banknote) {
        for (int i = 0; i < banknotes.length; i++) {
            banknotes[i] = banknote;
        }
        return this;
    }

    public int getNominal() {
        return nominal;
    }
    public String getStringNominal() {
        return String.valueOf(nominal);
    }
    public BanknoteCassette setNominal(int nominal) {
        this.nominal = nominal;
        return this;
    }

    public int size() {
        return size;
    }

    public boolean doesItHaveEnoughMoney(int amount) {
        return sum() >= amount;
    }
}
