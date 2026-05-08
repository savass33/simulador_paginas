<div align="center">
  <b>UNIVERSIDADE DE FORTALEZA</b><br>
  <b>CENTRO DE CIÊNCIAS TECNOLÓGICAS</b><br>
  <b>CURSO: CIÊNCIA DA COMPUTAÇÃO</b>
</div>

<br>

<h1 align="center">SIMULADOR DE ALGORITMOS DE SUBSTITUIÇÃO DE PÁGINAS</h1>

**Autor 1:** Savas Constantin Petalas Neto - 2410432  
**Autor 2:** Levi de Pontes Lima Santos - 2416516

**Palavras-chave:** Sistemas Operacionais. Memória Virtual. Substituição de Páginas. Algoritmos. Java Swing.

## Resumo
Este trabalho propõe o desenvolvimento de um simulador para avaliar o desempenho de diferentes algoritmos de substituição de páginas em sistemas de gerenciamento de memória virtual. O foco foi a implementação e análise dos métodos FIFO, LRU, do Relógio (Segunda Chance) e Ótimo. A ferramenta desenvolvida em Java permite a entrada de uma cadeia de referências e a quantidade de frames de memória, simulando e comparando o número de faltas de página geradas por cada um dos algoritmos escolhidos através de uma interface interativa.

## Introdução
O gerenciamento eficiente da memória virtual é crucial para o desempenho dos sistemas operacionais. A alocação e substituição de páginas são tarefas complexas que afetam diretamente a experiência do usuário. Neste contexto, os algoritmos de substituição de páginas desempenham um papel fundamental na otimização do uso da memória. 

Sabemos e vimos em sala os principais algoritmos de substituição de páginas. Para a construção deste simulador, foram selecionados os quatro métodos a seguir:
1. **Algoritmo FIFO (First In, First Out)**: A página mais antiga na memória é a primeira a ser substituída quando ocorre uma falta de página. É simples de entender, mas pode sofrer com a anomalia de Belady.
2. **Algoritmo LRU (Least Recently Used)**: Substitui a página que foi usada menos recentemente, baseando-se no princípio da localidade temporal.
3. **Algoritmo do Relógio (Clock)**: Simula um relógio circular utilizando um bit de uso (segunda chance) para determinar se uma página deve ou não ser substituída.
4. **Algoritmo Ótimo**: Substitui a página que será usada mais tarde no futuro, servindo como a referência ideal e teórica para avaliar o desempenho dos demais algoritmos.

## Metodologia
O simulador foi desenvolvido integralmente na linguagem de programação **Java**, utilizando a biblioteca nativa **Swing** para o desenvolvimento da interface gráfica e a geração de gráficos. O programa recebe como entrada uma sequência de números inteiros (cadeia de páginas) e um número estabelecido para os quadros de memória (*frames*). 

Quatro métodos distintos foram criados (na classe `PageReplacementAlgorithms.java`) para implementar a lógica de cada algoritmo de substituição. Através da interface gráfica (`SimulatorGUI.java`), o usuário pode gerar dados aleatórios ou fornecer os dados manualmente. Ao processar, a aplicação calcula e apresenta as saídas do número de faltas de página no formato exigido (`- Método X - Y faltas de página`), e desenha um gráfico de barras com o comparativo de desempenho entre as opções, atendendo também ao requisito extra do trabalho.

**Como executar a aplicação:**
Para utilizar o simulador, basta ter o *Java Development Kit* (JDK) instalado. Abra o terminal na pasta do projeto e execute os comandos:
```bash
javac PageReplacementAlgorithms.java SimulatorGUI.java
java SimulatorGUI
```

## Resultados e Discussão
Através da utilização do simulador com a interface gráfica, foi possível realizar testes empíricos para avaliar o comportamento de cada método. A título de demonstração, adotou-se um cenário de teste com a seguinte configuração:
- **Tamanho da memória (Frames):** 3
- **Fila de páginas (Referências):** 1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5

Após processar a simulação com a cadeia de 12 páginas, a ferramenta retornou os seguintes resultados acompanhados do gráfico comparativo:
- Método FIFO - 9 faltas de página
- Método LRU - 10 faltas de página
- Método do Relógio - 9 faltas de página
- Método Ótimo - 7 faltas de página

Na análise deste caso específico, observa-se que o **Método Ótimo**, por conseguir "prever" as futuras requisições de páginas, apresentou o melhor desempenho com apenas 7 faltas de página, servindo perfeitamente como referência ideal. Por outro lado, um ponto de discussão interessante ocorreu entre os métodos LRU e FIFO/Relógio: para esta carga de trabalho específica, o LRU apresentou o pior desempenho (10 faltas), evidenciando que em sequências cíclicas onde páginas recém-utilizadas demoram a ser requisitadas novamente, o princípio da localidade temporal pode ser penalizado em comparação ao FIFO puro ou ao Relógio.

## Conclusão
A implementação do simulador demonstrou na prática o funcionamento e as diferenças de eficiência envolvidas no gerenciamento da memória virtual de um Sistema Operacional. A possibilidade de visualizar os resultados quantitativamente e através de um gráfico comparativo tornou evidente o impacto da escolha correta de um algoritmo de substituição de páginas de acordo com a carga de trabalho submetida. A ferramenta atendeu com sucesso todos os objetivos propostos.

## Referências
- TANENBAUM, Andrew S.; BOS, Herbert. Sistemas Operacionais Modernos. 4. ed. São Paulo: Pearson Prentice Hall, 2016.
- DEVMEDIA. Introdução à interface GUI no Java. Disponível em: https://www.devmedia.com.br/introducao-a-interface-gui-no-java/25646. Acesso em: 07 mai. 2026.

---

### Projeto Fonte
**Link para o repositório no GitHub:** [https://github.com/savass33/simulador_paginas](https://github.com/savass33/simulador_paginas)