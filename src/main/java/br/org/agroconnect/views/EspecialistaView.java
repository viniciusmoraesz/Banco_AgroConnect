package br.org.agroconnect.views;

import br.org.agroconnect.dao.EspecialistaDao;
import br.org.agroconnect.exception.EntidadeNaoEncontradaException;
import br.org.agroconnect.models.Especialista;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class EspecialistaView {

    private final Scanner scanner;
    private final EspecialistaDao especialistaDao;

    public EspecialistaView() throws SQLException {
        this.scanner = new Scanner(System.in);
        this.especialistaDao = new EspecialistaDao();
    }

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println("\n=== MENU ESPECIALISTA ===");
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
                        cadastrarEspecialista();
                        break;
                    case 2:
                        listarEspecialistas();
                        break;
                    case 3:
                        pesquisarEspecialista();
                        break;
                    case 4:
                        atualizarEspecialista();
                        break;
                    case 5:
                        removerEspecialista();
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

    private void cadastrarEspecialista() throws SQLException {
        System.out.println("\n--- Cadastrar Especialista ---");
        System.out.print("Especialidade: ");
        String especialidade = scanner.nextLine();
        System.out.print("UUID do Usuário: ");
        UUID id = UUID.fromString(scanner.nextLine());

        Especialista especialista = new Especialista(especialidade, id);
        especialistaDao.cadastrar(especialista);

        System.out.println("Especialista cadastrado com sucesso.");
    }

    private void listarEspecialistas() throws SQLException {
        System.out.println("\n--- Lista de Especialistas ---");
        List<Especialista> lista = especialistaDao.listar();

        if (lista.isEmpty()) {
            System.out.println("Nenhum especialista encontrado.");
        } else {
            for (Especialista e : lista) {
                System.out.println("ID: " + e.getCdUsuario());
                System.out.println("Especialidade: " + e.getEspecialidade());
                System.out.println("-------------------------");
            }
        }
    }

    private void pesquisarEspecialista() throws SQLException, EntidadeNaoEncontradaException {
        System.out.print("\nDigite o UUID do especialista: ");
        UUID id = UUID.fromString(scanner.nextLine());
        Especialista e = especialistaDao.pesquisar(id);

        System.out.println("ID: " + e.getCdUsuario());
        System.out.println("Especialidade: " + e.getEspecialidade());
    }

    private void atualizarEspecialista() throws SQLException {
        try {
            System.out.print("\nDigite o UUID do especialista a ser atualizado: ");
            UUID id = UUID.fromString(scanner.nextLine());
            Especialista e = especialistaDao.pesquisar(id);

            System.out.print("Nova especialidade [" + e.getEspecialidade() + "]: ");
            String novaEspecialidade = scanner.nextLine();
            if (novaEspecialidade != null && !novaEspecialidade.trim().isEmpty()) {
                e.setEspecialidade(novaEspecialidade);
            }

            especialistaDao.atualizar(e);
            System.out.println("Especialista atualizado com sucesso.");

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removerEspecialista() throws SQLException {
        try {
            System.out.print("\nDigite o UUID do especialista a ser removido: ");
            UUID id = UUID.fromString(scanner.nextLine());

            especialistaDao.remover(id);
            System.out.println("Especialista removido com sucesso.");
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sair() throws SQLException {
        especialistaDao.fecharConexao();
        System.out.println("Conexão encerrada. Até logo!");
    }

    public static void main(String[] args) {
        try {
            new EspecialistaView().exibirMenu();
        } catch (SQLException e) {
            System.out.println("Erro ao iniciar o sistema: " + e.getMessage());
        }
    }
}

