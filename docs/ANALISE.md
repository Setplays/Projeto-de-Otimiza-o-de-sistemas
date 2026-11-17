# Análise do processo de otimização

## 1. Introdução

O sistema soma os inteiros pares do intervalo de 1 até n. O objetivo é manter o resultado enquanto se reduz o armazenamento intermediário e se torna a intenção do cálculo mais explícita. Para n = 1.000, o resultado é 250.500.

## 2. Desenvolvimento e decisões

1. **Baseline:** o algoritmo original armazena todos os números em `ArrayList<Integer>`, depois percorre a lista por índice e testa a paridade. A geração e a soma são etapas separadas. As inclusões têm custo amortizado constante e `get` tem custo constante; portanto, o conjunto permanece O(n), não O(n²).
2. **Fluxo primitivo:** `IntStream.rangeClosed(1, limite)` gera o intervalo inclusivo sem materializar uma lista. A execução é sequencial e preguiçosa: os números são processados à medida que a operação terminal os solicita.
3. **Filtro:** `filter(numero -> numero % 2 == 0)` explicita a seleção dos pares. O teste de paridade continua existindo, mas desaparecem acessos indexados e a coleção intermediária.
4. **Acumulação:** `asLongStream().sum()` mantém a soma em `long`. O fluxo já contém primitivos: não há motivo para fazer `boxed()` e depois `mapToInt(Integer::intValue)`. As duas versões usam `long`, pois, por exemplo, o resultado para 100.000 é 2.500.050.000, que excede `int`.
5. **Testabilidade e manutenção:** ambas expõem `calcular(int)` sem impressão. Isso separa a regra do programa de sua entrada e saída. A validação e a interface de console são equivalentes; valores não inteiros, negativos, acima do limite e argumentos em excesso encerram a aplicação com código 1 e mensagem de erro.

O exemplo otimizado do enunciado ainda cria uma lista com `boxed().collect(...)`. A versão deste projeto dá um passo adicional: remove essa lista. Streams, lambdas e `mapToInt` não são, isoladamente, garantias de desempenho; a eliminação da coleção é a mudança estrutural relevante aqui. Também não há paralelização automática em `stream()` ou no fluxo sequencial usado neste projeto.

| Critério | Original | Otimizada |
| --- | --- | --- |
| Estrutura intermediária | ArrayList e referências/objetos Integer | Sem coleção |
| Complexidade temporal | O(n), com duas etapas de percurso | O(n), com pipeline único |
| Memória auxiliar | O(n) | O(1), para este pipeline sequencial sem operações com estado |
| Resultado para 1.000 | 250.500 | 250.500 |
| Acumulador | long | long |
| Legibilidade | Passos explícitos e familiares | Intenção declarativa mais concisa |

Uma fórmula matemática permitiria tempo O(1) neste problema específico. Ela foi usada como oráculo dos testes, não como terceira versão, para manter o foco na otimização de processamento de sequências proposta no enunciado.

## 3. Resultados

Execução real em 17/11/2026, OpenJDK 21.0.13, ambiente Linux desta implementação, com limite 100.000:

| Medida | Original | Otimizada |
| --- | ---: | ---: |
| Soma | 2.500.050.000 | 2.500.050.000 |
| Mediana de tempo por operação | 0,419 ms | 0,065 ms |

São observações de uma execução, arredondadas, e não uma promessa de aceleração em outras máquinas. Os resultados não constituem uma medição de memória. JIT, coleta de lixo, frequência da CPU, carga e ordem de execução influenciam o tempo. O comparador inclui aquecimento e alternância de ordem, mas não elimina todos esses fatores; para conclusões rigorosas, recomenda-se JMH com processos independentes e várias configurações.

Os testes passaram em 116 verificações, incluindo o maior limite aceito, valores aleatórios e rejeição de entradas inválidas. Ambas as aplicações produziram `Soma dos números pares: 250500` sem argumentos.

O ganho estrutural é a passagem de memória auxiliar linear para constante. A complexidade temporal permanece linear. A redução observada no tempo é compatível com a remoção da lista e de seu custo de alocação, mas o experimento não isola a contribuição de cada fator. A separação entre cálculo e console facilita a manutenção e a repetição das verificações.

## 4. Referências

ORACLE. **IntStream: Java Platform, Standard Edition 8 API Specification**. Disponível em: <https://docs.oracle.com/javase/8/docs/api/java/util/stream/IntStream.html>. Acesso em: 17 nov. 2026.

ORACLE. **ArrayList: Java Platform, Standard Edition 8 API Specification**. Disponível em: <https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html>. Acesso em: 17 nov. 2026.

**PROJETO DE EXTENSÃO: disciplina Análise e Otimização de Sistema**. Material da atividade fornecido pelo usuário, sem autoria identificada. Data interna desconsiderada conforme orientação do usuário.
