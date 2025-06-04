package br.org.agroconnect.dao;

import br.org.agroconnect.exception.EntidadeNaoEncontradaException;
import br.org.agroconnect.factory.ConnectionFactory;
import br.org.agroconnect.models.Agricultor;

import java.nio.ByteBuffer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AgricultorDao {

    private Connection conexao;

    public AgricultorDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void cadastrar(Agricultor agricultor) throws SQLException {
        String sql = "INSERT INTO TB_AGRICULTOR (tp_cultivo, cd_usuario) VALUES (?, ?)";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setString(1, agricultor.getTpCultivo());
        stm.setBytes(2, uuidToBytes(agricultor.getCdUsuario()));
        stm.executeUpdate();
    }

    public Agricultor pesquisar(UUID cdUsuario) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM TB_AGRICULTOR WHERE cd_usuario = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setBytes(1, uuidToBytes(cdUsuario));
        ResultSet result = stm.executeQuery();

        if (!result.next()) {
            throw new EntidadeNaoEncontradaException("Agricultor não encontrado");
        }

        return parseAgricultor(result);
    }

    public List<Agricultor> listar() throws SQLException {
        String sql = "SELECT * FROM TB_AGRICULTOR";
        PreparedStatement stm = conexao.prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        List<Agricultor> lista = new ArrayList<>();

        while (result.next()) {
            lista.add(parseAgricultor(result));
        }

        return lista;
    }

    public void atualizar(Agricultor agricultor) throws SQLException {
        String sql = "UPDATE TB_AGRICULTOR SET tp_cultivo = ? WHERE cd_usuario = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setString(1, agricultor.getTpCultivo());
        stm.setBytes(2, uuidToBytes(agricultor.getCdUsuario()));
        stm.executeUpdate();
    }

    public void remover(UUID cdUsuario) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM TB_AGRICULTOR WHERE cd_usuario = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setBytes(1, uuidToBytes(cdUsuario));
        int linha = stm.executeUpdate();

        if (linha == 0) {
            throw new EntidadeNaoEncontradaException("Agricultor não encontrado para ser removido");
        }
    }

    private Agricultor parseAgricultor(ResultSet result) throws SQLException {
        String tpCultivo = result.getString("tp_cultivo");
        UUID cdUsuario = bytesToUUID(result.getBytes("cd_usuario"));
        return new Agricultor(tpCultivo, cdUsuario);
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
