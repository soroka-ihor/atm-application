package io.codelions.banknotes;

import lombok.Data;

@Data
public class FiveHundred extends Banknote {
    public FiveHundred() {
        super(500);
    }
}
