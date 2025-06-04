package br.org.agroconnect.dao;
import br.org.agroconnect.exception.EntidadeNaoEncontradaException;
import br.org.agroconnect.factory.ConnectionFactory;
import br.org.agroconnect.models.Pessoa;

import java.nio.ByteBuffer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PessoaDao {

    private Connection conexao;

    public PessoaDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void cadastrar(Pessoa pessoa) throws SQLException {

        String sql = "INSERT INTO TB_PESSOA (nm_nome, nr_cpf, cd_usuario, tp_usuario) VALUES (?, ?, ?, ?)";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setString(1, pessoa.getNmNome());
        stm.setString(2, pessoa.getNrCpf());
        stm.setBytes(3, uuidToBytes(pessoa.getCdUsuario()));
        stm.setString(4, pessoa.getTpUsuario());
        stm.executeUpdate();
    }

    public Pessoa pesquisar(UUID cdUsuario) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM TB_PESSOA WHERE cd_usuario = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setBytes(1, uuidToBytes(cdUsuario));
        ResultSet result = stm.executeQuery();

        if (!result.next()) {
            throw new EntidadeNaoEncontradaException("Pessoa não encontrada");
        }

        return parsePessoa(result);
    }

    public List<Pessoa> listar() throws SQLException {
        String sql = "SELECT * FROM TB_PESSOA";
        PreparedStatement stm = conexao.prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        List<Pessoa> lista = new ArrayList<>();

        while (result.next()) {
            lista.add(parsePessoa(result));
        }

        return lista;
    }

    public void atualizar(Pessoa pessoa) throws SQLException {
        String sql = "UPDATE TB_PESSOA SET nm_nome = ?, nr_cpf = ?, tp_usuario = ? WHERE cd_usuario = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setString(1, pessoa.getNmNome());
        stm.setString(2, pessoa.getNrCpf());
        stm.setString(3, pessoa.getTpUsuario());
        stm.setBytes(4, uuidToBytes(pessoa.getCdUsuario()));
        stm.executeUpdate();
    }

    public void remover(UUID cdUsuario) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM TB_PESSOA WHERE cd_usuario = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setBytes(1, uuidToBytes(cdUsuario));
        int linha = stm.executeUpdate();

        if (linha == 0) {
            throw new EntidadeNaoEncontradaException("Pessoa não encontrada para ser removida");
        }
    }

    private Pessoa parsePessoa(ResultSet result) throws SQLException {
        String nmNome = result.getString("nm_nome");
        String nrCpf = result.getString("nr_cpf");
        UUID cdUsuario = bytesToUUID(result.getBytes("cd_usuario"));
        String tpUsuario = result.getString("tp_usuario");
        return new Pessoa(nmNome, nrCpf, cdUsuario, tpUsuario);
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

