# 🔍 Buscador de Arquivos — AED / FURB

> Trabalho da disciplina **Algoritmos e Estruturas de Dados**  
> Professor: Gilvan Justino | Entrega: **15/06/2026** | Apresentação: **16/06/2026**

---

## 📌 Objetivo

Desenvolver uma aplicação Java capaz de **indexar e pesquisar palavras em arquivos `.txt`**, utilizando um **mapa de dispersão (HashMap próprio)** implementado do zero — sem uso de estruturas nativas do Java.

---

## 👥 Equipe

| Nome | Responsabilidade principal |
|------|---------------------------|
| [Nome 1] | |
| [Nome 2] | |

---

## 📋 Regras do Trabalho

- ❌ Proibido usar `ArrayList`, `HashMap` ou qualquer estrutura nativa do Java
- ✅ Reutilizar as estruturas implementadas na disciplina
- ✅ Pode adicionar métodos e atributos complementares nas estruturas existentes
- ✅ Equipe de até 2 pessoas

---

## 🗂️ Funcionalidades que devem ser implementadas

- [ ] Definir um diretório e indexar todos os `.txt` (incluindo subdiretórios)
- [ ] Extrair palavras ignorando maiúsculas/minúsculas
- [ ] Ignorar palavras só com números ou pontos
- [ ] Indexar apenas palavras com **3 ou mais letras**
- [ ] Ignorar pontuações
- [ ] Armazenar o índice em um **mapa de dispersão próprio** (`palavra → lista de arquivos`)
- [ ] **Salvar o índice em disco** após indexar
- [ ] **Carregar o índice do disco** ao iniciar (sem reindexar)
- [ ] Busca por **uma palavra** → exibe os arquivos que a contêm
- [ ] Busca por **várias palavras** → exibe apenas arquivos que contêm **todas** elas

---

## 🏗️ Estrutura sugerida do projeto

```
buscador-arquivos/
├── src/
│   ├── estruturas/
│   │   ├── MinhaListaEncadeada.java   ← lista que associa arquivos a cada palavra
│   │   ├── MeuHashMap.java            ← mapa de dispersão principal
│   │   └── EntradaMapa.java           ← par chave-valor do hash
│   ├── indexador/
│   │   ├── Indexador.java             ← lê arquivos e popula o mapa
│   │   └── LeitorArquivos.java        ← percorre diretórios recursivamente
│   ├── persistencia/
│   │   └── IndiceSerializador.java    ← salva e carrega o índice do disco
│   ├── buscador/
│   │   └── Buscador.java             ← realiza as buscas no índice
│   └── Main.java                      ← menu principal com o usuário
├── indice.dat                         ← arquivo gerado após indexação
├── diagramaUML.png                    ← diagrama UML da solução
└── README.md
```

---

## 🗺️ Divisão de tarefas sugerida

### 👤 Pessoa 1 — Estruturas de Dados + Persistência
- [ ] Implementar `MeuHashMap` (tabela hash com tratamento de colisão)
- [ ] Implementar `MinhaListaEncadeada` (para guardar a lista de arquivos por palavra)
- [ ] Implementar `EntradaMapa` (nó do mapa: chave + valor)
- [ ] Implementar `IndiceSerializador` (salvar e carregar o índice em arquivo)

### 👤 Pessoa 2 — Indexador + Buscador + Main
- [ ] Implementar `LeitorArquivos` (percorre pastas e subpastas recursivamente)
- [ ] Implementar `Indexador` (extrai palavras válidas e popula o mapa)
- [ ] Implementar `Buscador` (busca por uma ou várias palavras)
- [ ] Implementar `Main` (menu de interação com o usuário no terminal)

---

## 🔄 Fluxo de execução

```
Início
  │
  ├── indice.dat existe?
  │     ├── SIM → carrega índice do disco para memória
  │     └── NÃO → solicita diretório → indexa arquivos → salva indice.dat
  │
  └── Loop de busca
        ├── usuário digita palavra(s)
        ├── programa busca no índice em memória
        ├── exibe arquivos encontrados
        └── repete até o usuário sair
```

---

## 💡 Dicas de implementação

### MeuHashMap
- Use um array de listas encadeadas (encadeamento separado para colisões)
- Função hash: `Math.abs(palavra.hashCode() % tamanhoArray)`
- Tamanho inicial sugerido: **101** (número primo reduz colisões)

### Indexador
- Use `java.io.File` para percorrer diretórios (isso é permitido — é I/O, não estrutura de dados)
- Para extrair palavras: `linha.toLowerCase().split("[^a-záéíóúâêîôûãõç]+")` (regex remove pontuação)
- Validar: tamanho >= 3 e não ser só números/pontos

### Persistência
- Use `java.io.Serializable` nas classes do índice, ou escreva/leia o arquivo manualmente linha a linha
- Formato sugerido linha a linha: `palavra:arquivo1.txt,arquivo2.txt`

---

## 📦 Entregáveis

- [ ] Código fonte completo
- [ ] Diagrama UML da solução (pode usar [draw.io](https://draw.io) gratuitamente)
- [ ] Publicar no repositório até **15/06/2026**

---

## 📅 Cronograma sugerido

| Dia | Meta |
|-----|------|
| Hoje | Criar repositório, dividir tarefas, criar estrutura de pastas |
| Dia 2–3 | Implementar `MeuHashMap` e `MinhaListaEncadeada` |
| Dia 4–5 | Implementar `Indexador` e `LeitorArquivos` |
| Dia 6 | Implementar persistência (salvar/carregar índice) |
| Dia 7 | Implementar `Buscador` e `Main` (menu) |
| Dia 8 | Testes, ajustes e diagrama UML |
| **15/06** | ✅ Entrega final |
| **16/06** | 🎤 Apresentação |

---

## ▶️ Como executar

```bash
# Compilar
javac -d bin src/**/*.java src/*.java

# Executar
java -cp bin Main
```

---

## 📎 Referências

- Material da disciplina — Professor Gilvan Justino
- [Documentação Java I/O](https://docs.oracle.com/javase/8/docs/api/java/io/File.html)
