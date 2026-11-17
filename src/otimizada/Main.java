package otimizada;

import java.util.stream.IntStream;

/** Fluxo de inteiros primitivos sem uma coleção intermediária. */
public class Main {
    public static long calcular(int limite) {
        if (limite < 0 || limite > 1_000_000) {
            throw new IllegalArgumentException("Use um limite entre 0 e 1000000.");
        }
        return IntStream.rangeClosed(1, limite)
                .filter(numero -> numero % 2 == 0)
                .asLongStream()
                .sum();
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
