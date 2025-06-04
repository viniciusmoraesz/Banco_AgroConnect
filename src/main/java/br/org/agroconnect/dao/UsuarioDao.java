package br.org.agroconnect.dao;

import br.org.agroconnect.exception.EntidadeNaoEncontradaException;
import br.org.agroconnect.factory.ConnectionFactory;
import br.org.agroconnect.models.Usuario;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsuarioDao {

    private Connection conexao;

    public UsuarioDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void cadastrar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO TB_USUARIO (cd_usuario, ds_email, ds_senha) VALUES (?, ?, ?)";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setBytes(1, uuidToBytes(usuario.getCdUsuario()));
        stm.setString(2, usuario.getDsEmail());
        stm.setString(3, usuario.getDsSenha());
        stm.executeUpdate();
    }

    public Usuario pesquisar(UUID cdUsuario) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM TB_USUARIO WHERE cd_usuario = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setBytes(1, uuidToBytes(cdUsuario));
        ResultSet result = stm.executeQuery();

        if (!result.next()) {
            throw new EntidadeNaoEncontradaException("Usuário não encontrado");
        }

        return parseUsuario(result);
    }

    public List<Usuario> listar() throws SQLException {
        String sql = "SELECT * FROM TB_USUARIO";
        PreparedStatement stm = conexao.prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        List<Usuario> lista = new ArrayList<>();

        while (result.next()) {
            lista.add(parseUsuario(result));
        }

        return lista;
    }

    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE TB_USUARIO SET ds_email = ?, ds_senha = ? WHERE cd_usuario = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setString(1, usuario.getDsEmail());
        stm.setString(2, usuario.getDsSenha());
        stm.setBytes(3, uuidToBytes(usuario.getCdUsuario()));
        stm.executeUpdate();
    }

    public void remover(UUID cdUsuario) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM TB_USUARIO WHERE cd_usuario = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setBytes(1, uuidToBytes(cdUsuario));
        int linha = stm.executeUpdate();

        if (linha == 0) {
            throw new EntidadeNaoEncontradaException("Usuário não encontrado para ser removido");
        }
    }

    private Usuario parseUsuario(ResultSet result) throws SQLException {
        UUID cdUsuario = bytesToUUID(result.getBytes("cd_usuario"));
        String dsEmail = result.getString("ds_email");
        String dsSenha = result.getString("ds_senha");
        return new Usuario(cdUsuario, dsEmail, dsSenha);
    }

    private byte[] uuidToBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    private UUID bytesToUUID(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long high = bb.getLong();
        long low = bb.getLong();
        return new UUID(high, low);
    }

    public void fecharConexao() throws SQLException {
        conexao.close();
    }
}