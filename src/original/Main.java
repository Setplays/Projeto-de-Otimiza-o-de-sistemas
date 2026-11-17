package original;

import java.util.ArrayList;
import java.util.List;

/** Algoritmo do exemplo original: cria uma lista e depois soma os pares. */
public class Main {
    public static long calcular(int limite) {
        if (limite < 0 || limite > 1_000_000) {
            throw new IllegalArgumentException("Use um limite entre 0 e 1000000.");
        }
        List<Integer> numeros = new ArrayList<>();
        for (int i = 1; i <= limite; i++) {
            numeros.add(i);
        }
        long somaPares = 0;
        for (int i = 0; i < numeros.size(); i++) {
            if (numeros.get(i) % 2 == 0) {
                somaPares += numeros.get(i);
            }
        }
        return somaPares;
    }

    public static void main(String[] args) {
        try {
            if (args.length > 1) throw new IllegalArgumentException("Informe apenas um limite.");
            int limite = args.length == 0 ? 1000 : Integer.parseInt(args[0]);
            System.out.println("Soma dos números pares: " + calcular(limite));
        } catch (IllegalArgumentException e) {
            System.err.println("Entrada inválida: " + e.getMessage());
            System.exit(1);
        }
    }
}
