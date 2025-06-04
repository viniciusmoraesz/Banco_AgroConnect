package br.org.agroconnect.dao;

import br.org.agroconnect.exception.EntidadeNaoEncontradaException;
import br.org.agroconnect.factory.ConnectionFactory;
import br.org.agroconnect.models.Especialista;

import java.nio.ByteBuffer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EspecialistaDao {

    private Connection conexao;

    public EspecialistaDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void cadastrar(Especialista especialista) throws SQLException {
        String sql = "INSERT INTO TB_ESPECIALISTA (especialidade, cd_usuario) VALUES (?, ?)";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setString(1, especialista.getEspecialidade());
        stm.setBytes(2, uuidToBytes(especialista.getCdUsuario()));
        stm.executeUpdate();
    }

    public Especialista pesquisar(UUID cdUsuario) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM TB_ESPECIALISTA WHERE cd_usuario = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setBytes(1, uuidToBytes(cdUsuario));
        ResultSet result = stm.executeQuery();

        if (!result.next()) {
            throw new EntidadeNaoEncontradaException("Especialista não encontrado");
        }

        return parseEspecialista(result);
    }

    public List<Especialista> listar() throws SQLException {
        String sql = "SELECT * FROM TB_ESPECIALISTA";
        PreparedStatement stm = conexao.prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        List<Especialista> lista = new ArrayList<>();

        while (result.next()) {
            lista.add(parseEspecialista(result));
        }

        return lista;
    }

    public void atualizar(Especialista especialista) throws SQLException {
        String sql = "UPDATE TB_ESPECIALISTA SET especialidade = ? WHERE cd_usuario = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setString(1, especialista.getEspecialidade());
        stm.setBytes(2, uuidToBytes(especialista.getCdUsuario()));
        stm.executeUpdate();
    }

    public void remover(UUID cdUsuario) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM TB_ESPECIALISTA WHERE cd_usuario = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setBytes(1, uuidToBytes(cdUsuario));
        int linha = stm.executeUpdate();

        if (linha == 0) {
            throw new EntidadeNaoEncontradaException("Especialista não encontrado para ser removido");
        }
    }

    private Especialista parseEspecialista(ResultSet result) throws SQLException {
        String especialidade = result.getString("especialidade");
        UUID cdUsuario = bytesToUUID(result.getBytes("cd_usuario"));
        return new Especialista(especialidade, cdUsuario);
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
