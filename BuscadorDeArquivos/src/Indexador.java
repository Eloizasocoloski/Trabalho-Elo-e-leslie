/**
 * Extrai palavras válidas de arquivos .txt e as insere
 * no MapaDispersao (índice invertido).
 *
 * Regras (conforme enunciado):
 *  - Sem distinção entre maiúsculas e minúsculas (tudo vira lower-case)
 *  - Palavras formadas APENAS por dígitos ou pontos são ignoradas
 *  - Apenas palavras com 3 ou mais letras são indexadas
 *  - Pontuações são descartadas
 *
 * Estrutura do índice:
 *   palavra  →  ListaEncadeada<String>  (caminhos dos arquivos que contêm a palavra)
 */
public class Indexador {

    // Tamanho da tabela hash — primo grande reduz colisões
    private static final int TAMANHO_MAPA = 10007;

    private MapaDispersao<ListaEncadeada<String>> indice;
    private LeitorArquivos leitor;

    public Indexador() {
        this.indice  = new MapaDispersao<>(TAMANHO_MAPA);
        this.leitor  = new LeitorArquivos();
    }

    /**
     * Indexa todos os arquivos .txt presentes em {@code caminhoDiretorio}
     * e em seus subdiretórios.
     *
     * @param caminhoDiretorio  diretório raiz a varrer
     */
    public void indexar(String caminhoDiretorio) {
        ListaEncadeada<String> arquivos = leitor.listarArquivosTxt(caminhoDiretorio);

        if (arquivos.estaVazia()) {
            System.out.println("Nenhum arquivo .txt encontrado em: " + caminhoDiretorio);
            return;
        }

        int totalArquivos = 0;
        NoLista<String> noArquivo = arquivos.getPrimeiro();
        while (noArquivo != null) {
            String caminho = noArquivo.getInfo();
            indexarArquivo(caminho);
            totalArquivos++;
            noArquivo = noArquivo.getProximo();
        }

        System.out.println(totalArquivos + " arquivo(s) indexado(s). " +
                "Fator de carga do mapa: " +
                String.format("%.2f", indice.calcularFatorCarga()));
    }

    /** Lê um arquivo e insere cada palavra válida no índice. */
    private void indexarArquivo(String caminho) {
        String conteudo = leitor.lerConteudo(caminho);
        // Separa por qualquer espaço em branco
        String[] tokens = conteudo.split("\\s+");

        for (String token : tokens) {
            String palavra = limpar(token);
            if (palavraValida(palavra)) {
                registrar(palavra, caminho);
            }
        }
    }

    /**
     * Remove pontuação das extremidades e converte para minúsculas.
     * Pontuações internas (ex: hifens, apóstrofos) também são removidas.
     */
    private String limpar(String token) {
        // Remove tudo que não seja letra ou dígito
        return token.replaceAll("[^a-zA-ZÀ-ú0-9]", "").toLowerCase();
    }

    /**
     * Verifica se a palavra deve ser indexada:
     *  - comprimento >= 3
     *  - não pode ser formada APENAS por dígitos ou pontos
     *    (após limpeza, pontos já foram removidos, então basta checar dígitos)
     */
    private boolean palavraValida(String palavra) {
        if (palavra == null || palavra.length() < 3) return false;
        // Se todos os caracteres são dígitos → ignorar
        return !palavra.matches("[0-9]+");
    }

    /**
     * Associa {@code caminho} à {@code palavra} no índice.
     * Se a palavra ainda não existe no mapa, cria uma nova lista.
     * Se o arquivo já está na lista, não duplica.
     */
    private void registrar(String palavra, String caminho) {
        ListaEncadeada<String> docs = indice.buscar(palavra);

        if (docs == null) {
            docs = new ListaEncadeada<>();
            docs.inserir(caminho);
            indice.inserir(palavra, docs);
        } else {
            // Evita duplicatas: insere só se arquivo ainda não está na lista
            if (docs.buscar(caminho) == null) {
                docs.inserir(caminho);
            }
        }
    }

    /** Devolve o índice construído (para salvar em disco ou buscar). */
    public MapaDispersao<ListaEncadeada<String>> getIndice() {
        return indice;
    }

    /** Substitui o índice interno (usado ao carregar do disco). */
    public void setIndice(MapaDispersao<ListaEncadeada<String>> indice) {
        this.indice = indice;
    }
}


