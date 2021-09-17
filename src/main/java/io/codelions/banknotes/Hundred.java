package io.codelions.banknotes;

import lombok.Data;

@Data
public class Hundred extends Banknote {
    public Hundred() {
        super(100);
    }
}
