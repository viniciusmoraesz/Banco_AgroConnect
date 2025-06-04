package br.org.agroconnect.views;

import br.org.agroconnect.exception.EntidadeNaoEncontradaException;
import br.org.agroconnect.models.Usuario;
import br.org.agroconnect.dao.UsuarioDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class UsuarioView {

    private final Scanner scanner;
    private final UsuarioDao usuarioDao;

    public UsuarioView() throws SQLException {
        this.scanner = new Scanner(System.in);
        this.usuarioDao = new UsuarioDao();
    }

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println("\n=== MENU USUÁRIO ===");
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
                    case 1: cadastrarUsuario(); break;
                    case 2:listarUsuarios(); break;
                    case 3:pesquisarUsuario(); break;
                    case 4: atualizarUsuario(); break;
                    case 5: removerUsuario(); break;
                    case 0: sair(); break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opcao != 0);
    }

    private void cadastrarUsuario() throws SQLException {
        System.out.println("\n--- Cadastrar Usuário ---");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        UUID id = UUID.randomUUID();
        Usuario usuario = new Usuario(id, email, senha);
        usuarioDao.cadastrar(usuario);

        System.out.println("Usuário cadastrado com ID: " + id);
    }

    private void listarUsuarios() throws SQLException {
        System.out.println("\n--- Lista de Usuários ---");
        List<Usuario> usuarios = usuarioDao.listar();

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
        } else {
            for (Usuario u : usuarios) {
                System.out.println("ID: " + u.getCdUsuario());
                System.out.println("Email: " + u.getDsEmail());
                System.out.println("-------------------------");
            }
        }
    }

    private void pesquisarUsuario() throws SQLException, EntidadeNaoEncontradaException {
        System.out.print("\nDigite o UUID do usuário: ");
        UUID id = UUID.fromString(scanner.nextLine());
        Usuario u = usuarioDao.pesquisar(id);

        System.out.println("ID: " + u.getCdUsuario());
        System.out.println("Email: " + u.getDsEmail());
        System.out.println("Senha: " + u.getDsSenha());
    }

    private void atualizarUsuario() throws SQLException {
        try {
            System.out.print("\nDigite o UUID do usuário a ser atualizado: ");
            UUID id = UUID.fromString(scanner.nextLine());

            Usuario u = usuarioDao.pesquisar(id);

            System.out.print("Novo email [" + u.getDsEmail() + "]: ");
            String novoEmail = scanner.nextLine();
            if (novoEmail != null && !novoEmail.trim().isEmpty()) u.setDsEmail(novoEmail);

            System.out.print("Nova senha [" + u.getDsSenha() + "]: ");
            String novaSenha = scanner.nextLine();
            if (novaSenha != null && !novaSenha.trim().isEmpty()) u.setDsSenha(novaSenha);

            usuarioDao.atualizar(u);
            System.out.println("Usuário atualizado com sucesso.");

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removerUsuario() throws SQLException {
        try {
            System.out.print("\nDigite o UUID do usuário a ser removido: ");
            UUID id = UUID.fromString(scanner.nextLine());

            usuarioDao.remover(id);
            System.out.println("Usuário removido com sucesso.");
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sair() throws SQLException {
        usuarioDao.fecharConexao();
        System.out.println("Conexão encerrada. Até logo!");
    }

    public static void main(String[] args) {
        try {
            new UsuarioView().exibirMenu();
        } catch (SQLException e) {
            System.out.println("Erro ao iniciar o sistema: " + e.getMessage());
        }
    }
}

