package testes;

import java.util.Random;

/** Testes sem dependências externas; falhas lançam AssertionError. */
public class TesteSomaPares {
    private static int verificacoes;

    private static void verificar(int limite) {
        long quantidade = limite / 2;
        long esperado = quantidade * (quantidade + 1);
        long resultadoOriginal = original.Main.calcular(limite);
        long resultadoOtimizado = otimizada.Main.calcular(limite);
        if (resultadoOriginal != esperado || resultadoOtimizado != esperado) {
            throw new AssertionError("Falha para limite " + limite);
        }
        verificacoes++;
    }

    public static void main(String[] args) {
        int[] limites = {0, 1, 2, 3, 10, 999, 1000, 1001, 100_000, 1_000_000};
        for (int limite : limites) verificar(limite);
        Random random = new Random(42);
        for (int i = 0; i < 100; i++) verificar(random.nextInt(10_001));
        for (int invalido : new int[] {-1, 1_000_001, Integer.MAX_VALUE}) {
            rejeitar(() -> original.Main.calcular(invalido));
            rejeitar(() -> otimizada.Main.calcular(invalido));
        }
        System.out.println("OK: " + verificacoes + " verificações passaram.");
    }

    private static void rejeitar(Runnable acao) {
        try {
            acao.run();
        } catch (IllegalArgumentException esperado) {
            verificacoes++;
            return;
        }
        throw new AssertionError("Entrada inválida foi aceita.");
    }
}
