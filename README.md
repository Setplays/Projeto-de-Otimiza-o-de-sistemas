# Projeto de Análise e Otimização de Código em Java

Aplicação de console baseada no enunciado da disciplina Análise e Otimização de Sistema: somar os números pares de 1 até um limite inclusivo (1.000 por padrão).

## Duas versões

| Versão | Arquivo | Estratégia | Tempo | Memória auxiliar |
| --- | --- | --- | --- | --- |
| Original | `src/original/Main.java` | Preenche `ArrayList<Integer>` e percorre a lista | O(n) | O(n) |
| Otimizada | `src/otimizada/Main.java` | `IntStream` sequencial, `filter`, `asLongStream` e `sum` | O(n) | O(1) |

A versão original preserva o algoritmo do exemplo, mas não é uma transcrição literal: a lógica foi extraída para `calcular`, recebeu limite configurável e validação, pacote e acumulador `long`. Essas adaptações são comuns às duas versões para permitir testes e comparação. O limite aceito vai de 0 a 1.000.000, evitando alocações excessivas na versão original. Não há bibliotecas externas.

## Compilar e executar

Requisito: JDK 8 ou superior, com `java` e `javac` no PATH. Os testes desta entrega foram executados no OpenJDK 21.0.13.

Na raiz do projeto:

```sh
javac -encoding UTF-8 -d out src/original/Main.java src/otimizada/Main.java src/testes/TesteSomaPares.java src/comparacao/Comparador.java
java -cp out original.Main
java -cp out otimizada.Main
```

As duas versões imprimem:

```text
Soma dos números pares: 250500
```

Para outro limite:

```sh
java -cp out original.Main 100000
java -cp out otimizada.Main 100000
```

Para testar e comparar:

```sh
java -cp out testes.TesteSomaPares
java -cp out comparacao.Comparador
java -cp out comparacao.Comparador 1000
```

Os comandos funcionam em terminais Linux, macOS e Windows com JDK instalado. Em uma IDE, abra a pasta, marque `src` como pasta de fontes e execute a classe desejada pelo pacote completo.

## Verificação

Os testes usam a fórmula independente `k * (k + 1)`, em que `k = limite / 2`, como resultado esperado. Incluem zero, intervalo sem pares, limites pares e ímpares, 100 casos pseudoaleatórios com semente fixa, soma acima de `Integer.MAX_VALUE` e entradas fora do intervalo. Resultado observado: **116 verificações passaram**.

O comparador mede o cálculo completo, incluindo a construção da lista original. Realiza 200 aquecimentos por versão e 11 amostras de 20 execuções, alterna a ordem das versões e informa a mediana por execução. O resultado é consumido fora da região cronometrada em campo `volatile` para reduzir eliminação de trabalho pelo compilador. É um experimento didático em um único processo; não substitui benchmark com JMH, múltiplas JVMs e controle de ambiente.

Veja [a análise das melhorias e os resultados medidos](docs/ANALISE.md).

## Contexto acadêmico

Este repositório contém as duas implementações Java, os testes, o comparador e a análise técnica. O exemplo de soma de números pares é usado para estudar otimização de código, complexidade e uso de memória.
