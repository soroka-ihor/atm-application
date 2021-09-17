package io.codelions.banknotes;

import lombok.Data;

@Data
public class Thousand extends Banknote {
    public Thousand() {
        super(1000);
    }
}
