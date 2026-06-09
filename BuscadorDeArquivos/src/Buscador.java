/**
 * Realiza buscas no índice invertido (MapaDispersao).
 *
 * Comportamento (conforme enunciado):
 *  - 1 palavra  → lista todos os documentos que contêm essa palavra
 *  - N palavras → lista apenas os documentos que contêm TODAS elas (interseção)
 */
public class Buscador {

    private MapaDispersao<ListaEncadeada<String>> indice;

    public Buscador(MapaDispersao<ListaEncadeada<String>> indice) {
        this.indice = indice;
    }

    /**
     * Ponto de entrada: recebe a linha digitada pelo usuário,
     * separa as palavras e executa a busca adequada.
     *
     * @param consulta  string com uma ou mais palavras separadas por espaço
     * @return ListaEncadeada com os caminhos dos documentos encontrados
     */
    public ListaEncadeada<String> buscar(String consulta) {
        if (consulta == null || consulta.trim().isEmpty()) {
            return new ListaEncadeada<>();
        }

        String[] termos = consulta.trim().toLowerCase().split("\\s+");

        if (termos.length == 1) {
            return buscarPalavra(termos[0]);
        } else {
            return buscarIntersecao(termos);
        }
    }

    /**
     * Busca simples: devolve a lista de documentos de uma única palavra.
     */
    private ListaEncadeada<String> buscarPalavra(String palavra) {
        ListaEncadeada<String> resultado = indice.buscar(palavra);
        if (resultado == null) {
            return new ListaEncadeada<>();   // palavra não encontrada
        }
        return resultado;
    }

    /**
     * Busca múltipla: interseção dos conjuntos de documentos de cada palavra.
     * Só aparecem documentos que contêm TODAS as palavras informadas.
     */
    private ListaEncadeada<String> buscarIntersecao(String[] termos) {
        // Começa com os docs da primeira palavra
        ListaEncadeada<String> resultado = buscarPalavra(termos[0]);

        for (int i = 1; i < termos.length; i++) {
            ListaEncadeada<String> docsDoTermo = buscarPalavra(termos[i]);
            resultado = intersecao(resultado, docsDoTermo);

            // Otimização: se já está vazio, não precisa continuar
            if (resultado.estaVazia()) break;
        }

        return resultado;
    }

    /**
     * Retorna uma nova lista contendo apenas os elementos
     * presentes em AMBAS as listas (interseção).
     */
    private ListaEncadeada<String> intersecao(
            ListaEncadeada<String> listaA,
            ListaEncadeada<String> listaB) {

        ListaEncadeada<String> resultado = new ListaEncadeada<>();
        NoLista<String> noA = listaA.getPrimeiro();

        while (noA != null) {
            String doc = noA.getInfo();
            if (listaB.buscar(doc) != null) {
                resultado.inserir(doc);
            }
            noA = noA.getProximo();
        }

        return resultado;
    }

    /**
     * Formata e exibe os resultados no terminal.
     *
     * @param documentos  lista retornada por buscar()
     * @param consulta    consulta original (para exibir na mensagem)
     */
    public void exibirResultados(ListaEncadeada<String> documentos, String consulta) {
        System.out.println();
        if (documentos.estaVazia()) {
            System.out.println("Nenhum documento encontrado para: \"" + consulta + "\"");
        } else {
            int total = documentos.obterComprimento();
            System.out.println(total + " documento(s) encontrado(s) para: \"" + consulta + "\"");
            System.out.println("─".repeat(60));
            NoLista<String> no = documentos.getPrimeiro();
            int i = 1;
            while (no != null) {
                System.out.println("  " + i + ". " + no.getInfo());
                no = no.getProximo();
                i++;
            }
        }
        System.out.println();
    }
}
