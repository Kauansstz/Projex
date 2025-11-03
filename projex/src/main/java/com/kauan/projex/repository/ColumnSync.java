package com.kauan.projex.repository;

import java.sql.*;
import java.util.*;

public class ColumnSync {

    private Connection connection;

    public ColumnSync(Connection connection) {
        this.connection = connection;
    }

    public void sincronizar(String tabela, Map<String, Object> dados) throws SQLException {
        // Pega as colunas existentes no banco
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getColumns(null, null, tabela.toUpperCase(), null);
        Set<String> colunasBanco = new HashSet<>();
        while (rs.next()) {
            colunasBanco.add(rs.getString("COLUMN_NAME").toUpperCase());
        }

        // Filtra apenas os campos que existem no banco
        Map<String, Object> dadosValidos = new HashMap<>();
        for (Map.Entry<String, Object> entry : dados.entrySet()) {
            if (colunasBanco.contains(entry.getKey().toUpperCase())) {
                dadosValidos.put(entry.getKey(), entry.getValue());
            }
        }

        if (dadosValidos.isEmpty()) {
            System.out.println("Nenhum campo válido para atualizar.");
            return;
        }

        // Cria a query dinamicamente
        StringBuilder sql = new StringBuilder("UPDATE ").append(tabela).append(" SET ");
        List<Object> valores = new ArrayList<>();
        for (String coluna : dadosValidos.keySet()) {
            sql.append(coluna).append(" = ?, ");
            valores.add(dadosValidos.get(coluna));
        }
        sql.setLength(sql.length() - 2); // remove a última vírgula
        sql.append(" WHERE ID = ?"); // Exemplo usando ID como filtro
        valores.add(dados.get("ID")); // valor do ID

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < valores.size(); i++) {
                stmt.setObject(i + 1, valores.get(i));
            }
            int atualizados = stmt.executeUpdate();
            System.out.println("Linhas atualizadas: " + atualizados);
        }
    }

    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection(
            "jdbc:h2:~/test", "sa", ""
        );

        Map<String, Object> dados = new HashMap<>();
        dados.put("NOME", "Kauan");
        dados.put("EMAIL", "kauan@email.com");
        dados.put("ULTIMO_LOGIN", new Timestamp(System.currentTimeMillis()));
        dados.put("TOKEN", UUID.randomUUID().toString());
        dados.put("ID", 1L); // ID do registro que vai atualizar

        ColumnSync sync = new ColumnSync(conn);
        sync.sincronizar("INFO_USER", dados);
    }
}
