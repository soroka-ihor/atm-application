package io.codelions.banknotes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Scope("singleton")
public class CassetteStorage {

    private List<BanknoteCassette> storage;

    public CassetteStorage() {
        storage = new ArrayList<>();
        getStorage().add(new BanknoteCassette<Fifty>(1000).fillSlots(new Fifty()).setNominal(50));
        getStorage().add(new BanknoteCassette<Hundred>(1000).fillSlots(new Hundred()).setNominal(100));
        getStorage().add(new BanknoteCassette<TwoHundred>(1000).fillSlots(new TwoHundred()).setNominal(200));
        getStorage().add(new BanknoteCassette<FiveHundred>(1000).fillSlots(new FiveHundred()).setNominal(500));
    }

    public  List<BanknoteCassette> getStorage() {
        return storage;
    }
    public int availableMoney() {
        return getStorage().stream()
                .mapToInt(cassette -> cassette.sum())
                .sum();
    }
    public BanknoteCassette findLesserOrEqualNominal(int amount) {
        for (int i = getStorage().size() - 1; i >= 0; i--) {
            if (getStorage().get(i).getNominal() <= amount && getStorage().get(i).size() > 0) {
                return getStorage().get(i);
            }
        }
        return null;
    }
    public int getMinimalAvailableNominal() {
        for (int i = 0; i < getStorage().size(); i++) {
            if (getStorage().get(i).size() > 0)
                return getStorage().get(i).getNominal();
        }
        return 0;
    }
}
