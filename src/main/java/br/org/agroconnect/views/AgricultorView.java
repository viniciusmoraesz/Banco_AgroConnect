package br.org.agroconnect.views;

import br.org.agroconnect.dao.AgricultorDao;
import br.org.agroconnect.exception.EntidadeNaoEncontradaException;
import br.org.agroconnect.models.Agricultor;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class AgricultorView {

    private final Scanner scanner;
    private final AgricultorDao agricultorDao;

    public AgricultorView() throws SQLException {
        this.scanner = new Scanner(System.in);
        this.agricultorDao = new AgricultorDao();
    }

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println("\n=== MENU AGRICULTOR ===");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar todos");
            System.out.println("3 - Pesquisar por ID");
            System.out.println("4 - Atualizar");
            System.out.println("5 - Remover");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            try {
                switch (opcao) {
                    case 1:
                        cadastrarAgricultor();
                        break;
                    case 2:
                        listarAgricultores();
                        break;
                    case 3:
                        pesquisarAgricultor();
                        break;
                    case 4:
                        atualizarAgricultor();
                        break;
                    case 5:
                        removerAgricultor();
                        break;
                    case 0:
                        sair();
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opcao != 0);
    }

    private void cadastrarAgricultor() throws SQLException {
        System.out.println("\n--- Cadastrar Agricultor ---");
        System.out.print("Tipo de Cultivo: ");
        String tpCultivo = scanner.nextLine();

        System.out.print("UUID do Usuário: ");
        UUID cdUsuario = UUID.fromString(scanner.nextLine());

        Agricultor agricultor = new Agricultor(tpCultivo, cdUsuario);
        agricultorDao.cadastrar(agricultor);

        System.out.println("Agricultor cadastrado com sucesso.");
    }

    private void listarAgricultores() throws SQLException {
        System.out.println("\n--- Lista de Agricultores ---");
        List<Agricultor> agricultores = agricultorDao.listar();

        if (agricultores.isEmpty()) {
            System.out.println("Nenhum agricultor encontrado.");
        } else {
            for (Agricultor a : agricultores) {
                System.out.println("UUID do Usuário: " + a.getCdUsuario());
                System.out.println("Tipo de Cultivo: " + a.getTpCultivo());
                System.out.println("-----------------------------");
            }
        }
    }

    private void pesquisarAgricultor() throws SQLException, EntidadeNaoEncontradaException {
        System.out.print("\nDigite o UUID do agricultor: ");
        UUID id = UUID.fromString(scanner.nextLine());
        Agricultor a = agricultorDao.pesquisar(id);

        System.out.println("UUID do Usuário: " + a.getCdUsuario());
        System.out.println("Tipo de Cultivo: " + a.getTpCultivo());
    }

    private void atualizarAgricultor() throws SQLException {
        try {
            System.out.print("\nDigite o UUID do agricultor a ser atualizado: ");
            UUID id = UUID.fromString(scanner.nextLine());

            Agricultor a = agricultorDao.pesquisar(id);

            System.out.print("Novo tipo de cultivo [" + a.getTpCultivo() + "]: ");
            String novoCultivo = scanner.nextLine();
            if (novoCultivo != null && !novoCultivo.trim().isEmpty()) {
                a.setTpCultivo(novoCultivo);
            }

            agricultorDao.atualizar(a);
            System.out.println("Agricultor atualizado com sucesso.");

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removerAgricultor() throws SQLException {
        try {
            System.out.print("\nDigite o UUID do agricultor a ser removido: ");
            UUID id = UUID.fromString(scanner.nextLine());

            agricultorDao.remover(id);
            System.out.println("Agricultor removido com sucesso.");
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sair() throws SQLException {
        agricultorDao.fecharConexao();
        System.out.println("Conexão encerrada. Até logo!");
    }

    public static void main(String[] args) {
        try {
            new AgricultorView().exibirMenu();
        } catch (SQLException e) {
            System.out.println("Erro ao iniciar o sistema: " + e.getMessage());
        }
    }
}

