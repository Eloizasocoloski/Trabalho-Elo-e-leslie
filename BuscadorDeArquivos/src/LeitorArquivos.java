import java.io.File;

/**
 * Percorre recursivamente um diretório e coleta
 * todos os arquivos .txt encontrados.
 */
public class LeitorArquivos {

    /**
     * Varre o diretório (e subdiretórios) e devolve uma
     * ListaEncadeada com os caminhos absolutos de cada .txt.
     *
     * @param caminhoDiretorio  caminho do diretório raiz
     * @return lista com os caminhos dos arquivos .txt encontrados
     */
    public ListaEncadeada<String> listarArquivosTxt(String caminhoDiretorio) {
        ListaEncadeada<String> arquivos = new ListaEncadeada<>();
        File diretorio = new File(caminhoDiretorio);

        if (!diretorio.exists()) {
            System.out.println("Diretório não encontrado: " + caminhoDiretorio);
            return arquivos;
        }
        if (!diretorio.isDirectory()) {
            System.out.println("O caminho informado não é um diretório: " + caminhoDiretorio);
            return arquivos;
        }

        percorrerRecursivo(diretorio, arquivos);
        return arquivos;
    }

    /** Recursão interna: visita cada entrada do diretório. */
    private void percorrerRecursivo(File diretorio, ListaEncadeada<String> arquivos) {
        File[] entradas = diretorio.listFiles();
        if (entradas == null) return;

        for (File entrada : entradas) {
            if (entrada.isDirectory()) {
                percorrerRecursivo(entrada, arquivos);
            } else if (entrada.isFile() && entrada.getName().toLowerCase().endsWith(".txt")) {
                arquivos.inserir(entrada.getAbsolutePath());
            }
        }
    }

    /**
     * Lê todo o conteúdo de um arquivo de texto e devolve como String.
     *
     * @param caminho  caminho absoluto do arquivo
     * @return conteúdo do arquivo, ou string vazia em caso de erro
     */
    public String lerConteudo(String caminho) {
        StringBuilder sb = new StringBuilder();
        try (java.io.BufferedReader br = new java.io.BufferedReader(
                new java.io.InputStreamReader(
                        new java.io.FileInputStream(caminho), "UTF-8"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                sb.append(linha).append(" ");
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo '" + caminho + "': " + e.getMessage());
        }
        return sb.toString();
    }
}
 