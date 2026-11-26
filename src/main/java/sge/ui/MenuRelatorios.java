package sge.ui;

import java.util.List;
import java.util.Scanner;
import sge.service.RelatorioService;
import sge.service.RelatorioGerado;

/**
 * Apresenta no console um menu interativo para geração de relatórios.
 */
public class MenuRelatorios {
    private final RelatorioService relatorioService;
    private final Scanner scanner;

    /**
     * Cria o menu com o serviço de relatórios e a entrada do console.
     *
     * @param relatorioService serviço de relatórios
     * @param scanner entrada do console
     * @throws IllegalArgumentException se algum argumento for nulo
     */
    public MenuRelatorios(RelatorioService relatorioService, Scanner scanner) {
        if (relatorioService == null || scanner == null) {
            throw new IllegalArgumentException("O serviço e a entrada são obrigatórios.");
        }
        this.relatorioService = relatorioService;
        this.scanner = scanner;
    }

    /**
     * Exibe relatórios até que o usuário escolha sair ou a entrada termine.
     */
    public void executar() {
        while (true) {
            List<RelatorioGerado> relatorios = relatorioService.listarRelatorios();
            System.out.println("\n=== Menu de relatórios ===");
            for (int i = 0; i < relatorios.size(); i++) {
                System.out.println((i + 1) + " - " + relatorios.get(i).getTipo());
            }
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            if (!scanner.hasNextLine()) {
                return;
            }
            String entrada = scanner.nextLine().trim();
            int opcao;
            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Informe um número do menu.");
                continue;
            }

            if (opcao == 0) {
                return;
            }
            if (opcao < 1 || opcao > relatorios.size()) {
                System.out.println("Opção inválida. Selecione uma opção do menu.");
                continue;
            }
            System.out.println(relatorios.get(opcao - 1).getConteudo());
        }
    }
}
