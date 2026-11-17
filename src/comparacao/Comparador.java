package comparacao;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.IntToLongFunction;

/** Medição didática, sem impressão dentro da região cronometrada. */
public class Comparador {
    private static volatile long resultadoConsumido;

    private static long medir(IntToLongFunction algoritmo, int limite, int repeticoes) {
        long acumulador = 0;
        long inicio = System.nanoTime();
        for (int i = 0; i < repeticoes; i++) acumulador += algoritmo.applyAsLong(limite);
        long tempo = System.nanoTime() - inicio;
        resultadoConsumido = acumulador;
        return tempo / repeticoes;
    }

    public static void main(String[] args) {
        int limite = args.length == 0 ? 100_000 : Integer.parseInt(args[0]);
        long a = original.Main.calcular(limite);
        long b = otimizada.Main.calcular(limite);
        if (a != b) throw new AssertionError("Resultados diferentes.");
        int repeticoes = 20;
        for (int i = 0; i < 10; i++) {
            medir(original.Main::calcular, limite, repeticoes);
            medir(otimizada.Main::calcular, limite, repeticoes);
        }
        long[] temposOriginal = new long[11];
        long[] temposOtimizada = new long[11];
        for (int i = 0; i < temposOriginal.length; i++) {
            if (i % 2 == 0) {
                temposOriginal[i] = medir(original.Main::calcular, limite, repeticoes);
                temposOtimizada[i] = medir(otimizada.Main::calcular, limite, repeticoes);
            } else {
                temposOtimizada[i] = medir(otimizada.Main::calcular, limite, repeticoes);
                temposOriginal[i] = medir(original.Main::calcular, limite, repeticoes);
            }
        }
        Arrays.sort(temposOriginal);
        Arrays.sort(temposOtimizada);
        System.out.println("Java: " + System.getProperty("java.version"));
        System.out.println("Limite: " + limite + "; soma: " + a);
        System.out.println("11 amostras de 20 execuções após 200 aquecimentos por versão.");
        System.out.printf(Locale.ROOT, "Original (mediana): %.3f ms/op%n", temposOriginal[5] / 1e6);
        System.out.printf(Locale.ROOT, "Otimizada (mediana): %.3f ms/op%n", temposOtimizada[5] / 1e6);
        System.out.println("Medição didática; os tempos variam conforme JVM, JIT, GC e hardware.");
    }
}
