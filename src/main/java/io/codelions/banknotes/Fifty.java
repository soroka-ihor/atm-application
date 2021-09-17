package io.codelions.banknotes;

import lombok.Data;

@Data
public class Fifty extends Banknote {

    public Fifty() {
        super(50);
    }
}
